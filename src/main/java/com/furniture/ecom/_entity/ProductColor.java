/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "product_color")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductColor.findAll", query = "SELECT p FROM ProductColor p"),
    @NamedQuery(name = "ProductColor.findByColorNo", query = "SELECT p FROM ProductColor p WHERE p.productColorPK.colorNo = :colorNo"),
    @NamedQuery(name = "ProductColor.findByProdNo", query = "SELECT p FROM ProductColor p WHERE p.productColorPK.prodNo = :prodNo"),
    @NamedQuery(name = "ProductColor.findByColorImg", query = "SELECT p FROM ProductColor p WHERE p.colorImg = :colorImg"),
    @NamedQuery(name = "ProductColor.findBySecondColorNo", query = "SELECT p FROM ProductColor p WHERE p.secondColorNo = :secondColorNo")})

public class ProductColor implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductColorPK productColorPK;
    @Basic(optional = false)
    @Column(name = "color_img")
    private String colorImg;
    @Column(name = "second_color_no")
    private Integer secondColorNo;

    public ProductColor() {
    }

    public ProductColor(ProductColorPK productColorPK) {
        this.productColorPK = productColorPK;
    }

    public ProductColor(ProductColorPK productColorPK, String colorImg) {
        this.productColorPK = productColorPK;
        this.colorImg = colorImg;
    }

    public ProductColor(int colorNo, long prodNo) {
        this.productColorPK = new ProductColorPK(colorNo, prodNo);
    }

    public ProductColorPK getProductColorPK() {
        return productColorPK;
    }

    public void setProductColorPK(ProductColorPK productColorPK) {
        this.productColorPK = productColorPK;
    }

    public String getColorImg() {
        return colorImg;
    }

    public void setColorImg(String colorImg) {
        this.colorImg = colorImg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productColorPK != null ? productColorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductColor)) {
            return false;
        }
        ProductColor other = (ProductColor) object;
        if ((this.productColorPK == null && other.productColorPK != null) || (this.productColorPK != null && !this.productColorPK.equals(other.productColorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.ProductColor[ productColorPK=" + productColorPK + " ]";
    }

    public Integer getSecondColorNo() {
        return secondColorNo;
    }

    public void setSecondColorNo(Integer secondColorNo) {
        this.secondColorNo = secondColorNo;
    }

}
