/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.OrdersDTO;
import com.furniture.ecom._entity.Orders;
import com.furniture.ecom._dto.OrdersDetailsDTO;
import com.furniture.ecom._entity.OrdersDetails;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom._helpers.GlobalConstants;
import com.furniture.ecom._model.Pagging;
import com.furniture.ecom.dao.GeneralDAO;
import com.furniture.ecom.dao.OrdersDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author HP
 */
@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {
    
    @Autowired
    OrdersDAO orderDAO;
    
    @Autowired
    ModelMapper modelMapper;
    
    @Autowired
    CommonHelper commonHelper;
    
    @Autowired
    GeneralDAO generalDAO;
    
    @Override
    public List<OrdersDTO> getAllOrders(Integer lngNo, Pagging paging) throws IOException {
        List<Orders> orders = orderDAO.getAllOrders(paging);
        List<OrdersDTO> OrdersDTOList = new ArrayList();
        if (orders != null) {
            Set<Integer> custNoList = new HashSet<>();
            for (Orders order : orders) {
                OrdersDTO orderDTO = modelMapper.map(order, OrdersDTO.class);
                orderDTO.setOrderStatusNm(commonHelper.getFlgValBasedOnLang(GlobalConstants.OrderStatus, orderDTO.getOrderStatus(), lngNo));
                custNoList.add(orderDTO.getCustNo());
                OrdersDTOList.add(orderDTO);
            }
            Map<Integer, String> map = generalDAO.getCustomersNm(custNoList);
            for (OrdersDTO ordersDTO : OrdersDTOList) {
                ordersDTO.setCustomerName(map.get(ordersDTO.getCustNo()));
            }
        }
        return OrdersDTOList;
    }
    
    @Override
    public List<OrdersDTO> getCancelledOrders(Integer lngNo, Pagging paging) throws IOException {
        List<Orders> orders = orderDAO.getCancelledOrders(paging);
        List<OrdersDTO> OrdersDTOList = new ArrayList();
        if (orders != null) {
            Set<Integer> custNoList = new HashSet<>();
            for (Orders order : orders) {
                OrdersDTO orderDTO = modelMapper.map(order, OrdersDTO.class);
                orderDTO.setOrderStatusNm(commonHelper.getFlgValBasedOnLang(GlobalConstants.OrderStatus, orderDTO.getOrderStatus(), lngNo));
                custNoList.add(orderDTO.getCustNo());
                OrdersDTOList.add(orderDTO);
            }
            Map<Integer, String> map = generalDAO.getCustomersNm(custNoList);
            for (OrdersDTO ordersDTO : OrdersDTOList) {
                ordersDTO.setCustomerName(map.get(ordersDTO.getCustNo()));
            }
        }
        return OrdersDTOList;
    }
    
    @Override
    public List<OrdersDTO> getDeliveredOrders(Integer lngNo, Pagging paging) throws IOException {
        List<Orders> orders = orderDAO.getDeliveredOrders(paging);
        List<OrdersDTO> OrdersDTOList = new ArrayList();
        if (orders != null) {
            Set<Integer> custNoList = new HashSet<>();
            for (Orders order : orders) {
                OrdersDTO orderDTO = modelMapper.map(order, OrdersDTO.class);
                orderDTO.setOrderStatusNm(commonHelper.getFlgValBasedOnLang(GlobalConstants.OrderStatus, orderDTO.getOrderStatus(), lngNo));
                custNoList.add(orderDTO.getCustNo());
                OrdersDTOList.add(orderDTO);
            }
            Map<Integer, String> map = generalDAO.getCustomersNm(custNoList);
            for (OrdersDTO ordersDTO : OrdersDTOList) {
                ordersDTO.setCustomerName(map.get(ordersDTO.getCustNo()));
            }
        }
        return OrdersDTOList;
    }
    
    @Override
    public List<OrdersDTO> getCustomerOrders(OrdersDTO orderDTO, Integer lngNo, Pagging paging) throws IOException {
        List<Orders> orders = orderDAO.getCustomerOrders(orderDTO.getCustNo(), paging);
        List<OrdersDTO> OrdersDTOList = new ArrayList();
        if (orders != null) {
            Set<Integer> custNoList = new HashSet<>();
            for (Orders order : orders) {
                orderDTO = modelMapper.map(order, OrdersDTO.class);
                orderDTO.setOrderStatusNm(commonHelper.getFlgValBasedOnLang(GlobalConstants.OrderStatus, orderDTO.getOrderStatus(), lngNo));
                custNoList.add(orderDTO.getCustNo());
                OrdersDTOList.add(orderDTO);
            }
        }
        return OrdersDTOList;
    }
    
    @Override
    public List<OrdersDetailsDTO> getOrderDetails(OrdersDTO orderDTO, Integer lngNo) {
        List<OrdersDetails> ordersDetailsList = orderDAO.getOrderDetails(orderDTO.getOrderNo());
        List<OrdersDetailsDTO> OrdersDetailsDTOList = new ArrayList();
        if (ordersDetailsList != null) {
            Set<Long> prodList = new HashSet();
            for (OrdersDetails orderDetails : ordersDetailsList) {
                OrdersDetailsDTO ordersDetails = modelMapper.map(orderDetails, OrdersDetailsDTO.class);
                prodList.add(ordersDetails.getProdNo());
                OrdersDetailsDTOList.add(ordersDetails);
            }
            Map<Long, String> productNames = generalDAO.getTextListFromId("Product", "prodName", "prodNo", prodList, lngNo);
            for (OrdersDetailsDTO ordersDetails : OrdersDetailsDTOList) {
                ordersDetails.setProdNm(productNames.get(ordersDetails.getProdNo()));
                ordersDetails.setProdTotalPrice(ordersDetails.getProdPrice().max(BigDecimal.valueOf(ordersDetails.getProdQunatity().longValue())));
            }
        }
        return OrdersDetailsDTOList;
    }
    
    @Override
    public void updateOrderStatus(OrdersDTO orderDTO) {
        orderDAO.updateOrderStatus(orderDTO.getOrderNo(), orderDTO.getOrderStatus());
    }
    
    @Override
    public void cancelOrder(OrdersDTO orderDTO) {
        orderDAO.cancelOrder(orderDTO.getOrderNo());
    }
    
    @Override
    public void addOrder(OrdersDTO orderDTO) {
        orderDAO.addOrder(orderDTO);
    }
    
}
