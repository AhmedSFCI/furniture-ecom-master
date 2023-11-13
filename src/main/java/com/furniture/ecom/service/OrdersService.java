/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.OrdersDTO;
import com.furniture.ecom._dto.OrdersDetailsDTO;
import com.furniture.ecom._model.Pagging;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author HP
 */
public interface OrdersService {

    public List<OrdersDTO> getAllOrders(Integer lngNo ,  Pagging paging) throws IOException;

    public List<OrdersDTO> getCancelledOrders(Integer lngNo , Pagging paging) throws IOException;

    public List<OrdersDTO> getDeliveredOrders(Integer lngNo , Pagging paging) throws IOException;

    public List<OrdersDTO> getCustomerOrders(OrdersDTO orderDTO,Integer lngNo , Pagging paging) throws IOException;

    public List<OrdersDetailsDTO> getOrderDetails(OrdersDTO orderDTO, Integer lngNo);

    public void updateOrderStatus(OrdersDTO orderDTO);

    public void cancelOrder(OrdersDTO orderDTO);

    public void addOrder(OrdersDTO orderDTO);

}
