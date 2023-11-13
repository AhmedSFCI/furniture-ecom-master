/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furniture.ecom._helpers.APIContainer;
import com.furniture.ecom._helpers.GlobalConstants;
import com.furniture.ecom._helpers.JwtToken;
import com.furniture.ecom.service.CustomerService;
import com.furniture.ecom._dto.CustomerDTO;
import com.furniture.ecom._dto.RequestDTO;
import com.furniture.ecom._dto.ResultDTO;
import com.furniture.ecom.service.GeneralService;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom._helpers.CommonValidationHelper;
import com.furniture.ecom._model.LoginUser;
import com.furniture.ecom._model.ResponseMessages;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.furniture.ecom._helpers.JsonAPIDocs;
import com.furniture.ecom._helpers.RequestResponseObj;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/User/")
public class UserController {

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
    CommonValidationHelper commonValHelper;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("changeImage")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.UPDATE_CUSTOMER_IMG, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> changeImage(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        if (commonValHelper.checkLoginUsrIsCustomer(loginUsr)) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }

        Map<String, Integer> errorsList = new HashMap<>();
        try {
            CustomerDTO customerDto = objectMapper.convertValue(data.get(RequestResponseObj.CUSTOMER),CustomerDTO.class);
            if (customerDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (customerDto.getCustNo() == null) {
                errorsList.put("custNo", 1);
            }
            if (customerDto.getCustNo() != loginUsr.getUsrNo().intValue()) {
                errorsList.put("custNo", 15);
            } else {
                if (customerDto.getCustPic() == null || customerDto.getCustPic().isBlank()) {
                    errorsList.put("imgPath", 1);
                } else if (!customerDto.getCustPic().matches(GlobalConstants.IMAGE_PATTERN)) {
                    errorsList.put("imgPath", 23);
                }
            }
            if (errorsList.size() > 0) {
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (customerService.checkCustomerISExists(customerDto.getCustNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(4, "customer", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            customerService.updateCustomerPic(customerDto);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (DataIntegrityViolationException | NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        }
    }

    @PostMapping("updateProfile")
    public ResponseEntity<Object> updateCustomerData(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        if (commonValHelper.checkLoginUsrIsCustomer(loginUsr)) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        Map<String, Integer> errorsList = new HashMap<>();
        try {
            CustomerDTO customerDto = objectMapper.convertValue(data.get(RequestResponseObj.CUSTOMER),CustomerDTO.class);
            if (customerDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (customerDto.getCustNo() == null) {
                errorsList.put("custNo", 1);
            }
            if (customerDto.getCustNo() != loginUsr.getUsrNo().intValue()) {
                errorsList.put("custNo", 15);
            } else {
                if (customerDto.getCustEmail() == null || customerDto.getCustEmail().isBlank()) {
                    errorsList.put("custEmail", 1);
                }
                if (customerDto.getCustName() == null || customerDto.getCustName().isBlank()) {
                    errorsList.put("custName", 1);
                }
            }
            if (errorsList.size() > 0) {
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }

            Integer rowsEffect = customerService.updateCustomer(customerDto);
            if (Objects.equals(rowsEffect, ResponseMessages.DUPLICATE_RECORD_AR_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(3, "custEmail", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (Objects.equals(rowsEffect, ResponseMessages.NOT_FOUND_RECORD_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "custNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
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
//    @PostMapping("resetPassword")
//    public ResponseEntity<Object> resetPassword(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req){
//        
//    }

    @PostMapping("getUserProfile")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_CUSTOMER_PROFILE, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> getProfile(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) {
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
            CustomerDTO customerDto = objectMapper.convertValue(data.get(RequestResponseObj.CUSTOMER),CustomerDTO.class);
            if (customerDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (customerDto.getCustNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "custNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (customerDto.getCustNo() != loginUsr.getUsrNo().intValue()) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            CustomerDTO customer = customerService.getCustomerByNo(customerDto.getCustNo());
            if (customer != null) {
                data.put(RequestResponseObj.CUSTOMER,customer);
                result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            } else {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "custNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("getUserOrder")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_CUSTOMER_Orders, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> getUserOrders(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) {
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
            CustomerDTO customerDto = objectMapper.convertValue(data.get(RequestResponseObj.CUSTOMER),CustomerDTO.class);
            if (customerDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (customerDto.getCustNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "custNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (customerDto.getCustNo() != loginUsr.getUsrNo().intValue()) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (customerService.checkCustomerISExists(customerDto.getCustNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "custNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }

        } catch (NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
        return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
    }

    

}
