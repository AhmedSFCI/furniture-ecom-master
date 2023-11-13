/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "field")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Field.findAll", query = "SELECT f FROM Field f"),
    @NamedQuery(name = "Field.findByFieldname", query = "SELECT f FROM Field f WHERE f.fieldPK.fieldname = :fieldname"),
    @NamedQuery(name = "Field.findByFieldText", query = "SELECT f FROM Field f WHERE f.fieldText = :fieldText")})
public class Field implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FieldPK fieldPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "field_text")
    private String fieldText;

    public Field() {
    }

    public Field(FieldPK fieldPK) {
        this.fieldPK = fieldPK;
    }

    public Field(FieldPK fieldPK, String fieldText) {
        this.fieldPK = fieldPK;
        this.fieldText = fieldText;
    }

    public Field(String fieldname, int langNo) {
        this.fieldPK = new FieldPK(fieldname, langNo);
    }

    public FieldPK getFieldPK() {
        return fieldPK;
    }

    public void setFieldPK(FieldPK fieldPK) {
        this.fieldPK = fieldPK;
    }

    public String getFieldText() {
        return fieldText;
    }

    public void setFieldText(String fieldText) {
        this.fieldText = fieldText;
    }

  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fieldPK != null ? fieldPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Field)) {
            return false;
        }
        Field other = (Field) object;
        if ((this.fieldPK == null && other.fieldPK != null) || (this.fieldPK != null && !this.fieldPK.equals(other.fieldPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.Field[ fieldPK=" + fieldPK + " ]";
    }
    
}
