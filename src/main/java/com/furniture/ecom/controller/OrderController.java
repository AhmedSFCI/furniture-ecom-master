/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furniture.ecom._dto.ErrorDTO;
import com.furniture.ecom._dto.MaterialDTO;
import com.furniture.ecom._dto.OrdersDTO;
import com.furniture.ecom._dto.OrdersDetailsDTO;
import com.furniture.ecom._dto.RequestDTO;
import com.furniture.ecom._dto.ResultDTO;
import com.furniture.ecom._entity.Product;
import com.furniture.ecom._enums.OrderStatus;
import com.furniture.ecom._helpers.APIContainer;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom._helpers.CommonValidationHelper;
import com.furniture.ecom._helpers.GlobalConstants;
import com.furniture.ecom._helpers.JsonAPIDocs;
import com.furniture.ecom._helpers.JwtToken;
import com.furniture.ecom._helpers.RequestResponseObj;
import com.furniture.ecom._model.LoginUser;
import com.furniture.ecom._model.Pagging;
import com.furniture.ecom._model.ResponseMessages;
import com.furniture.ecom._util.ObjectChecker;
import com.furniture.ecom.dao.GeneralDAO;
import com.furniture.ecom.service.GeneralService;
import com.furniture.ecom.service.MaterialService;
import com.furniture.ecom.service.OrdersService;
import com.furniture.ecom.service.ProductService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/Orders/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {

    @Autowired
    Validator validator;
    @Autowired
    OrdersService ordersService;

    @Autowired
    GeneralService generalService;

    @Autowired
    JwtToken jwtToken;

    @Autowired
    CommonHelper commonHelper;

    @Autowired
    CommonValidationHelper commonValHelper;

    @Autowired
    ProductService productService;

    @Autowired
    GeneralDAO generalDAO;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("getOrdersList")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = "", name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> getOrdersList(@RequestHeader(name = "Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
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
            data.put(RequestResponseObj.COUNT, generalService.getCountOfRecords("Orders", null));
            Pagging paging = (Pagging) req.getData().get(RequestResponseObj.PAGING);
            if (paging == null) {
                paging = new Pagging();
                paging.setItmPerPage(20);
                paging.setPageNo(1);
            } else {
                if (paging.getItmPerPage() == null || paging.getItmPerPage() <= 0) {
                    paging.setItmPerPage(20);
                }
                if (paging.getPageNo() == null || paging.getPageNo() <= 0 || (paging.getPageNo() - 1) * paging.getItmPerPage() >= (Integer) data.get(RequestResponseObj.COUNT)) {
                    result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, " Incorrect Page No please try Again with correct value!!");
                    return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
                }
            }
            List<OrdersDTO> ordersList = ordersService.getAllOrders(langNo, paging);
            data.put(RequestResponseObj.ORDER, ordersList);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (NullPointerException ex) {
            throw new NullPointerException();
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("getCancelledOrdersList")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = "", name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> getCancelledOrdersList(@RequestHeader(name = "Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
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
            data.put(RequestResponseObj.COUNT, generalService.getCountOfRecords("Orders", " AND orderStatus = '" + OrderStatus.CANCELED.toString() + "'"));
            Pagging paging = (Pagging) req.getData().get(RequestResponseObj.PAGING);
            if (paging == null) {
                paging = new Pagging();
                paging.setItmPerPage(20);
                paging.setPageNo(1);
            } else {
                if (paging.getItmPerPage() == null || paging.getItmPerPage() <= 0) {
                    paging.setItmPerPage(20);
                }
                if (paging.getPageNo() == null || paging.getPageNo() <= 0 || (paging.getPageNo() - 1) * paging.getItmPerPage() >= (Integer) data.get(RequestResponseObj.COUNT)) {
                    result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, " Incorrect Page No please try Again with correct value!!");
                    return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
                }
            }
            List<OrdersDTO> ordersList = ordersService.getCancelledOrders(langNo, paging);
            data.put(RequestResponseObj.ORDER, ordersList);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (NullPointerException ex) {
            throw new NullPointerException();
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("getDeliveredOrdersList")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = "", name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> getDeliveredOrdersList(@RequestHeader(name = "Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
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
            data.put(RequestResponseObj.COUNT, generalService.getCountOfRecords("Orders", " AND orderStatus = '" + OrderStatus.DELIVERED.toString() + "'"));
            Pagging paging = (Pagging) req.getData().get(RequestResponseObj.PAGING);
            if (paging == null) {
                paging = new Pagging();
                paging.setItmPerPage(20);
                paging.setPageNo(1);
            } else {
                if (paging.getItmPerPage() == null || paging.getItmPerPage() <= 0) {
                    paging.setItmPerPage(20);
                }
                if (paging.getPageNo() == null || paging.getPageNo() <= 0 || (paging.getPageNo() - 1) * paging.getItmPerPage() >= (Integer) data.get(RequestResponseObj.COUNT)) {
                    result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, " Incorrect Page No please try Again with correct value!!");
                    return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
                }
            }
            List<OrdersDTO> ordersList = ordersService.getDeliveredOrders(langNo, paging);
            data.put(RequestResponseObj.ORDER, ordersList);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (NullPointerException ex) {
            throw new NullPointerException();
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("getCustOrderList")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_CUST_ORDERS, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> getCustOrderList(@RequestHeader(name = "Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();

//        if (commonValHelper.checkLoginUsrIsNotAnAdmin(loginUsr)) {
//            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
//            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
//        }
        try {
            OrdersDTO orderDTO = objectMapper.convertValue(data.get(RequestResponseObj.ORDER),OrdersDTO.class);
            if (orderDTO.getCustNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "custNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (generalService.checkIdIsExists("Customer", "custNo", orderDTO.getCustNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "custNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }
            data.put(RequestResponseObj.COUNT, generalService.getCountOfRecords("Orders", " AND custNo = " + orderDTO.getCustNo()));
            Pagging paging = (Pagging) req.getData().get(RequestResponseObj.PAGING);
            if (paging == null) {
                paging = new Pagging();
                paging.setItmPerPage(20);
                paging.setPageNo(1);
            } else {
                if (paging.getItmPerPage() == null || paging.getItmPerPage() <= 0) {
                    paging.setItmPerPage(20);
                }
                if (paging.getPageNo() == null || paging.getPageNo() <= 0 || (paging.getPageNo() - 1) * paging.getItmPerPage() >= (Integer) data.get(RequestResponseObj.COUNT)) {
                    result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, " Incorrect Page No please try Again with correct value!!");
                    return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
                }
            }
            List<OrdersDTO> ordersList = ordersService.getCustomerOrders(orderDTO, langNo, paging);
            data.put(RequestResponseObj.ORDER, ordersList);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (NullPointerException ex) {
            throw new NullPointerException();
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("getOrderDetails")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_ORDER_DETAILS, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> getOrderDetails(@RequestHeader(name = "Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();

//        if (commonValHelper.checkLoginUsrIsNotAnAdmin(loginUsr)) {
//            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
//            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
//        }
        try {
            OrdersDTO orderDTO = objectMapper.convertValue(data.get(RequestResponseObj.ORDER),OrdersDTO.class);
            if (orderDTO.getOrderNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "orderNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (generalService.checkIdIsExists("Orders", "orderNo", orderDTO.getOrderNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "orderNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }

            List<OrdersDetailsDTO> ordersList = ordersService.getOrderDetails(orderDTO, langNo);
            data.put(RequestResponseObj.ORDERS_DETAILS_LIST, ordersList);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (NullPointerException ex) {
            throw new NullPointerException();
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("cancelOrder")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_ORDER_DETAILS, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> cancelOrder(@RequestHeader(name = "Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();

//        if (commonValHelper.checkLoginUsrIsNotAnAdmin(loginUsr)) {
//            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
//            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
//        }
        try {
            OrdersDTO orderDTO = objectMapper.convertValue(data.get(RequestResponseObj.ORDER),OrdersDTO.class);
            if (orderDTO.getCustNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "custNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (generalService.checkIdIsExists("Customer", "custNo", orderDTO.getCustNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "custNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }

            if (orderDTO.getOrderNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "orderNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (generalService.checkIdIsExists("Orders", "orderNo", orderDTO.getOrderNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "orderNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }

            ordersService.cancelOrder(orderDTO);
            // data.put(RequestResponseObj.ORDER, ordersList);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (NullPointerException ex) {
            throw new NullPointerException();
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("updateOrderStatus")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.UPD_ORDER_STATUS, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> updateOrderStatus(@RequestHeader(name = "Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
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
            OrdersDTO orderDTO = objectMapper.convertValue(data.get(RequestResponseObj.ORDER),OrdersDTO.class);
//            if (orderDTO.getCustNo() == null) {
//                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "custNo", langNo));
//                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
//            }
//            if (generalService.checkIdIsExists("Customer", "custNo", orderDTO.getCustNo())) {
//                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "custNo", langNo));
//                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
//            }

            if (orderDTO.getOrderNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "orderNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (generalService.checkIdIsExists("Orders", "orderNo", orderDTO.getOrderNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "orderNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }
            if (orderDTO.getOrderStatus() == null || orderDTO.getOrderStatus().isBlank()) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "orderStatus", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!commonHelper.checkFlgValIsExists(GlobalConstants.OrderStatus, orderDTO.getOrderStatus())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "orderStatus", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }
            ordersService.updateOrderStatus(orderDTO);
            // data.put(RequestResponseObj.ORDER, ordersList);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (NullPointerException ex) {
            throw new NullPointerException();
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("addOrder")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.ADD_ORDER, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> addOrder(@RequestHeader(name = "Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();

        if (!commonValHelper.checkLoginUsrIsCustomer(loginUsr)) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        try {
            OrdersDTO orderDTO = objectMapper.convertValue(data.get(RequestResponseObj.ORDER),OrdersDTO.class);
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(orderDTO);
            if (constraintViolations != null && constraintViolations.size() > 0) {
                List<ErrorDTO> objErrors = generalService.getErrorMessages(constraintViolations, langNo);
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, objErrors);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (commonHelper.checkFlgValIsExists(GlobalConstants.PayType, orderDTO.getPayType())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(3, "payType", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }

            Set<Long> prodList = new HashSet();
            for (OrdersDetailsDTO orderDetails : orderDTO.getOrderDetailsList()) {
                orderDetails.setOrderNo(orderDTO.getOrderNo());
                if (orderDetails.getProdNo() == null) {
                    result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "orderNo", langNo));
                    return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
                }
                if (orderDetails.getProdQunatity() == null || orderDetails.getProdQunatity() <= 0) {
                    result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "prodQunatity", langNo));
                    return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
                }

            }
            Map<Long, Product> products = productService.getProductListByNos(prodList);
            double orderTotalPrice = 0;
            for (OrdersDetailsDTO orderDetails : orderDTO.getOrderDetailsList()) {
                Product product = products.get(orderDetails.getProdNo());
                if (product == null) {
                    result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "product", langNo));
                    return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
                }
                double discountRate = 0;
                if (product.getDiscountNo() != null) {
                    Object rate = generalDAO.getAttributeValue("Discount", "discountRate", "discountNo", product.getDiscountNo());
                    if (rate == null) {
                        result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "discountRate", langNo));
                        return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
                    }
                    discountRate = (double) rate;
                }
                Double productPrice = product.getProdPrice() - (product.getProdPrice() * discountRate) / 100;
                Double totalPrice = productPrice * orderDetails.getProdQunatity();
                orderTotalPrice+=totalPrice;
                orderDetails.setProdPrice(BigDecimal.valueOf(productPrice));
                orderDetails.setProdTotalPrice(BigDecimal.valueOf(totalPrice));
                prodList.add(orderDetails.getProdNo());
            }
            orderDTO.setCustNo(loginUsr.getUsrNo().intValue());
            orderDTO.setCreateOn(new Date());
            orderDTO.setOrderStatus(OrderStatus.IN_PROCESS.toString());
            Object orderNo = generalService.getMaxValue("Orders", "orderNo");
            orderDTO.setOrderNo((Integer) orderNo + 1);
            orderDTO.setCancelFlg(BigInteger.ZERO);
            orderDTO.setCancelOn(null);
            //  orderDTO.setDeliveryDate();
            ordersService.addOrder(orderDTO);
            // data.put(RequestResponseObj.ORDER, ordersList);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (NullPointerException ex) {
            throw new NullPointerException();
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }
}
