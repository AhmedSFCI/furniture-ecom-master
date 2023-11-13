/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.OrdersDTO;
import com.furniture.ecom._entity.Orders;
import com.furniture.ecom._entity.OrdersDetails;
import com.furniture.ecom._model.Pagging;
import java.util.List;

/**
 *
 * @author HP
 */
public interface OrdersDAO {

    public List<Orders> getAllOrders(Pagging paging);

    public List<Orders> getCancelledOrders(Pagging paging);

    public List<Orders> getDeliveredOrders(Pagging paging);

    public List<Orders> getCustomerOrders(Integer custNo,Pagging paging);

    public List<OrdersDetails> getOrderDetails(Integer orderNo);

    public void updateOrderStatus(Integer orderNo, String status);

    public void cancelOrder(Integer orderNo);

    public void addOrder(OrdersDTO orderDTO);
}
