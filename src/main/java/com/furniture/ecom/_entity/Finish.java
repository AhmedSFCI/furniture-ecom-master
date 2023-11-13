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
@Table(name = "finish")
@NamedQueries({
    @NamedQuery(name = "Finish.findAll", query = "SELECT f FROM Finish f"),
    @NamedQuery(name = "Finish.findByFinishNo", query = "SELECT f FROM Finish f WHERE f.finishNo = :finishNo"),
    @NamedQuery(name = "Finish.findByFinishNmAr", query = "SELECT f FROM Finish f WHERE f.finishNmAr = :finishNmAr"),
    @NamedQuery(name = "Finish.findByFinishNmEn", query = "SELECT f FROM Finish f WHERE f.finishNmEn = :finishNmEn")})
public class Finish implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "finish_no")
    private Integer finishNo;
    @Basic(optional = false)
    @Column(name = "finish_nm_ar")
    private String finishNmAr;
    @Column(name = "finish_nm_en")
    private String finishNmEn;
    public Finish() {
    }

    public Finish(Integer finishNo) {
        this.finishNo = finishNo;
    }

    public Finish(Integer finishNo, String finishNmAr) {
        this.finishNo = finishNo;
        this.finishNmAr = finishNmAr;
    }

    public Integer getFinishNo() {
        return finishNo;
    }

    public void setFinishNo(Integer finishNo) {
        this.finishNo = finishNo;
    }

    public String getFinishNmAr() {
        return finishNmAr;
    }

    public void setFinishNmAr(String finishNmAr) {
        this.finishNmAr = finishNmAr;
    }

    public String getFinishNmEn() {
        return finishNmEn;
    }

    public void setFinishNmEn(String finishNmEn) {
        this.finishNmEn = finishNmEn;
    }

 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (finishNo != null ? finishNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Finish)) {
            return false;
        }
        Finish other = (Finish) object;
        if ((this.finishNo == null && other.finishNo != null) || (this.finishNo != null && !this.finishNo.equals(other.finishNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ahmed.test.Finish[ finishNo=" + finishNo + " ]";
    }
    
}
