/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author HP
 */
public class OrdersDetailsDTO implements Serializable {

    private Integer orderNo;
    @NotNull(message = "1-prodNo")
    private Long prodNo;

    private String prodNm;
    @NotNull(message = "1-prodQunatity")
    private Integer prodQunatity;

    private BigDecimal prodPrice;

    private BigDecimal prodTotalPrice;

    public OrdersDetailsDTO() {
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public Long getProdNo() {
        return prodNo;
    }

    public void setProdNo(Long prodNo) {
        this.prodNo = prodNo;
    }

    public Integer getProdQunatity() {
        return prodQunatity;
    }

    public void setProdQunatity(Integer prodQunatity) {
        this.prodQunatity = prodQunatity;
    }

    public BigDecimal getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(BigDecimal prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public BigDecimal getProdTotalPrice() {
        return prodTotalPrice;
    }

    public void setProdTotalPrice(BigDecimal prodTotalPrice) {
        this.prodTotalPrice = prodTotalPrice;
    }

}
