/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "orders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findByOrderNo", query = "SELECT o FROM Orders o WHERE o.orderNo = :orderNo"),
    @NamedQuery(name = "Orders.findByTotalQuantity", query = "SELECT o FROM Orders o WHERE o.totalQuantity = :totalQuantity"),
    @NamedQuery(name = "Orders.findByTotalPrice", query = "SELECT o FROM Orders o WHERE o.totalPrice = :totalPrice"),
    @NamedQuery(name = "Orders.findByOrderAddress", query = "SELECT o FROM Orders o WHERE o.orderAddress = :orderAddress"),
    @NamedQuery(name = "Orders.findByCreateOn", query = "SELECT o FROM Orders o WHERE o.createOn = :createOn"),
    @NamedQuery(name = "Orders.findByCancelFlg", query = "SELECT o FROM Orders o WHERE o.cancelFlg = :cancelFlg"),
    @NamedQuery(name = "Orders.findByCancelOn", query = "SELECT o FROM Orders o WHERE o.cancelOn = :cancelOn"),
    @NamedQuery(name = "Orders.findByDeliveryDate", query = "SELECT o FROM Orders o WHERE o.deliveryDate = :deliveryDate"),
    @NamedQuery(name = "Orders.findByOrderStatus", query = "SELECT o FROM Orders o WHERE o.orderStatus = :orderStatus"),
    @NamedQuery(name = "Orders.findByPayType", query = "SELECT o FROM Orders o WHERE o.payType = :payType")})
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "order_no")
    private Integer orderNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_quantity")
    private int totalQuantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Basic(optional = false)
    @Column(name = "order_status")
    private String orderStatus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "order_address")
    private String orderAddress;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pay_type")
    private String payType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createOn;
    @Column(name = "cancel_flg")
    private BigInteger cancelFlg;
    @Column(name = "cancel_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelOn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "delivery_date")
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;
    @Column(name = "city_no")
    private Integer cityNo;
    @Column(name = "coupon_no")
    private Integer couponNo;
    @Basic(optional = false)
    @Column(name = "tax_no")
    private Integer taxNo;
    @Column(name = "cust_no")
    private Integer custNo;

    public Orders() {
    }

    public Orders(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Orders(Integer orderNo, int totalQuantity, BigDecimal totalPrice, String orderStatus, String orderAddress, String payType, Date createOn, BigDecimal shippingPrice, Date deliveryDate) {
        this.orderNo = orderNo;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.orderAddress = orderAddress;
        this.payType = payType;
        this.createOn = createOn;
        this.deliveryDate = deliveryDate;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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

    public Integer getCustNo() {
        return custNo;
    }

    public void setCustNo(Integer custNo) {
        this.custNo = custNo;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderNo != null ? orderNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.orderNo == null && other.orderNo != null) || (this.orderNo != null && !this.orderNo.equals(other.orderNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.Orders[ orderNo=" + orderNo + " ]";
    }

}
