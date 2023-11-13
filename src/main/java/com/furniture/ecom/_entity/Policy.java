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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "policy")
@NamedQueries({
    @NamedQuery(name = "Policy.findAll", query = "SELECT p FROM Policy p"),
    @NamedQuery(name = "Policy.findByPolicyNo", query = "SELECT p FROM Policy p WHERE p.policyNo = :policyNo"),
    @NamedQuery(name = "Policy.findByPolicyTxtAr", query = "SELECT p FROM Policy p WHERE p.policyTxtAr = :policyTxtAr"),
    @NamedQuery(name = "Policy.findByCreateOn", query = "SELECT p FROM Policy p WHERE p.createOn = :createOn"),
    @NamedQuery(name = "Policy.findByEnabled", query = "SELECT p FROM Policy p WHERE p.enabled = :enabled"),
    @NamedQuery(name = "Policy.findByPolicyTxtEn", query = "SELECT p FROM Policy p WHERE p.policyTxtEn = :policyTxtEn"),
    @NamedQuery(name = "Policy.findByOrderNo", query = "SELECT p FROM Policy p WHERE p.orderNo = :orderNo")})
public class Policy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "policy_no")
    private Integer policyNo;
    @Basic(optional = false)
    @Column(name = "policy_txt_ar")
    private String policyTxtAr;
    @Basic(optional = false)
    @Column(name = "create_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createOn;
    @Basic(optional = false)
    @Column(name = "enabled")
    private Short enabled;
    @Column(name = "policy_txt_en")
    private String policyTxtEn;
    @Basic(optional = false)
    @Column(name = "order_no")
    private int orderNo;
    @Column(name = "create_adm")
    private Integer createAdm;

    public Policy() {
    }

    public Policy(Integer policyNo) {
        this.policyNo = policyNo;
    }

    public Policy(Integer policyNo, String policyTxtAr, Date createOn, short enabled) {
        this.policyNo = policyNo;
        this.policyTxtAr = policyTxtAr;
        this.createOn = createOn;
        this.enabled = enabled;
    }

    public Integer getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(Integer policyNo) {
        this.policyNo = policyNo;
    }

    public String getPolicyTxtAr() {
        return policyTxtAr;
    }

    public void setPolicyTxtAr(String policyTxtAr) {
        this.policyTxtAr = policyTxtAr;
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

    public void setEnabled(Short enabled) {
        this.enabled = enabled;
    }

    public String getPolicyTxtEn() {
        return policyTxtEn;
    }

    public void setPolicyTxtEn(String policyTxtEn) {
        this.policyTxtEn = policyTxtEn;
    }

    public Integer getCreateAdm() {
        return createAdm;
    }

    public void setCreateAdm(Integer createAdm) {
        this.createAdm = createAdm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (policyNo != null ? policyNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Policy)) {
            return false;
        }
        Policy other = (Policy) object;
        if ((this.policyNo == null && other.policyNo != null) || (this.policyNo != null && !this.policyNo.equals(other.policyNo))) {
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
        return "com.mycompany.mavenproject1.Policy[ policyNo=" + policyNo + " ]";
    }

}
