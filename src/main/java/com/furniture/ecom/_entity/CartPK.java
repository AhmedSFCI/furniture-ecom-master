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

/**
 *
 * @author HP
 */
@Embeddable
public class CartPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cust_no")
    private int custNo;
    @Basic(optional = false)
    @Column(name = "prod_no")
    private int prodNo;

    public CartPK() {
    }

    public CartPK(int custNo, int prodNo) {
        this.custNo = custNo;
        this.prodNo = prodNo;
    }

    public int getCustNo() {
        return custNo;
    }

    public void setCustNo(int custNo) {
        this.custNo = custNo;
    }

    public int getProdNo() {
        return prodNo;
    }

    public void setProdNo(int prodNo) {
        this.prodNo = prodNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) custNo;
        hash += (int) prodNo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CartPK)) {
            return false;
        }
        CartPK other = (CartPK) object;
        if (this.custNo != other.custNo) {
            return false;
        }
        if (this.prodNo != other.prodNo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ahmed.test.CartPK[ custNo=" + custNo + ", prodNo=" + prodNo + " ]";
    }
    
}
