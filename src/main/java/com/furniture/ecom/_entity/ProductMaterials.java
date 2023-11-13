/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "product_materials")
@NamedQueries({
    @NamedQuery(name = "ProductMaterials.findAll", query = "SELECT p FROM ProductMaterials p"),
    @NamedQuery(name = "ProductMaterials.findByProdMatrlNo", query = "SELECT p FROM ProductMaterials p WHERE p.prodMatrlNo = :prodMatrlNo")})
public class ProductMaterials implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prod_matrl_no")
    private Integer prodMatrlNo;
    @Column(name = "material_no")
    private Integer materialNo;
    @Column(name = "prod_no")
    private Long prodNo;

    public ProductMaterials() {
    }

    public ProductMaterials(Integer prodMatrlNo) {
        this.prodMatrlNo = prodMatrlNo;
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

    public Long getProdNo() {
        return prodNo;
    }

    public void setProdNo(Long prodNo) {
        this.prodNo = prodNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prodMatrlNo != null ? prodMatrlNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductMaterials)) {
            return false;
        }
        ProductMaterials other = (ProductMaterials) object;
        if ((this.prodMatrlNo == null && other.prodMatrlNo != null) || (this.prodMatrlNo != null && !this.prodMatrlNo.equals(other.prodMatrlNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ahmed.test.ProductMaterials[ prodMatrlNo=" + prodMatrlNo + " ]";
    }

}
