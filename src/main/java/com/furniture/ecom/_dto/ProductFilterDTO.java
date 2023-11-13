/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import java.util.List;
import java.util.Set;

/**
 *
 * @author HP
 */
public class ProductFilterDTO {
    
    private Integer pageNo;
    private Integer itemPerPage;
    private Double minPrice;
    private Double maxPrice;
    private Short availablility;
    private Set<Integer> categoryList;
    private Set<Integer> materialList;
    private Set<Integer> styleList;
    private Set<Integer> colorList;
    private Set<Integer> finishList;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getItemPerPage() {
        return itemPerPage;
    }

    public void setItemPerPage(Integer itemPerPage) {
        this.itemPerPage = itemPerPage;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Short getAvailablility() {
        return availablility;
    }

    public void setAvailablility(Short availablility) {
        this.availablility = availablility;
    }

    public Set<Integer> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(Set<Integer> categoryList) {
        this.categoryList = categoryList;
    }

    public Set<Integer> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(Set<Integer> materialList) {
        this.materialList = materialList;
    }

    public Set<Integer> getStyleList() {
        return styleList;
    }

    public void setStyleList(Set<Integer> styleList) {
        this.styleList = styleList;
    }

    public Set<Integer> getColorList() {
        return colorList;
    }

    public void setColorList(Set<Integer> colorList) {
        this.colorList = colorList;
    }

    public Set<Integer> getFinishList() {
        return finishList;
    }

    public void setFinishList(Set<Integer> finishList) {
        this.finishList = finishList;
    }

    
    
    
}
