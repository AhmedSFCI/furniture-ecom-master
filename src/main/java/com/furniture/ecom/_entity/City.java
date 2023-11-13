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
 * @author Mostafa Atta
 */
@Entity
@Table(name = "city")
@NamedQueries({
    @NamedQuery(name = "City.findAll", query = "SELECT c FROM City c"),
    @NamedQuery(name = "City.findByCityNo", query = "SELECT c FROM City c WHERE c.cityNo = :cityNo"),
    @NamedQuery(name = "City.findByCityNmAr", query = "SELECT c FROM City c WHERE c.cityNmAr = :cityNmAr"),
    @NamedQuery(name = "City.findByCityNmEn", query = "SELECT c FROM City c WHERE c.cityNmEn = :cityNmEn"),
    @NamedQuery(name = "City.findByShipPrice", query = "SELECT c FROM City c WHERE c.shipPrice = :shipPrice")})
public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "city_no")
    private Integer cityNo;
    @Basic(optional = false)
    @Column(name = "city_nm_ar")
    private String cityNmAr;
    @Column(name = "city_nm_en")
    private String cityNmEn;
    @Basic(optional = false)
    @Column(name = "ship_price")
    private double shipPrice;

    public City() {
    }

    public City(Integer cityNo) {
        this.cityNo = cityNo;
    }

    public City(Integer cityNo, String cityNmAr, double shipPrice) {
        this.cityNo = cityNo;
        this.cityNmAr = cityNmAr;
        this.shipPrice = shipPrice;
    }

    public Integer getCityNo() {
        return cityNo;
    }

    public void setCityNo(Integer cityNo) {
        this.cityNo = cityNo;
    }

    public String getCityNmAr() {
        return cityNmAr;
    }

    public void setCityNmAr(String cityNmAr) {
        this.cityNmAr = cityNmAr;
    }

    public String getCityNmEn() {
        return cityNmEn;
    }

    public void setCityNmEn(String cityNmEn) {
        this.cityNmEn = cityNmEn;
    }

    public double getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(double shipPrice) {
        this.shipPrice = shipPrice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cityNo != null ? cityNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof City)) {
            return false;
        }
        City other = (City) object;
        if ((this.cityNo == null && other.cityNo != null) || (this.cityNo != null && !this.cityNo.equals(other.cityNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.test.City[ cityNo=" + cityNo + " ]";
    }
    
}
