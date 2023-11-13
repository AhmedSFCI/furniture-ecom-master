/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author HP
 */

public class DiscountDTO implements Serializable {

    private Integer discountNo;
  
    private Double discountRate;
    
    private Date createOn;
    
    private Date startDate;
    
    private Double discountMaxValue;
    
    private Date expirDate;
    
    private Short enabled;
    
    private Integer createAdm;

    public DiscountDTO() {

    }

    public DiscountDTO(Integer discountNo) {
        this.discountNo = discountNo;
    }

    public DiscountDTO(Integer discountNo, Double percentage, Date createOn, Date startDate, short enabled) {
        this.discountNo = discountNo;
        this.discountRate = percentage;
        this.createOn = createOn;
        this.startDate = startDate;
        this.enabled = enabled;
    }

    public Integer getDiscountNo() {
        return discountNo;
    }

    public void setDiscountNo(Integer discountNo) {
        this.discountNo = discountNo;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpirDate() {
        return expirDate;
    }

    public void setExpirDate(Date expirDate) {
        this.expirDate = expirDate;
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

    public Double getDiscountMaxValue() {
        return discountMaxValue;
    }

    public void setDiscountMaxValue(Double discountMaxValue) {
        this.discountMaxValue = discountMaxValue;
    }

    

 

    @Override
    public String toString() {
        return "com.furniture.ecom.dto.Discount[ discountNo=" + discountNo + " ]";
    }

}
