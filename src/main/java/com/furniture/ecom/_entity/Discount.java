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
@Table(name = "discount")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Discount.findAll", query = "SELECT d FROM Discount d"),
    @NamedQuery(name = "Discount.findByDiscountNo", query = "SELECT d FROM Discount d WHERE d.discountNo = :discountNo"),
    @NamedQuery(name = "Discount.findByDiscountRate", query = "SELECT d FROM Discount d WHERE d.discountRate = :discountRate"),
    @NamedQuery(name = "Discount.findByCreateOn", query = "SELECT d FROM Discount d WHERE d.createOn = :createOn"),
    @NamedQuery(name = "Discount.findByStartDate", query = "SELECT d FROM Discount d WHERE d.startDate = :startDate"),
    @NamedQuery(name = "Discount.findByExpirDate", query = "SELECT d FROM Discount d WHERE d.expirDate = :expirDate"),
    @NamedQuery(name = "Discount.findByEnabled", query = "SELECT d FROM Discount d WHERE d.active = :enabled"),
    @NamedQuery(name = "Discount.findByDiscountMaxValue", query = "SELECT d FROM Discount d WHERE d.discountMaxValue = :discountMaxValue")})
public class Discount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "discount_no")
    private Integer discountNo;
    @Basic(optional = false)
    @Column(name = "discount_rate")
    private Double discountRate;
    @Basic(optional = false)
    @Column(name = "create_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createOn;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "expir_date")
    @Temporal(TemporalType.DATE)
    private Date expirDate;
    @Basic(optional = false)
    @Column(name = "enabled")
    private short active;
    @Column(name = "discount_max_value")
    private Double discountMaxValue;
    @Column(name = "create_adm")
    private Integer createAdm;

    public Discount() {
    }

    public Discount(Integer discountNo) {
        this.discountNo = discountNo;
    }

    public Discount(Integer discountNo, Double discountRate, Date createOn, short active) {
        this.discountNo = discountNo;
        this.discountRate = discountRate;
        this.createOn = createOn;
        this.active = active;
    }

    public Integer getDiscountNo() {
        return discountNo;
    }

    public void setDiscountNo(Integer discountNo) {
        this.discountNo = discountNo;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

   

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpirDate() {
        return expirDate;
    }

    public void setExpirDate(Date expirDate) {
        this.expirDate = expirDate;
    }

    public short getActive() {
        return active;
    }

    public void setActive(short enabled) {
        this.active = enabled;
    }

    public Double getDiscountMaxValue() {
        return discountMaxValue;
    }

    public void setDiscountMaxValue(Double discountMaxValue) {
        this.discountMaxValue = discountMaxValue;
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
        hash += (discountNo != null ? discountNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Discount)) {
            return false;
        }
        Discount other = (Discount) object;
        if ((this.discountNo == null && other.discountNo != null) || (this.discountNo != null && !this.discountNo.equals(other.discountNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ahmed.test.app.Discount[ discountNo=" + discountNo + " ]";
    }

}
