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
public class ProductMaterialsDTO implements Serializable {

    private Integer prodMatrlNo;
    private Integer materialNo;
    private String materialNm;
    private String prodNm;
    private Long prodNo;

    public ProductMaterialsDTO() {
    }

    public Integer getProdMatrlNo() {
        return prodMatrlNo;
    }

    public void setProdMatrlNo(Integer prodMatrlNo) {
        this.prodMatrlNo = prodMatrlNo;
    }

    public Integer getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(Integer materialNo) {
        this.materialNo = materialNo;
    }

    public String getMaterialNm() {
        return materialNm;
    }

    public void setMaterialNm(String materialNm) {
        this.materialNm = materialNm;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public Long getProdNo() {
        return prodNo;
    }

    public void setProdNo(Long prodNo) {
        this.prodNo = prodNo;
    }

    
}
