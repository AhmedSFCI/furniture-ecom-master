/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author HP
 */
public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer catNo;
    @NotBlank(message = "1-catNameAr")
    private String catNameAr;
    private String catNameEn;
    private String catDsc;
    private String catDscAr;
    private String catDscEn;
    private String categoryName;
    private List<ImageDTO> images;
    private List<ProductDTO> products;
    private Long noOfProducts ;

    public CategoryDTO() {
    }

    public CategoryDTO(Integer catNo) {
        this.catNo = catNo;
    }

    public CategoryDTO(Integer catNo, String catNameAr) {
        this.catNo = catNo;
        this.catNameAr = catNameAr;
    }

    public Integer getCatNo() {
        return catNo;
    }

    public void setCatNo(Integer catNo) {
        this.catNo = catNo;
    }

    public String getCatNameAr() {
        return catNameAr;
    }

    public void setCatNameAr(String catNameAr) {
        this.catNameAr = catNameAr;
    }

    public String getCatNameEn() {
        return catNameEn;
    }

    public void setCatNameEn(String catNameEn) {
        this.catNameEn = catNameEn;
    }

    public String getCatDsc() {
        return catDsc;
    }

    public void setCatDsc(String catDsc) {
        this.catDsc = catDsc;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCatDscAr() {
        return catDscAr;
    }

    public void setCatDscAr(String catDscAr) {
        this.catDscAr = catDscAr;
    }

    public String getCatDscEn() {
        return catDscEn;
    }

    public void setCatDscEn(String catDscEn) {
        this.catDscEn = catDscEn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catNo != null ? catNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoryDTO)) {
            return false;
        }
        CategoryDTO other = (CategoryDTO) object;
        if ((this.catNo == null && other.catNo != null) || (this.catNo != null && !this.catNo.equals(other.catNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.Category[ catNo=" + catNo + " ]";
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public Long getNoOfProducts() {
        return noOfProducts;
    }

    public void setNoOfProducts(Long noOfProducts) {
        this.noOfProducts = noOfProducts;
    }

  
    

}
