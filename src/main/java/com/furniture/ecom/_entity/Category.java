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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "category")
@NamedQueries({
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c"),
    @NamedQuery(name = "Category.findByCatNo", query = "SELECT c FROM Category c WHERE c.catNo = :catNo"),
    @NamedQuery(name = "Category.findByCatNameAr", query = "SELECT c FROM Category c WHERE c.catNameAr = :catNameAr"),
    @NamedQuery(name = "Category.findByCatNameEn", query = "SELECT c FROM Category c WHERE c.catNameEn = :catNameEn"),
    @NamedQuery(name = "Category.findByCatDscAr", query = "SELECT c FROM Category c WHERE c.catDscAr = :catDscAr"),
    @NamedQuery(name = "Category.findByCatDscEn", query = "SELECT c FROM Category c WHERE c.catDscEn = :catDscEn")})
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cat_no")
    private Integer catNo;
    @Basic(optional = false)
    @Column(name = "cat_name_ar")
    private String catNameAr;
    @Column(name = "cat_name_en")
    private String catNameEn;
    @Column(name = "cat_dsc_ar")
    private String catDscAr;
    @Column(name = "cat_dsc_en")
    private String catDscEn;

    public Category() {
    }

    public Category(Integer catNo) {
        this.catNo = catNo;
    }

    public Category(Integer catNo, String catNameAr) {
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
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.catNo == null && other.catNo != null) || (this.catNo != null && !this.catNo.equals(other.catNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.Category[ catNo=" + catNo + " ]";
    }

}
