/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author HP
 */
public class MaterialDTO implements Serializable {

    private Integer materialNo;
    @NotBlank(message = "1-materialNmAr")
    @Length(min = 5, message = "32-materialNmAr")
    private String materialNmAr;
    private String materialNmEn;
    private String materialNm;
    private List<ProductDTO> products;

    public MaterialDTO(){
        
    }
    public Integer getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(Integer materialNo) {
        this.materialNo = materialNo;
    }

    public String getMaterialNmAr() {
        return materialNmAr;
    }

    public void setMaterialNmAr(String materialNmAr) {
        this.materialNmAr = materialNmAr;
    }

    public String getMaterialNmEn() {
        return materialNmEn;
    }

    public void setMaterialNmEn(String materialNmEn) {
        this.materialNmEn = materialNmEn;
    }

    public String getMaterialNm() {
        return materialNm;
    }

    public void setMaterialNm(String materialNm) {
        this.materialNm = materialNm;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

}
