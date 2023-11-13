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
@Table(name = "style")
@NamedQueries({
    @NamedQuery(name = "Style.findAll", query = "SELECT s FROM Style s"),
    @NamedQuery(name = "Style.findByStyleNo", query = "SELECT s FROM Style s WHERE s.styleNo = :styleNo"),
    @NamedQuery(name = "Style.findByStyleNmAr", query = "SELECT s FROM Style s WHERE s.styleNmAr = :styleNmAr"),
    @NamedQuery(name = "Style.findByStyleNmEn", query = "SELECT s FROM Style s WHERE s.styleNmEn = :styleNmEn")})
public class Style implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "style_no")
    private Integer styleNo;
    @Basic(optional = false)
    @Column(name = "style_nm_ar")
    private String styleNmAr;
    @Column(name = "style_nm_en")
    private String styleNmEn;

    public Style() {
    }

    public Style(Integer styleNo) {
        this.styleNo = styleNo;
    }

    public Style(Integer styleNo, String styleNmAr) {
        this.styleNo = styleNo;
        this.styleNmAr = styleNmAr;
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

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (styleNo != null ? styleNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Style)) {
            return false;
        }
        Style other = (Style) object;
        if ((this.styleNo == null && other.styleNo != null) || (this.styleNo != null && !this.styleNo.equals(other.styleNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ahmed.test.Style[ styleNo=" + styleNo + " ]";
    }
    
}
