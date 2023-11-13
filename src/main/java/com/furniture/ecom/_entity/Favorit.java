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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "favorit")
@NamedQueries({
    @NamedQuery(name = "Favorit.findAll", query = "SELECT f FROM Favorit f"),
    @NamedQuery(name = "Favorit.findByCustNo", query = "SELECT f FROM Favorit f WHERE f.favoritPK.custNo = :custNo"),
    @NamedQuery(name = "Favorit.findByProdNo", query = "SELECT f FROM Favorit f WHERE f.favoritPK.prodNo = :prodNo"),
    @NamedQuery(name = "Favorit.findByAddedDate", query = "SELECT f FROM Favorit f WHERE f.addedDate = :addedDate")})
public class Favorit implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FavoritPK favoritPK;
    @Basic(optional = false)
    @Column(name = "added_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addedDate;

    public Favorit() {
    }

    public Favorit(FavoritPK favoritPK) {
        this.favoritPK = favoritPK;
    }

    public Favorit(FavoritPK favoritPK, Date addedDate) {
        this.favoritPK = favoritPK;
        this.addedDate = addedDate;
    }

    public Favorit(int custNo, int prodNo) {
        this.favoritPK = new FavoritPK(custNo, prodNo);
    }

    public FavoritPK getFavoritPK() {
        return favoritPK;
    }

    public void setFavoritPK(FavoritPK favoritPK) {
        this.favoritPK = favoritPK;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (favoritPK != null ? favoritPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Favorit)) {
            return false;
        }
        Favorit other = (Favorit) object;
        if ((this.favoritPK == null && other.favoritPK != null) || (this.favoritPK != null && !this.favoritPK.equals(other.favoritPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ahmed.test.Favorit[ favoritPK=" + favoritPK + " ]";
    }

}
