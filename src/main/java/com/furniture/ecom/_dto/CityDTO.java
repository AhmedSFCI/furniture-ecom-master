/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Mostafa Atta
 */
public class CityDTO implements Serializable {

    private Integer cityNo;
    @NotBlank(message = "1-cityNmAr")
    @Length(max = 30, message = "19-cityNmAr")
    private String cityNmAr;
    private String cityNmEn;
    private double shipPrice;
    private String cityTxt;
    private Date createOn;
    private Short enabled;
    private Integer createAdm;


    public CityDTO() {
    }

    public CityDTO(Integer cityNo, String cityNmAr, String cityNmEn, double shipPrice, String cityTxt, Date createOn, Short enabled, Integer createAdm) {
        this.cityNo = cityNo;
        this.cityNmAr = cityNmAr;
        this.cityNmEn = cityNmEn;
        this.shipPrice = shipPrice;
        this.cityTxt = cityTxt;
        this.createOn = createOn;
        this.enabled = enabled;
        this.createAdm = createAdm;
    }

    public CityDTO(String cityNmAr, String cityNmEn, double shipPrice) {
        this.cityNmAr = cityNmAr;
        this.cityNmEn = cityNmEn;
        this.shipPrice = shipPrice;
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

    public String getCityTxt() {
        return cityTxt;
    }

    public void setCityTxt(String cityTxt) {
        this.cityTxt = cityTxt;
    }


    public Integer getCityNo() {
        return cityNo;
    }

    public void setCityNo(Integer cityNo) {
        this.cityNo = cityNo;
    }

    public String getCityNmAr() {
        return cityNmAr;
    }

    public void setCityNmAr(String cityNmAr) {
        this.cityNmAr = cityNmAr;
    }

    public String getCityNmEn() {
        return cityNmEn;
    }

    public void setCityNmEn(String cityNmEn) {
        this.cityNmEn = cityNmEn;
    }

    public double getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(double shipPrice) {
        this.shipPrice = shipPrice;
    }

}
