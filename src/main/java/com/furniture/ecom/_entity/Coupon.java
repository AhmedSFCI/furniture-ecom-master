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
@Table(name = "coupon")
@NamedQueries({
    @NamedQuery(name = "Coupon.findAll", query = "SELECT c FROM Coupon c"),
    @NamedQuery(name = "Coupon.findByCouponNo", query = "SELECT c FROM Coupon c WHERE c.couponNo = :couponNo"),
    @NamedQuery(name = "Coupon.findByCouponCode", query = "SELECT c FROM Coupon c WHERE c.couponCode = :couponCode"),
    @NamedQuery(name = "Coupon.findByStartDate", query = "SELECT c FROM Coupon c WHERE c.startDate = :startDate"),
    @NamedQuery(name = "Coupon.findByEndDate", query = "SELECT c FROM Coupon c WHERE c.endDate = :endDate"),
    @NamedQuery(name = "Coupon.findByEnabled", query = "SELECT c FROM Coupon c WHERE c.enabled = :enabled")})
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "coupon_no")
    private Integer couponNo;
    @Basic(optional = false)
    @Column(name = "coupon_code")
    private String couponCode;
    @Basic(optional = false)
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Basic(optional = false)
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Basic(optional = false)
    @Column(name = "enabled")
    private short enabled;

    public Coupon() {
    }

    public Coupon(Integer couponNo) {
        this.couponNo = couponNo;
    }

    public Coupon(Integer couponNo, String couponCode, Date startDate, Date endDate, short enabled) {
        this.couponNo = couponNo;
        this.couponCode = couponCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.enabled = enabled;
    }

    public Integer getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(Integer couponNo) {
        this.couponNo = couponNo;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public short getEnabled() {
        return enabled;
    }

    public void setEnabled(short enabled) {
        this.enabled = enabled;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (couponNo != null ? couponNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coupon)) {
            return false;
        }
        Coupon other = (Coupon) object;
        if ((this.couponNo == null && other.couponNo != null) || (this.couponNo != null && !this.couponNo.equals(other.couponNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ahmed.test.Coupon[ couponNo=" + couponNo + " ]";
    }
    
}
