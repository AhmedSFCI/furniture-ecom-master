/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furniture.ecom._helpers.JsonAPIDocs;
import com.furniture.ecom._helpers.APIContainer;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom._helpers.CommonValidationHelper;
import com.furniture.ecom._helpers.JwtToken;
import com.furniture.ecom._dto.RequestDTO;
import com.furniture.ecom._dto.ErrorDTO;
import com.furniture.ecom._dto.DiscountDTO;
import com.furniture.ecom._dto.ResultDTO;
import com.furniture.ecom._helpers.RequestResponseObj;
import com.furniture.ecom.service.DiscountService;
import com.furniture.ecom.service.GeneralService;
import com.furniture.ecom._model.LoginUser;
import com.furniture.ecom._model.ResponseMessages;
import com.furniture.ecom._util.ObjectChecker;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author HP
 */
@RestController
@RequestMapping("/Discount/")
public class DiscountController {

    @Autowired
    Validator validator;
    @Autowired
    DiscountService discountService;
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

    @PostMapping("getDiscountList")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "Authorization", paramType = "header"),
        @ApiImplicitParam(example = JsonAPIDocs.GET_DISCOUNTS_LIST, name = "req", paramType = "body")})
    public ResponseEntity<Object> getDiscountList(@RequestHeader("Authorization") String Auth, @RequestBody(required = false) RequestDTO req)
            throws Exception {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        Integer langNo = 1;
        try {
            langNo = commonHelper.getLngNoFromRequest(req);
            DiscountDTO discountDto = discountService.getDiscount();
            if (discountDto != null) {
                data.put(RequestResponseObj.DISCOUNT, discountDto);
            }
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (NullPointerException ex) {
            throw new NullPointerException();
        } catch (Exception ex) {
            ex.printStackTrace();
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("getDiscountByCode")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "Authorization", paramType = "header"),
        @ApiImplicitParam(example = JsonAPIDocs.GET_DISCOUNT_BY_NO, name = "req", paramType = "body")})
    public ResponseEntity<Object> getDiscountByCode(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();

        if (commonValHelper.checkLoginUsrIsNotAnAdmin(loginUsr)) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        try {
            DiscountDTO discountDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.DISCOUNT), DiscountDTO.class);
            if (ObjectChecker.isEmptyOrNull(discountDto)) {
                result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            DiscountDTO discount = discountService.getDiscountByNo(discountDto.getDiscountNo());
            if (ObjectChecker.isEmptyOrNull(discount)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "discountNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }
            data.put(RequestResponseObj.DISCOUNT, discount);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("addDiscount")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "Authorization", paramType = "header"),
        @ApiImplicitParam(example = JsonAPIDocs.ADD_DISCOUNT, name = "req", paramType = "body")})
    public ResponseEntity<Object> addDiscount(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();

        if (commonValHelper.checkLoginUsrIsNotAnAdmin(loginUsr)) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }

        try {
             DiscountDTO discountDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.DISCOUNT), DiscountDTO.class);
            if (ObjectChecker.isEmptyOrNull(discountDto)) {
                result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(discountDto);
            if (constraintViolations != null && constraintViolations.size() > 0) {
                List<ErrorDTO> objErrors = generalService.getErrorMessages(constraintViolations, langNo);
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, objErrors);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (ObjectChecker.isNotEmptyOrZero(discountDto.getDiscountNo())) {
                discountDto.setDiscountNo(null);
            }
            discountDto.setCreateOn(new Date());
            discountDto.setCreateAdm(loginUsr.getUsrNo().intValue());
            Integer responseCode = discountService.addDiscount(discountDto);
            if (ObjectChecker.areEqual(responseCode, ResponseMessages.RECORD_IS_EXISTS_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(3, "discountNo", langNo));
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

    @PostMapping("deleteDiscount")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "Authorization", paramType = "header"),
        @ApiImplicitParam(example = JsonAPIDocs.DELETE_DISCOUNT, name = "req", paramType = "body")})
    public ResponseEntity<Object> deleteDiscount(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();

        if (commonValHelper.checkLoginUsrIsNotAnAdmin(loginUsr)) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }

        try {
             DiscountDTO discountDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.DISCOUNT), DiscountDTO.class);
            if (ObjectChecker.isEmptyOrNull(discountDto)) {
                result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (ObjectChecker.isEmptyOrZero(new BigDecimal(discountDto.getDiscountNo()))) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "discountNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (ObjectChecker.areEqual(discountService.deleteDiscount(discountDto.getDiscountNo()), ResponseMessages.NOT_FOUND_RECORD_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "discountNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
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

    @PostMapping("updateDiscount")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "Authorization", paramType = "header"),
        @ApiImplicitParam(example = JsonAPIDocs.UPDATE_DISCOUNT, name = "req", paramType = "body")})
    public ResponseEntity<Object> updateDiscount(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        System.out.println("Auth : " + Auth);
        LoginUser usr = jwtToken.decodeToken(Auth);
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        int langNo = 1;

        Map<String, Integer> errorsList = new HashMap<>();
        try {
            langNo = commonHelper.getLngNoFromRequest(req);
             DiscountDTO discountDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.DISCOUNT), DiscountDTO.class);
            if (ObjectChecker.isEmptyOrNull(discountDto)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (ObjectChecker.isEmptyOrZero(new BigDecimal(discountDto.getDiscountNo()))) {
                errorsList.put("discountNo", 1);
            } else if (!discountService.checkDiscountIsExists(discountDto.getDiscountNo())) {
                errorsList.put("discountNo", 4);
            }
            if (ObjectChecker.isEmptyOrNull(discountDto.getStartDate())) {
                errorsList.put("startDate", 1);
            } else if (!discountDto.getStartDate().after(new Date())) {
                errorsList.put("startDate", 26);
            }
            if (discountDto.getExpirDate() == null) {
                errorsList.put("expirDate", 1);
            } else if (!discountDto.getExpirDate().after(new Date())) {
                errorsList.put("expirDate", 26);
            } else if (discountDto.getExpirDate() != null && discountDto.getStartDate() != null) {
                if (discountDto.getExpirDate().before(discountDto.getStartDate())) {
                    errorsList.put("expirDate", 22);
                }
            }

            if (discountDto.getEnabled() == null || discountDto.getEnabled() < 0 || discountDto.getEnabled() > 1) {
                errorsList.put("enabled", 6);
            }
            if (ObjectChecker.isEmptyOrZero(new BigDecimal(discountDto.getDiscountRate()))) {
                errorsList.put("discountRate", 25);
            }
            if (errorsList.size() > 0) {
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }

            Integer rowsEffect = discountService.updateDiscount(discountDto);

            if (Objects.equals(rowsEffect, ResponseMessages.NOT_FOUND_RECORD_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "discountNo", langNo));
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

}
