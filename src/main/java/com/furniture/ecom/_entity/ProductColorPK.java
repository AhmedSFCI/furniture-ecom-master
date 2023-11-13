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
public class ProductColorPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "color_no")
    private int colorNo;
    @Basic(optional = false)
    @Column(name = "prod_no")
    private Long prodNo;

    public ProductColorPK() {
    }

    public ProductColorPK(int colorNo, Long prodNo) {
        this.colorNo = colorNo;
        this.prodNo = prodNo;
    }

    public int getColorNo() {
        return colorNo;
    }

    public void setColorNo(int colorNo) {
        this.colorNo = colorNo;
    }

    public Long getProdNo() {
        return prodNo;
    }

    public void setProdNo(Long prodNo) {
        this.prodNo = prodNo;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) colorNo;
        hash += (long) prodNo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductColorPK)) {
            return false;
        }
        ProductColorPK other = (ProductColorPK) object;
        if (this.colorNo != other.colorNo) {
            return false;
        }
        if (this.prodNo != other.prodNo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.ProductColorPK[ colorNo=" + colorNo + ", prodNo=" + prodNo + " ]";
    }
    
}
