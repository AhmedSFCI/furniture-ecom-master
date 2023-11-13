/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author HP
 */

public class TaxesDTO implements Serializable {

   
    private Integer taxNo;
    
    private Double percentage;

    private Date createOn;
   
    private Short enabled;
    
    private Integer createAdm;
    

    public TaxesDTO() {
    }

    public TaxesDTO(Integer taxNo) {
        this.taxNo = taxNo;
    }

    public TaxesDTO(Integer taxNo, Double taxRate, Date createOn, short enabled) {
        this.taxNo = taxNo;
        this.percentage = taxRate;
        this.createOn = createOn;
        this.enabled = enabled;
    }

    public Integer getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(Integer taxNo) {
        this.taxNo = taxNo;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    
    

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public Short getEnabled() {
        return enabled;
    }

    public void setEnabled(Short enabled) {
        this.enabled = enabled;
    }

    public Integer getCreateAdm() {
        return createAdm;
    }

    public void setCreateAdm(Integer createAdm) {
        this.createAdm = createAdm;
    }

    

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taxNo != null ? taxNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TaxesDTO)) {
            return false;
        }
        TaxesDTO other = (TaxesDTO) object;
        if ((this.taxNo == null && other.taxNo != null) || (this.taxNo != null && !this.taxNo.equals(other.taxNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.Taxes[ taxNo=" + taxNo + " ]";
    }
    
}
