/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "about")
@NamedQueries({
    @NamedQuery(name = "About.findAll", query = "SELECT a FROM About a"),
    @NamedQuery(name = "About.findByAboutNo", query = "SELECT a FROM About a WHERE a.aboutNo = :aboutNo"),
    @NamedQuery(name = "About.findByAboutTxtAr", query = "SELECT a FROM About a WHERE a.aboutTxtAr = :aboutTxtAr"),
    @NamedQuery(name = "About.findByAboutTxtEn", query = "SELECT a FROM About a WHERE a.aboutTxtEn = :aboutTxtEn"),
    @NamedQuery(name = "About.findByCreateOn", query = "SELECT a FROM About a WHERE a.createOn = :createOn"),
    @NamedQuery(name = "About.findByEnabled", query = "SELECT a FROM About a WHERE a.enabled = :enabled"),
    @NamedQuery(name = "About.findByOrderNo", query = "SELECT a FROM About a WHERE a.orderNo = :orderNo")})
public class About implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "about_no")
    private Integer aboutNo;
    @Basic(optional = false)
    @Column(name = "about_txt_ar")
    private String aboutTxtAr;
    @Column(name = "about_txt_en")
    private String aboutTxtEn;
    @Basic(optional = false)
    @Column(name = "create_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createOn;
    @Basic(optional = false)
    @Column(name = "enabled")
    private Short enabled;
    @Basic(optional = false)
    @Column(name = "order_no")
    private int orderNo;
    @Column(name = "adm_no")
    private Integer admNo;

    public About() {
    }

    public About(Integer aboutNo) {
        this.aboutNo = aboutNo;
    }

    public About(Integer aboutNo, String aboutTxtAr, Date createOn, short enabled) {
        this.aboutNo = aboutNo;
        this.aboutTxtAr = aboutTxtAr;
        this.createOn = createOn;
        this.enabled = enabled;
    }

    public Integer getAboutNo() {
        return aboutNo;
    }

    public void setAboutNo(Integer aboutNo) {
        this.aboutNo = aboutNo;
    }

    public String getAboutTxtAr() {
        return aboutTxtAr;
    }

    public void setAboutTxtAr(String aboutTxtAr) {
        this.aboutTxtAr = aboutTxtAr;
    }

    public String getAboutTxtEn() {
        return aboutTxtEn;
    }

    public void setAboutTxtEn(String aboutTxtEn) {
        this.aboutTxtEn = aboutTxtEn;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public Short getEnabled() {
        return enabled;
    }

    public void setEnabled(short enabled) {
        this.enabled = enabled;
    }

    public Integer getAdmNo() {
        return admNo;
    }

    public void setAdmNo(Integer admNo) {
        this.admNo = admNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aboutNo != null ? aboutNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof About)) {
            return false;
        }
        About other = (About) object;
        if ((this.aboutNo == null && other.aboutNo != null) || (this.aboutNo != null && !this.aboutNo.equals(other.aboutNo))) {
            return false;
        }
        return true;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity[ aboutNo=" + aboutNo + " ]";
    }

}
