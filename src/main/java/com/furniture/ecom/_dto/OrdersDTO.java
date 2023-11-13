/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * @author HP
 */
public class OrdersDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer orderNo;
    private int totalQuantity;
    private BigDecimal totalPrice;
    private String orderStatus;
    @NotBlank(message = "1-orderAddress")
    private String orderAddress;
    private Date createOn;
    private BigInteger cancelFlg;
    private Date cancelOn;
    @NotNull(message = "1-payType")
    private String payType;
    private Date deliveryDate;
    @NotNull(message = "1-cityNo")
    private Integer cityNo;
    private Integer couponNo;
    private Integer taxNo;
    private String orderStatusNm;
    private String customerName;
   // @NotNull(message = "1-custNo")
    private Integer custNo;
    @NotEmpty(message = "1-orderDetails")
    private List<OrdersDetailsDTO> orderDetailsList;
    public OrdersDTO() {
    }

    public OrdersDTO(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public BigInteger getCancelFlg() {
        return cancelFlg;
    }

    public void setCancelFlg(BigInteger cancelFlg) {
        this.cancelFlg = cancelFlg;
    }

    public Date getCancelOn() {
        return cancelOn;
    }

    public void setCancelOn(Date cancelOn) {
        this.cancelOn = cancelOn;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getCityNo() {
        return cityNo;
    }

    public void setCityNo(Integer cityNo) {
        this.cityNo = cityNo;
    }

    public Integer getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(Integer couponNo) {
        this.couponNo = couponNo;
    }

    public Integer getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(Integer taxNo) {
        this.taxNo = taxNo;
    }

    public String getOrderStatusNm() {
        return orderStatusNm;
    }

    public void setOrderStatusNm(String orderStatusNm) {
        this.orderStatusNm = orderStatusNm;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getCustNo() {
        return custNo;
    }

    public void setCustNo(Integer custNo) {
        this.custNo = custNo;
    }

    public List<OrdersDetailsDTO> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrdersDetailsDTO> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    
}
