/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author HP
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pagging {

    private Integer pageNo;
    private Integer ItmPerPage;
    private String pagingType;
    private Integer typeValue;
    public Pagging() {
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getItmPerPage() {
        if (this.ItmPerPage == null || this.ItmPerPage <= 0) {
            return 10;
        }
        return ItmPerPage;
    }

    public void setItmPerPage(Integer ItmPerPage) {
        this.ItmPerPage = ItmPerPage;
    }

    public String getPagingType() {
        return pagingType;
    }

    public void setPagingType(String pagingType) {
        this.pagingType = pagingType;
    }

    public Integer getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(Integer typeValue) {
        this.typeValue = typeValue;
    }
    

}
