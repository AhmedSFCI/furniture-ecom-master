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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "taxes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taxes.findAll", query = "SELECT t FROM Taxes t"),
    @NamedQuery(name = "Taxes.findByTaxNo", query = "SELECT t FROM Taxes t WHERE t.taxNo = :taxNo"),
    @NamedQuery(name = "Taxes.findByPercentage", query = "SELECT t FROM Taxes t WHERE t.percentage = :percentage"),
    @NamedQuery(name = "Taxes.findByCreateOn", query = "SELECT t FROM Taxes t WHERE t.createOn = :createOn"),
    @NamedQuery(name = "Taxes.findByEnabled", query = "SELECT t FROM Taxes t WHERE t.enabled = :enabled")})
public class Taxes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tax_no")
    private Integer taxNo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "percentage")
    private Double percentage;
    @Basic(optional = false)
    @Column(name = "create_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createOn;
    @Basic(optional = false)
    @Column(name = "enabled")
    private short enabled;
    @Column(name = "create_adm")
    private Integer createAdm;

    public Taxes() {
    }

    public Taxes(Integer taxNo) {
        this.taxNo = taxNo;
    }

    public Taxes(Integer taxNo, Double percentage, Date createOn, short enabled) {
        this.taxNo = taxNo;
        this.percentage = percentage;
        this.createOn = createOn;
        this.enabled = enabled;
    }

    public Integer getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(Integer taxNo) {
        this.taxNo = taxNo;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

   

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public short getEnabled() {
        return enabled;
    }

    public void setEnabled(short enabled) {
        this.enabled = enabled;
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
        hash += (taxNo != null ? taxNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxes)) {
            return false;
        }
        Taxes other = (Taxes) object;
        if ((this.taxNo == null && other.taxNo != null) || (this.taxNo != null && !this.taxNo.equals(other.taxNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ahmed.test.app.Taxes[ taxNo=" + taxNo + " ]";
    }
    
}
