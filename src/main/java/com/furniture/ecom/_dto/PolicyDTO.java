/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author HP
 */
public class PolicyDTO implements Serializable {

    private Integer policyNo;
    @NotBlank(message = "1-policyTxtAr")
    @Length(min = 50, message = "19-policyTxtAr")
    private String policyTxtAr;
    private String policyTxtEn;

    private String policyTxt;

    private Date createOn;

    private Short enabled;
    private Integer orderNo;

    private Integer createAdm;
    private List<ImageDTO> images;

    public PolicyDTO() {
    }

    public PolicyDTO(Integer policyNo) {
        this.policyNo = policyNo;
    }

    public PolicyDTO(Integer policyNo, String policyTxtAr, String policyTxtEn, String policyTxt, Date createOn, short enabled, Integer createAdm, List<ImageDTO> images) {
        this.policyNo = policyNo;
        this.policyTxtAr = policyTxtAr;
        this.policyTxtEn = policyTxtEn;
        this.policyTxt = policyTxt;
        this.createOn = createOn;
        this.enabled = enabled;
        this.createAdm = createAdm;
        this.images = images;
    }

    public Integer getCreateAdm() {
        return createAdm;
    }

    public void setCreateAdm(Integer createAdm) {
        this.createAdm = createAdm;
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }

    public String getPolicyTxtAr() {
        return policyTxtAr;
    }

    public void setPolicyTxtAr(String policyTxtAr) {
        this.policyTxtAr = policyTxtAr;
    }

    public String getPolicyTxtEn() {
        return policyTxtEn;
    }

    public void setPolicyTxtEn(String policyTxtEn) {
        this.policyTxtEn = policyTxtEn;
    }

    public Integer getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(Integer policyNo) {
        this.policyNo = policyNo;
    }

    public String getPolicyTxt() {
        return policyTxt;
    }

    public void setPolicyTxt(String policyTxt) {
        this.policyTxt = policyTxt;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (policyNo != null ? policyNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PolicyDTO)) {
            return false;
        }
        PolicyDTO other = (PolicyDTO) object;
        if ((this.policyNo == null && other.policyNo != null) || (this.policyNo != null && !this.policyNo.equals(other.policyNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.Policies[ policyNo=" + policyNo + " ]";
    }

}
