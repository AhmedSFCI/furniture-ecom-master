/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import java.io.Serializable;

/**
 *
 * @author HP
 */
public class ProductColorDTO implements Serializable {

    private String colorImg;
    
    private Integer secondColorNo;
    private Integer colorNo;
    private Long prodNo;
    private String secondColorNm;
    private String colorNm;

    public ProductColorDTO() {
    }

    public String getColorImg() {
        return colorImg;
    }

    public void setColorImg(String colorImg) {
        this.colorImg = colorImg;
    }

    public Integer getSecondColorNo() {
        return secondColorNo;
    }

    public void setSecondColorNo(Integer secondColorNo) {
        this.secondColorNo = secondColorNo;
    }

    public Integer getColorNo() {
        return colorNo;
    }

    public void setColorNo(int colorNo) {
        this.colorNo = colorNo;
    }

    public String getSecondColorNm() {
        return secondColorNm;
    }

    public void setSecondColorNm(String secondColorNm) {
        this.secondColorNm = secondColorNm;
    }

    public String getColorNm() {
        return colorNm;
    }

    public void setColorNm(String colorNm) {
        this.colorNm = colorNm;
    }

    public Long getProdNo() {
        return prodNo;
    }

    public void setProdNo(Long prodNo) {
        this.prodNo = prodNo;
    }

    
}
