/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "orders_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdersDetails.findAll", query = "SELECT o FROM OrdersDetails o"),
    @NamedQuery(name = "OrdersDetails.findByProdQunatity", query = "SELECT o FROM OrdersDetails o WHERE o.prodQunatity = :prodQunatity"),
    @NamedQuery(name = "OrdersDetails.findByProdPrice", query = "SELECT o FROM OrdersDetails o WHERE o.prodPrice = :prodPrice")})
public class OrdersDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrdersDetailsPK ordersDetailsPK;
    @Column(name = "prod_qunatity")
    private Integer prodQunatity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prod_price")
    private BigDecimal prodPrice;

    public OrdersDetails() {
    }

    public OrdersDetails(OrdersDetailsPK ordersDetailsPK) {
        this.ordersDetailsPK = ordersDetailsPK;
    }

    public OrdersDetails(int orderNo, int prodNo) {
        this.ordersDetailsPK = new OrdersDetailsPK(orderNo, prodNo);
    }

    public OrdersDetailsPK getOrdersDetailsPK() {
        return ordersDetailsPK;
    }

    public void setOrdersDetailsPK(OrdersDetailsPK ordersDetailsPK) {
        this.ordersDetailsPK = ordersDetailsPK;
    }

    public Integer getProdQunatity() {
        return prodQunatity;
    }

    public void setProdQunatity(Integer prodQunatity) {
        this.prodQunatity = prodQunatity;
    }

    public BigDecimal getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(BigDecimal prodPrice) {
        this.prodPrice = prodPrice;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordersDetailsPK != null ? ordersDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdersDetails)) {
            return false;
        }
        OrdersDetails other = (OrdersDetails) object;
        if ((this.ordersDetailsPK == null && other.ordersDetailsPK != null) || (this.ordersDetailsPK != null && !this.ordersDetailsPK.equals(other.ordersDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.OrdersDetails[ ordersDetailsPK=" + ordersDetailsPK + " ]";
    }
    
}
