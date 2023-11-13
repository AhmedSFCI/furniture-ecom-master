/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furniture.ecom._helpers.APIContainer;
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
import java.util.List;
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
import com.furniture.ecom._model.Pagging;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mosta
 */
@RestController
@RequestMapping("/Customer/")
public class CustomerController {

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

    @PostMapping("getAllCustomers")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_ALL_CUSTOMERS, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> getAllCustomers(@RequestHeader("Authorization") String Auth, @RequestBody(required = false) RequestDTO req) throws Exception {
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
            List<CustomerDTO> customerDtoList = customerService.getCustomerList();
            if (customerDtoList != null) {
                data.put(RequestResponseObj.CUSTOMER_LIST,customerDtoList);
            } else {
                data.put(RequestResponseObj.CUSTOMER_LIST,new ArrayList<>());
            }
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            throw new NullPointerException();
        } catch (Exception ex) {
            ex.printStackTrace();
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("getMultiCustomerPageList")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_PRODUCT_BY_PAGE, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> getCustomersPage(@RequestHeader("Authorization") String Auth, @RequestBody(required = false) RequestDTO req) throws Exception {
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
            Pagging pagingDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.PAGING), Pagging.class);
            if (pagingDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }
            if (pagingDto.getPageNo() <= 0) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(8, "pageNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }
            if (pagingDto.getItmPerPage() <= 0) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(8, "itmPerPage", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }
            Long count = generalService.getCountOfRecords("Customer", null);
            if (count < pagingDto.getPageNo() || count < (pagingDto.getItmPerPage() * pagingDto.getPageNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(20, "pageNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }
           data.put(RequestResponseObj.COUNT,count);

            List<CustomerDTO> customerDtoList = customerService.getMultiCustomerPageList(pagingDto);
            if (customerDtoList != null) {
                data.put(RequestResponseObj.CUSTOMER_LIST,customerDtoList);
            }
            data.put(RequestResponseObj.CUSTOMER_LIST,customerDtoList);
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

    @PostMapping("getCustomerByCode")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_CUSTOMER_BY_NO, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> getCustomerByCode(@RequestHeader("Authorization") String Auth, @RequestBody(required = false) RequestDTO req) throws Exception {
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
            CustomerDTO customerDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.CUSTOMER), CustomerDTO.class);
            if (customerDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (customerDto.getCustNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "custNo", langNo));
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

    @PostMapping("deleteCustomer")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.DELETE_CUSTOMER, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> deleteCustomer(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
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
            CustomerDTO customerDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.CUSTOMER), CustomerDTO.class);
            if (customerDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (customerDto.getCustNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "custNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }

            if (!Objects.equals(customerService.deleteCustomer(customerDto.getCustNo()), ResponseMessages.NOT_FOUND_RECORD_CODE)) {
                result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            } else {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "custNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
        } catch (DataIntegrityViolationException | NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }
}
