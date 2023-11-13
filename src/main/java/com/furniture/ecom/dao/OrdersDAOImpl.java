/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.OrdersDTO;
import com.furniture.ecom._dto.OrdersDetailsDTO;
import com.furniture.ecom._entity.Orders;
import com.furniture.ecom._entity.OrdersDetails;
import com.furniture.ecom._entity.OrdersDetailsPK;
import com.furniture.ecom._enums.OrderStatus;
import com.furniture.ecom._model.Pagging;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HP
 */
@Repository
public class OrdersDAOImpl implements OrdersDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<Orders> getAllOrders(Pagging paging) {
        String HQL = "FROM Orders ORDER BY ORDER BY orderNo";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(HQL);
        query.setFirstResult(paging.getItmPerPage() * (paging.getPageNo()-1));
        query.setMaxResults(paging.getItmPerPage());
        return query.getResultList();
    }

    @Override
    public List<Orders> getCancelledOrders(Pagging paging) {
        String HQL = "FROM Orders WHERE orderStatus = :status ORDER BY orderNo";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(HQL);
        query.setParameter("status", OrderStatus.CANCELED.toString().toLowerCase());
        query.setFirstResult(paging.getItmPerPage() * (paging.getPageNo()-1));
        query.setMaxResults(paging.getItmPerPage());
        return query.getResultList();
    }

    @Override
    public List<Orders> getDeliveredOrders(Pagging paging) {
        String HQL = "FROM Orders WHERE orderStatus = :status ORDER BY orderNo";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(HQL);
        query.setParameter("status", OrderStatus.DELIVERED.toString());
        query.setFirstResult(paging.getItmPerPage() * (paging.getPageNo()-1));
        query.setMaxResults(paging.getItmPerPage());
        return query.getResultList();
    }

    @Override
    public List<Orders> getCustomerOrders(Integer custNo , Pagging paging) {
        String HQL = "FROM Orders WHERE custNo = :custNo ORDER BY orderNo";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(HQL);
        query.setParameter("custNo", custNo);
        query.setFirstResult(paging.getItmPerPage() * (paging.getPageNo()-1));
        query.setMaxResults(paging.getItmPerPage());
        return query.getResultList();
    }

    @Override
    public List<OrdersDetails> getOrderDetails(Integer orderNo) {
        String HQL = "FROM OrdersDerails WHERE ordersDerailsPK.orderNo = :orderNo ORDER BY ordersDerailsPK.prodNo";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(HQL);
        query.setParameter("custNo", orderNo);
        return query.getResultList();
    }

    @Override
    public void cancelOrder(Integer orderNo) {
        Session session = sessionFactory.getCurrentSession();
        Orders persistentEntity = session.get(Orders.class, orderNo);
        if (persistentEntity != null) {
            persistentEntity.setCancelFlg(BigInteger.ONE);
            persistentEntity.setCancelOn(new Date());
            persistentEntity.setOrderStatus(OrderStatus.DELIVERED.toString());
            session.merge(persistentEntity);
        }
    }

    @Override
    public void updateOrderStatus(Integer orderNo, String status) {
        Session session = sessionFactory.getCurrentSession();
        Orders persistentEntity = session.get(Orders.class, orderNo);
        if (persistentEntity != null) {
            if (status.equals(OrderStatus.CANCELED.toString())) {
                persistentEntity.setCancelFlg(BigInteger.ONE);
                persistentEntity.setCancelOn(new Date());
            }
            persistentEntity.setOrderStatus(status);
            session.merge(persistentEntity);
        }
    }

    @Override
    public void addOrder(OrdersDTO orderDTO) {
        Session session = sessionFactory.getCurrentSession();
        Orders order = modelMapper.map(orderDTO, Orders.class);
        order.setCreateOn(new Date());
        session.save(order);
        addOrderDetails(orderDTO.getOrderDetailsList());
    }

    private void addOrderDetails(List<OrdersDetailsDTO> list) {
        Session session = sessionFactory.getCurrentSession();
        if (list != null) {
            for (OrdersDetailsDTO details : list) {
                OrdersDetails ordersDetails = modelMapper.map(details, OrdersDetails.class);
                ordersDetails.setOrdersDetailsPK(new OrdersDetailsPK(details.getOrderNo(), details.getProdNo()));
                session.save(ordersDetails);
            }
        }
    }

}
