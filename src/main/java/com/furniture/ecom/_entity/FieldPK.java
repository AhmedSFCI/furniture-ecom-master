/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author HP
 */
@Embeddable
public class FieldPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "fieldname")
    private String fieldname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lang_no")
    private int langNo;

    public FieldPK() {
    }

    public FieldPK(String fieldname, int langNo) {
        this.fieldname = fieldname;
        this.langNo = langNo;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public int getLangNo() {
        return langNo;
    }

    public void setLangNo(int langNo) {
        this.langNo = langNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fieldname != null ? fieldname.hashCode() : 0);
        hash += (int) langNo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FieldPK)) {
            return false;
        }
        FieldPK other = (FieldPK) object;
        if ((this.fieldname == null && other.fieldname != null) || (this.fieldname != null && !this.fieldname.equals(other.fieldname))) {
            return false;
        }
        if (this.langNo != other.langNo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.FieldPK[ fieldname=" + fieldname + ", langNo=" + langNo + " ]";
    }
    
}
