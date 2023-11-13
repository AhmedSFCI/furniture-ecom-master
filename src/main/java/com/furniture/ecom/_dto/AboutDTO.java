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
public class AboutDTO implements Serializable {

    private Integer aboutNo;
    @NotBlank(message = "1-aboutTxtAr")
    @Length(min = 50, message = "19-aboutTxtAr")
    private String aboutTxtAr;
    private String aboutTxtEn;

    private String aboutTxt;

    private Date createOn;

    private Short enabled;

    private Integer orderNo;

    private Integer admNo;
    
    private List<ImageDTO> images;

    public AboutDTO() {
    }

    public AboutDTO(Integer aboutNo) {
        this.aboutNo = aboutNo;
    }

    public AboutDTO(Integer aboutNo, String aboutTxtAr, String aboutTxtEn, Date createOn, short enabled, Integer admNo) {
        this.aboutNo = aboutNo;
        this.aboutTxtAr = aboutTxtAr;
        this.aboutTxtEn = aboutTxtEn;
        this.createOn = createOn;
        this.enabled = enabled;
        this.admNo = admNo;
    }

    public Integer getAboutNo() {
        return aboutNo;
    }

    public void setAboutNo(Integer aboutNo) {
        this.aboutNo = aboutNo;
    }

    public String getAboutTxtAr() {
        return aboutTxtAr;
    }

    public void setAboutTxtAr(String aboutTxtAr) {
        this.aboutTxtAr = aboutTxtAr;
    }

    public String getAboutTxtEn() {
        return aboutTxtEn;
    }

    public void setAboutTxtEn(String aboutTxtEn) {
        this.aboutTxtEn = aboutTxtEn;
    }

    public String getAboutTxt() {
        return aboutTxt;
    }

    public void setAboutTxt(String aboutTxt) {
        this.aboutTxt = aboutTxt;
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

    public Integer getAdmNo() {
        return admNo;
    }

    public void setAdmNo(Integer admNo) {
        this.admNo = admNo;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }
    
    @Override
    public String toString() {
        return "com.furniture.ecom.dto.About[ aboutNo=" + aboutNo + " ]";
    }

}
