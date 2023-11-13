/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furniture.ecom.service.CustomerService;
import com.furniture.ecom._dto.FavoritDTO;
import com.furniture.ecom._dto.ProductDTO;
import com.furniture.ecom._dto.RequestDTO;
import com.furniture.ecom._dto.ResultDTO;
import com.furniture.ecom.service.GeneralService;
import com.furniture.ecom._helpers.APIContainer;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom._helpers.CommonValidationHelper;
import com.furniture.ecom._helpers.JsonAPIDocs;
import com.furniture.ecom._helpers.JwtToken;
import com.furniture.ecom._helpers.RequestResponseObj;
import com.furniture.ecom._model.LoginUser;
import com.furniture.ecom._model.ResponseMessages;
import com.furniture.ecom.service.FavoritService;
import com.furniture.ecom._util.ObjectChecker;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
@RequestMapping("/Favorit")
public class FavoritController {

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
    FavoritService favoritService;
    @Autowired
    CommonValidationHelper commonValHelper;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("getFavoritProductList")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_FAVORIT_PRODUCTS, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> getFavoritProductList(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        System.out.println(loginUsr);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        try {
            if (!commonValHelper.checkLoginUsrIsCustomer(loginUsr)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            FavoritDTO favoritDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.FAVORIT), FavoritDTO.class);
            if (favoritDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!ObjectChecker.isNotEmptyOrZero(favoritDto.getCustNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "customer", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (loginUsr.getUsrNo().intValue() != favoritDto.getCustNo()) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            List<ProductDTO> products = favoritService.getFavoritProductList(favoritDto.getCustNo(), langNo);
            data.put(RequestResponseObj.PRODUCT_LIST, products);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (MailException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, "This Email is Invalid .... ");
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
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

    @PostMapping("getCustomerFavoritList")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_FAVORIT_PRODUCTS, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> getCustomerFavoritList(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        System.out.println(loginUsr);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        try {
            if (!commonValHelper.checkLoginUsrIsCustomer(loginUsr)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }

            FavoritDTO favoritDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.FAVORIT), FavoritDTO.class);
            if (favoritDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!ObjectChecker.isNotEmptyOrZero(favoritDto.getCustNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "customer", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (loginUsr.getUsrNo().intValue() != favoritDto.getCustNo()) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            List<FavoritDTO> favorits = favoritService.getCustomerFavoritList(favoritDto.getCustNo());
            data.put(RequestResponseObj.FAVORIT_LIST, favorits);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (MailException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, "This Email is Invalid .... ");
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
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

    @PostMapping("addFavorit")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.ADD_FAVORIT, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> addFavorit(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        System.out.println(loginUsr);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        try {
            if (!commonValHelper.checkLoginUsrIsCustomer(loginUsr)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }

            FavoritDTO favoritDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.FAVORIT), FavoritDTO.class);
            if (favoritDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!ObjectChecker.isNotEmptyOrZero(favoritDto.getCustNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "customer", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!ObjectChecker.isNotEmptyOrZero(favoritDto.getProdNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "product", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!generalService.checkIdIsExists("Customer", "custNo", favoritDto.getCustNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "customer", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!generalService.checkIdIsExists("Product", "prodNo", favoritDto.getCustNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "product", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (loginUsr.getUsrNo().intValue() != favoritDto.getCustNo()) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            Integer added = favoritService.addFavorit(favoritDto);
            if (Objects.equals(added, ResponseMessages.RECORD_IS_EXISTS_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(3, "product", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);

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

    @PostMapping("deleteFavorit")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.ADD_FAVORIT, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> deleteFavorit(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        System.out.println(loginUsr);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        try {
            if (!commonValHelper.checkLoginUsrIsCustomer(loginUsr)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }

            FavoritDTO favoritDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.FAVORIT), FavoritDTO.class);
            if (favoritDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!ObjectChecker.isNotEmptyOrZero(favoritDto.getCustNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "customer", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!ObjectChecker.isNotEmptyOrZero(favoritDto.getProdNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "product", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (loginUsr.getUsrNo().intValue() != favoritDto.getCustNo()) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            Integer deleted = favoritService.deleteFavorit(favoritDto);
            if (Objects.equals(deleted, ResponseMessages.NOT_FOUND_RECORD_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "product", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);

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

    @PostMapping("deleteCustomerFavorit")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_FAVORIT_PRODUCTS, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> deleteAllCustomerFavorit(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        System.out.println(loginUsr);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        try {
            if (!commonValHelper.checkLoginUsrIsCustomer(loginUsr)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }

            FavoritDTO favoritDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.FAVORIT), FavoritDTO.class);
            if (favoritDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!ObjectChecker.isNotEmptyOrZero(favoritDto.getCustNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "customer", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (loginUsr.getUsrNo().intValue() != favoritDto.getCustNo()) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            Integer deleted = favoritService.deleteAllCustomerFavorit(favoritDto.getCustNo());
            if (Objects.equals(deleted, ResponseMessages.NOT_FOUND_RECORD_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "customer", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);

        } catch (MailException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, "This Email is Invalid .... ");
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
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
}
