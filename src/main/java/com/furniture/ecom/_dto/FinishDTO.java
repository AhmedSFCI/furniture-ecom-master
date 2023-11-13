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
 * 01099077621
 * @author HP
 */
public class FinishDTO implements Serializable {


    
    private Integer finishNo;
    @NotBlank(message = "1-finishNmAr")
    @Length(min = 5, message = "32-finishNmAr")
    private String finishNmAr;
    
    private String finishNmEn;
    private String finishNm;
    private List<ProductDTO> products;

    public FinishDTO() {
    }
            

    public Integer getFinishNo() {
        return finishNo;
    }

    public void setFinishNo(Integer finishNo) {
        this.finishNo = finishNo;
    }

    public String getFinishNmAr() {
        return finishNmAr;
    }

    public void setFinishNmAr(String finishNmAr) {
        this.finishNmAr = finishNmAr;
    }

    public String getFinishNmEn() {
        return finishNmEn;
    }

    public void setFinishNmEn(String finishNmEn) {
        this.finishNmEn = finishNmEn;
    }

    public String getFinishNm() {
        return finishNm;
    }

    public void setFinishNm(String finishNm) {
        this.finishNm = finishNm;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
    

  
}
