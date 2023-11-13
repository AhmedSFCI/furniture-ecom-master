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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "color")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Color.findAll", query = "SELECT c FROM Color c"),
    @NamedQuery(name = "Color.findByColorNo", query = "SELECT c FROM Color c WHERE c.colorNo = :colorNo"),
    @NamedQuery(name = "Color.findByColorNmAr", query = "SELECT c FROM Color c WHERE c.colorNmAr = :colorNmAr"),
    @NamedQuery(name = "Color.findByColorNmEn", query = "SELECT c FROM Color c WHERE c.colorNmEn = :colorNmEn"),
    @NamedQuery(name = "Color.findByColorCode", query = "SELECT c FROM Color c WHERE c.colorCode = :colorCode")})
public class Color implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "color_no")
    private Integer colorNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "color_nm_ar")
    private String colorNmAr;
    @Size(max = 100)
    @Column(name = "color_nm_en")
    private String colorNmEn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "color_code")
    private String colorCode;

    public Color() {
    }

    public Color(Integer colorNo) {
        this.colorNo = colorNo;
    }

    public Color(Integer colorNo, String colorNmAr, String colorCode) {
        this.colorNo = colorNo;
        this.colorNmAr = colorNmAr;
        this.colorCode = colorCode;
    }

    public Integer getColorNo() {
        return colorNo;
    }

    public void setColorNo(Integer colorNo) {
        this.colorNo = colorNo;
    }

    public String getColorNmAr() {
        return colorNmAr;
    }

    public void setColorNmAr(String colorNmAr) {
        this.colorNmAr = colorNmAr;
    }

    public String getColorNmEn() {
        return colorNmEn;
    }

    public void setColorNmEn(String colorNmEn) {
        this.colorNmEn = colorNmEn;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (colorNo != null ? colorNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Color)) {
            return false;
        }
        Color other = (Color) object;
        if ((this.colorNo == null && other.colorNo != null) || (this.colorNo != null && !this.colorNo.equals(other.colorNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.Color[ colorNo=" + colorNo + " ]";
    }
}