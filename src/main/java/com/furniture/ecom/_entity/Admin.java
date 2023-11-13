/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "admin")
@NamedQueries({
    @NamedQuery(name = "Admin.findAll", query = "SELECT a FROM Admin a"),
    @NamedQuery(name = "Admin.findByAdmNo", query = "SELECT a FROM Admin a WHERE a.admNo = :admNo"),
    @NamedQuery(name = "Admin.findByAdmName", query = "SELECT a FROM Admin a WHERE a.admName = :admName"),
    @NamedQuery(name = "Admin.findByAdmCode", query = "SELECT a FROM Admin a WHERE a.admCode = :admCode"),
    @NamedQuery(name = "Admin.findByAdmRoleType", query = "SELECT a FROM Admin a WHERE a.admRoleType = :admRoleType"),
    @NamedQuery(name = "Admin.findByAdmPassword", query = "SELECT a FROM Admin a WHERE a.admPassword = :admPassword"),
    @NamedQuery(name = "Admin.findByAdmPic", query = "SELECT a FROM Admin a WHERE a.admPic = :admPic"),
    @NamedQuery(name = "Admin.findByInActive", query = "SELECT a FROM Admin a WHERE a.inActive = :inActive"),
    @NamedQuery(name = "Admin.findByCreateOn", query = "SELECT a FROM Admin a WHERE a.createOn = :createOn"),
    @NamedQuery(name = "Admin.findByInactivDate", query = "SELECT a FROM Admin a WHERE a.inactivDate = :inactivDate")})
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "adm_no")
    private Integer admNo;
    @Basic(optional = false)
    @Column(name = "adm_name")
    private String admName;
    @Basic(optional = false)
    @Column(name = "adm_code")
    private String admCode;
    @Basic(optional = false)
    @Column(name = "adm_role_type")
    private String admRoleType;
    @Basic(optional = false)
    @Column(name = "adm_password")
    private String admPassword;
    @Column(name = "adm_pic")
    private String admPic;
    @Basic(optional = false)
    @Column(name = "in_active")
    private short inActive;
    @Basic(optional = false)
    @Column(name = "create_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createOn;
    @Column(name = "inactiv_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inactivDate;

    public Admin() {
    }

    public Admin(Integer admNo, String admName, String admCode, String admRoleType, String admPassword, String admPic, short inActive, Date createOn, Date inactivDate) {
        this.admNo = admNo;
        this.admName = admName;
        this.admCode = admCode;
        this.admRoleType = admRoleType;
        this.admPassword = admPassword;
        this.admPic = admPic;
        this.inActive = inActive;
        this.createOn = createOn;
        this.inactivDate = inactivDate;
    }

    public Integer getAdmNo() {
        return admNo;
    }

    public void setAdmNo(Integer admNo) {
        this.admNo = admNo;
    }

    public String getAdmName() {
        return admName;
    }

    public void setAdmName(String admName) {
        this.admName = admName;
    }

    public String getAdmCode() {
        return admCode;
    }

    public void setAdmCode(String admCode) {
        this.admCode = admCode;
    }

    public String getAdmRoleType() {
        return admRoleType;
    }

    public void setAdmRoleType(String admRoleType) {
        this.admRoleType = admRoleType;
    }

    public String getAdmPassword() {
        return admPassword;
    }

    public void setAdmPassword(String admPassword) {
        this.admPassword = admPassword;
    }

    public String getAdmPic() {
        return admPic;
    }

    public void setAdmPic(String admPic) {
        this.admPic = admPic;
    }

    public short getInActive() {
        return inActive;
    }

    public void setInActive(short inActive) {
        this.inActive = inActive;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public Date getInactivDate() {
        return inactivDate;
    }

    public void setInactivDate(Date inactivDate) {
        this.inactivDate = inactivDate;
    }

    

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (admNo != null ? admNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Admin)) {
            return false;
        }
        Admin other = (Admin) object;
        if ((this.admNo == null && other.admNo != null) || (this.admNo != null && !this.admNo.equals(other.admNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.Admin[ admNo=" + admNo + " ]";
    }


}
