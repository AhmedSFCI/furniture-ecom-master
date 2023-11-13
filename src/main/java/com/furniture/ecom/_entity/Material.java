/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "material")
@NamedQueries({
    @NamedQuery(name = "Material.findAll", query = "SELECT m FROM Material m"),
    @NamedQuery(name = "Material.findByMaterialNo", query = "SELECT m FROM Material m WHERE m.materialNo = :materialNo"),
    @NamedQuery(name = "Material.findByMaterialNmAr", query = "SELECT m FROM Material m WHERE m.materialNmAr = :materialNmAr"),
    @NamedQuery(name = "Material.findByMaterialNmEn", query = "SELECT m FROM Material m WHERE m.materialNmEn = :materialNmEn")})
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "material_no")
    private Integer materialNo;
    @Basic(optional = false)
    @Column(name = "material_nm_ar")
    private String materialNmAr;
    @Column(name = "material_nm_en")
    private String materialNmEn;
    public Material() {
    }

    public Material(Integer materialNo) {
        this.materialNo = materialNo;
    }

    public Material(Integer materialNo, String materialNmAr) {
        this.materialNo = materialNo;
        this.materialNmAr = materialNmAr;
    }

    public Integer getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(Integer materialNo) {
        this.materialNo = materialNo;
    }

    public String getMaterialNmAr() {
        return materialNmAr;
    }

    public void setMaterialNmAr(String materialNmAr) {
        this.materialNmAr = materialNmAr;
    }

    public String getMaterialNmEn() {
        return materialNmEn;
    }

    public void setMaterialNmEn(String materialNmEn) {
        this.materialNmEn = materialNmEn;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (materialNo != null ? materialNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Material)) {
            return false;
        }
        Material other = (Material) object;
        if ((this.materialNo == null && other.materialNo != null) || (this.materialNo != null && !this.materialNo.equals(other.materialNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ahmed.test.Material[ materialNo=" + materialNo + " ]";
    }
    
}
