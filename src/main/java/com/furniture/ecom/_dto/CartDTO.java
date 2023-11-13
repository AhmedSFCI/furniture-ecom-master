/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author HP
 */
public class CartDTO implements Serializable {

    private Integer custNo;
    private Integer prodNo;
    private Double totalPrice;
    private Integer quantity;
    private Date addedDate;
    private ProductDTO product;

    public CartDTO() {
    }

    public Integer getCustNo() {
        return custNo;
    }

    public void setCustNo(Integer custNo) {
        this.custNo = custNo;
    }

    public Integer getProdNo() {
        return prodNo;
    }

    public void setProdNo(Integer prodNo) {
        this.prodNo = prodNo;
    }

  

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
    

    @Override
    public String toString() {
        return "CartDTO{" + "custNo=" + custNo + ", prodNo=" + prodNo  + ", totalPrice=" + totalPrice + ", quantity=" + quantity + ", addedDate=" + addedDate + '}';
    }

   

}
