/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author HP
 */
@Embeddable
public class OrdersDetailsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "order_no")
    private int orderNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prod_no")
    private long prodNo;

    public OrdersDetailsPK() {
    }

    public OrdersDetailsPK(int orderNo, long prodNo) {
        this.orderNo = orderNo;
        this.prodNo = prodNo;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public long getProdNo() {
        return prodNo;
    }

    public void setProdNo(long prodNo) {
        this.prodNo = prodNo;
    }

  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) orderNo;
        hash += (int) prodNo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdersDetailsPK)) {
            return false;
        }
        OrdersDetailsPK other = (OrdersDetailsPK) object;
        if (this.orderNo != other.orderNo) {
            return false;
        }
        if (this.prodNo != other.prodNo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.OrdersDetailsPK[ orderNo=" + orderNo + ", prodNo=" + prodNo + " ]";
    }
    
}
