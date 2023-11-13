/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._entity;

import java.io.Serializable;
import java.util.List;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "language")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Language.findAll", query = "SELECT l FROM Language l"),
    @NamedQuery(name = "Language.findByLangNo", query = "SELECT l FROM Language l WHERE l.langNo = :langNo"),
    @NamedQuery(name = "Language.findByLangNameAr", query = "SELECT l FROM Language l WHERE l.langNameAr = :langNameAr"),
    @NamedQuery(name = "Language.findByLangNameEn", query = "SELECT l FROM Language l WHERE l.langNameEn = :langNameEn")})
public class Language implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "lang_name_ar")
    private String langNameAr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "lang_name_en")
    private String langNameEn;
     private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lang_no")
    private Integer langNo;
    

    public Language() {
    }

    public Language(Integer langNo) {
        this.langNo = langNo;
    }

    public Language(Integer langNo, String langNameAr, String langNameEn) {
        this.langNo = langNo;
        this.langNameAr = langNameAr;
        this.langNameEn = langNameEn;
    }

    public Integer getLangNo() {
        return langNo;
    }

    public void setLangNo(Integer langNo) {
        this.langNo = langNo;
    }

    public String getLangNameAr() {
        return langNameAr;
    }

    public void setLangNameAr(String langNameAr) {
        this.langNameAr = langNameAr;
    }

    public String getLangNameEn() {
        return langNameEn;
    }

    public void setLangNameEn(String langNameEn) {
        this.langNameEn = langNameEn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (langNo != null ? langNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Language)) {
            return false;
        }
        Language other = (Language) object;
        if ((this.langNo == null && other.langNo != null) || (this.langNo != null && !this.langNo.equals(other.langNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.Language[ langNo=" + langNo + " ]";
    }

   
}
