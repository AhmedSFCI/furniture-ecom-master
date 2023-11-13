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
public class StyleDTO implements Serializable {

    private Integer styleNo;
    @NotBlank(message = "1-styleNmAr")
    @Length(min = 3, message = "32-styleNmAr")
    private String styleNmAr;
    private String styleNmEn;
    private String styleNm;
    private List<ProductDTO> products;

    public StyleDTO() {
    }

    
    public StyleDTO(Integer styleNo, String styleNmAr, String styleNmEn) {
        this.styleNo = styleNo;
        this.styleNmAr = styleNmAr;
        this.styleNmEn = styleNmEn;
    }

    public Integer getStyleNo() {
        return styleNo;
    }

    public void setStyleNo(Integer styleNo) {
        this.styleNo = styleNo;
    }

    public String getStyleNmAr() {
        return styleNmAr;
    }

    public void setStyleNmAr(String styleNmAr) {
        this.styleNmAr = styleNmAr;
    }

    public String getStyleNmEn() {
        return styleNmEn;
    }

    public void setStyleNmEn(String styleNmEn) {
        this.styleNmEn = styleNmEn;
    }

    public String getStyleNm() {
        return styleNm;
    }

    public void setStyleNm(String styleNm) {
        this.styleNm = styleNm;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

}
