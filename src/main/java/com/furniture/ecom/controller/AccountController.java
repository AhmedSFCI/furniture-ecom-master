/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furniture.ecom.service.CustomerService;
import com.furniture.ecom._dto.CustomerDTO;
import com.furniture.ecom._dto.ErrorDTO;
import com.furniture.ecom._dto.RequestDTO;
import com.furniture.ecom._dto.ResultDTO;
import com.furniture.ecom._enums.RoleType;
import com.furniture.ecom.service.GeneralService;
import com.furniture.ecom._helpers.APIContainer;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom._helpers.CommonValidationHelper;
import com.furniture.ecom._helpers.JsonAPIDocs;
import com.furniture.ecom._helpers.JwtToken;
import com.furniture.ecom._helpers.PasswordEncoder;
import com.furniture.ecom._helpers.RandomValue;
import com.furniture.ecom._helpers.RequestResponseObj;
import com.furniture.ecom._model.LoginData;
import com.furniture.ecom._model.LoginUser;
import com.furniture.ecom._model.ResponseMessages;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/Account/")
public class AccountController {

    @Autowired
    Validator validator;
    @Autowired
    CustomerService customerService;
    @Autowired
    GeneralService generalService;
    @Autowired
    JwtToken jwtToken;
    @Autowired
    CommonHelper commonHelper;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CommonValidationHelper commonValHelper;

    APIContainer container = new APIContainer();

    @PostMapping("register")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.CUSTOMER_REGISTER, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> register(@RequestBody RequestDTO req) {
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        int langNo = commonHelper.getLngNoFromRequest(req);
        try {
            CustomerDTO customerDTO = objectMapper.convertValue(req.getData().get(RequestResponseObj.CUSTOMER), CustomerDTO.class);
            Map<String, Integer> errorsList = new HashMap<>();
            if (customerDTO == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(customerDTO);
            if (constraintViolations != null && constraintViolations.size() > 0) {
                List<ErrorDTO> objErrors = generalService.getErrorMessages(constraintViolations, langNo);
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, objErrors);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!customerDTO.getConfirmCustPassword().equals(customerDTO.getCustPassword())) {
                errorsList.put("confirmPassword", 7);
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }

            customerDTO.setCreateOn(new Date());
            Short verified = 0;
            customerDTO.setVerifiedFlg((short) 0);
            customerDTO.setVerifyCode(RandomValue.getAlphaNumericString(10));
            customerDTO.setCustPassword(PasswordEncoder.encrypt(customerDTO.getCustPassword()));
            customerDTO.setVerifyExpirDate(commonHelper.addHoursToDate(new Date(), 24));
            try {
                String emailContent = commonHelper.buildEmail(customerDTO.getCustName(), customerDTO.getVerifyCode(), "VerifyEmail");
                commonHelper.sendEmail(customerDTO.getCustEmail(), emailContent);
            } catch (MailException ex) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, "This Email is Invalid .... ");
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            Integer effectsRow = customerService.addCustomer(customerDTO);
            if (Objects.equals(effectsRow, ResponseMessages.RECORD_IS_EXISTS_CODE)) {
                errorsList.put("custNo", 3);
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (Objects.equals(effectsRow, ResponseMessages.DUPLICATE_RECORD_AR_CODE)) {
                errorsList.put("custEmail", 3);
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            String token = jwtToken.createToken(customerService.getCustomerByEmail(customerDTO.getCustEmail()).getCustNo().longValue(), null, PasswordEncoder.encrypt(RoleType.CUSTOMER.toString()), customerDTO.getCustName(), customerDTO.getCustPic(), customerDTO.getCustEmail());
            data.put(RequestResponseObj.TOAKEN, token);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(container.responseWrapper(data, result), HttpStatus.OK);
        } catch (DataIntegrityViolationException | NullPointerException ex) {
            ex.printStackTrace();
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("login")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.CUSTOMER_LOGIN, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> login(@RequestBody RequestDTO req) {
        ResultDTO result;
        Map<String, Integer> errorsList = new HashMap<>();
        Map<String, Object> data = new HashMap();
        int langNo = commonHelper.getLngNoFromRequest(req);

        try {
            LoginData loginData = objectMapper.convertValue(req.getData().get(RequestResponseObj.LOGIN_DATA), LoginData.class);
            if (loginData == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (loginData.getEmail() == null || loginData.getEmail().isBlank()) {
                errorsList.put("custEmail", 1);
            }
            if (loginData.getPassword() == null || loginData.getPassword().isBlank()) {
                errorsList.put("password", 1);
            }
            if (errorsList.size() > 0) {
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            CustomerDTO customerDto = customerService.getCustomerByEmail(loginData.getEmail());
            if (customerDto == null) {
                errorsList.put("custEmail", 11);
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (PasswordEncoder.encrypt(loginData.getPassword()) == null ? customerDto.getCustPassword() != null : !PasswordEncoder.encrypt(loginData.getPassword()).equals(customerDto.getCustPassword())) {
                errorsList.put("password", 5);
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (customerDto.getVerifiedFlg() == 0) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(9, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            String token = jwtToken.createToken(customerDto.getCustNo().longValue(), null, PasswordEncoder.encrypt(RoleType.CUSTOMER.toString()), customerDto.getCustName(), customerDto.getCustPic(), customerDto.getCustEmail());
            data.put(RequestResponseObj.TOAKEN, token);
            customerDto.setCustPassword(null);
            data.put(RequestResponseObj.CUSTOMER, customerDto);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, generalService.getErrorMsg(12, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (DataIntegrityViolationException | NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        }
    }

    @PostMapping("changePassword")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.CUSTOMER_CHANGE_PASSWORD, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> changePassword(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();

        if (!commonValHelper.checkLoginUsrIsNotAnAdmin(loginUsr)) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        Map<String, Integer> errorsList = new HashMap<>();
        try {
            CustomerDTO customerDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.CUSTOMER), CustomerDTO.class);
            if (customerDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (customerDto.getCustNo() != loginUsr.getUsrNo().intValue()) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(30, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }

            if (customerDto.getOldPassword() == null || customerDto.getOldPassword().isBlank()) {
                errorsList.put("oldPassword", 1);
            }
            if (customerDto.getCustPassword() == null || customerDto.getCustPassword().isBlank()) {
                errorsList.put("password", 1);
            } else if (customerDto.getCustPassword().length() < 8) {
                errorsList.put("password", 13);
            }
            if (customerDto.getConfirmCustPassword() == null || customerDto.getConfirmCustPassword().isBlank()) {
                errorsList.put("confirmPassword", 1);
            } else if (customerDto.getConfirmCustPassword().length() < 8) {
                errorsList.put("confirmPassword", 13);
            }
            if (customerDto.getCustPassword() != null && customerDto.getConfirmCustPassword() != null) {
                if (!customerDto.getCustPassword().equals(customerDto.getConfirmCustPassword())) {
                    errorsList.put("confirmPassword", 7);
                }
            }
            if (errorsList.size() > 0) {
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            CustomerDTO updatedCustomer = customerService.getCustomerByNo(loginUsr.getUsrNo().intValue());
            if (updatedCustomer == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "user", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!updatedCustomer.getCustPassword().equals(PasswordEncoder.encrypt(customerDto.getOldPassword()))) {
                errorsList.put("password", 5);
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            customerDto.setCustPassword(PasswordEncoder.encrypt(customerDto.getCustPassword()));
            customerService.updateCustomerPassword(customerDto);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);

        } catch (DataIntegrityViolationException | NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("confirmEmail")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.CONFIRM_EMAIL, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> confirmEmail(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        try {
            if (commonValHelper.checkLoginUsrIsCustomer(loginUsr)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!commonValHelper.checkStringValueIsValid((String) req.getData().get(RequestResponseObj.VERIFIED_CODE))) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "verifyCode", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            CustomerDTO customerDto = customerService.getCustomerByEmail(loginUsr.getEmail());
            if (customerDto.getVerifiedFlg() == 1) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(33, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (customerDto.getVerifyExpirDate().before(new Date())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.DATE_EPIRED[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (req.getData().get(RequestResponseObj.VERIFIED_CODE).equals(customerDto.getVerifyCode())) {
                customerService.setCustomerVerified(customerDto);
                result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.VIERIFY_SUCCESS[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            } else {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(22, "verifyCode", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("sendVerifiedCode")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = "", name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> resendVerificationCode(@RequestHeader("Authorization") String Auth, @RequestBody(required = false) RequestDTO req) {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        if (commonValHelper.checkLoginUsrIsCustomer(loginUsr)) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        try {
            CustomerDTO customerDto = customerService.getCustomerByEmail(loginUsr.getEmail());
            if (customerDto.getVerifiedFlg() == 1) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(33, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            Short verified = 0;
            customerDto.setVerifiedFlg((short) 0);
            customerDto.setVerifyCode(RandomValue.getAlphaNumericString(10));
            customerDto.setVerifyExpirDate(commonHelper.addHoursToDate(new Date(), 24));
            customerService.updateCustomerVerifiedCode(customerDto);
            String emailContent = commonHelper.buildEmail(customerDto.getCustName(), customerDto.getVerifyCode(), "VerifyEmail");
            commonHelper.sendEmail(customerDto.getCustEmail(), emailContent);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (MailException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, "This Email is Invalid .... ");
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("forgetPassword")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.FORGET_PASSWORD, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> forgetPassword(@RequestBody RequestDTO req) {
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        Map<String, Integer> errorsList = new HashMap<>();
        try {
            CustomerDTO customerDTO = objectMapper.convertValue(req.getData().get(RequestResponseObj.CUSTOMER), CustomerDTO.class);
            String email = customerDTO.getCustEmail();
            if (!commonValHelper.checkStringValueIsValid(email)) {
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(1, "custEmail", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            CustomerDTO customerDto = customerService.getCustomerByEmail(email);
            if (customerDto == null) {
                errorsList.put("custEmail", 4);
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            customerDto.setVerifyPasswordCode(RandomValue.getAlphaNumericString(6).toUpperCase());
            customerDto.setVerifyPasswordExpirDate(commonHelper.addHoursToDate(new Date(), 1));
            String emailContent = commonHelper.buildEmail(customerDto.getCustName(), customerDto.getVerifyPasswordCode(), "Password");
            commonHelper.sendEmail(customerDto.getCustEmail(), emailContent);
            customerService.updateCustomerPasswordCode(customerDto);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("resetPassword")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.RESET_PASSWORD, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> resetPassword(@RequestBody RequestDTO req) {
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        Map<String, Integer> errorsList = new HashMap<>();
        try {
            CustomerDTO customerDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.CUSTOMER), CustomerDTO.class);
            if (!commonValHelper.checkStringValueIsValid(customerDto.getCustEmail())) {
                errorsList.put("custEmail", 1);
            }
            if (!commonValHelper.checkStringValueIsValid(customerDto.getVerifyPasswordCode())) {
                errorsList.put("verifyPasswordCode", 1);
            }
            if (!commonValHelper.checkStringValueIsValid(customerDto.getCustPassword())) {
                errorsList.put("password", 1);
            }
            if (!commonValHelper.checkStringValueIsValid(customerDto.getCustPassword())) {
                errorsList.put("password", 1);
            } else if (customerDto.getCustPassword().length() < 8) {
                errorsList.put("password", 13);
            }
            if (!commonValHelper.checkStringValueIsValid(customerDto.getConfirmCustPassword())) {
                errorsList.put("confirmPassword", 1);
            } else {
                if (!customerDto.getCustPassword().equals(customerDto.getConfirmCustPassword())) {
                    errorsList.put("confirmPassword", 7);
                }
            }
            if (errorsList.size() > 0) {
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }

            CustomerDTO customer = customerService.getCustomerByEmail(customerDto.getCustEmail());
            if (customer == null) {
                errorsList.put("custEmail", 4);
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (customer.getVerifiedFlg() == 0) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(9, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!customer.getVerifyPasswordCode().equals(customerDto.getVerifyPasswordCode())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.PASSWORD_VERIFY_CODE_ERROR[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (customer.getVerifyExpirDate().after(new Date())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, "This Code Is Expired ,,, resend a new one .");
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            customer.setCustPassword(PasswordEncoder.encrypt(customerDto.getCustPassword()));
            customerService.updateCustomerPassword(customer);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

}
