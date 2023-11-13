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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByCustNo", query = "SELECT c FROM Customer c WHERE c.custNo = :custNo"),
    @NamedQuery(name = "Customer.findByCustEmail", query = "SELECT c FROM Customer c WHERE c.custEmail = :custEmail"),
    @NamedQuery(name = "Customer.findByCustName", query = "SELECT c FROM Customer c WHERE c.custName = :custName"),
    @NamedQuery(name = "Customer.findByCustPassword", query = "SELECT c FROM Customer c WHERE c.custPassword = :custPassword"),
    @NamedQuery(name = "Customer.findByCustPic", query = "SELECT c FROM Customer c WHERE c.custPic = :custPic"),
    @NamedQuery(name = "Customer.findByCreateOn", query = "SELECT c FROM Customer c WHERE c.createOn = :createOn"),
    @NamedQuery(name = "Customer.findByCustPhone", query = "SELECT c FROM Customer c WHERE c.custPhone = :custPhone"),
    @NamedQuery(name = "Customer.findByCustCity", query = "SELECT c FROM Customer c WHERE c.custCity = :custCity"),
    @NamedQuery(name = "Customer.findByCustCountry", query = "SELECT c FROM Customer c WHERE c.custCountry = :custCountry"),
    @NamedQuery(name = "Customer.findByVerifyCode", query = "SELECT c FROM Customer c WHERE c.verifyCode = :verifyCode"),
    @NamedQuery(name = "Customer.findByVerifiedFlg", query = "SELECT c FROM Customer c WHERE c.verifiedFlg = :verifiedFlg"),
    @NamedQuery(name = "Customer.findByVerifyExpirDate", query = "SELECT c FROM Customer c WHERE c.verifyExpirDate = :verifyExpirDate"),
    @NamedQuery(name = "Customer.findByVerifyPasswordCode", query = "SELECT c FROM Customer c WHERE c.verifyPasswordCode = :verifyPasswordCode"),
    @NamedQuery(name = "Customer.findByVerifyPasswordExpirDate", query = "SELECT c FROM Customer c WHERE c.verifyPasswordExpirDate = :verifyPasswordExpirDate")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cust_no")
    private Integer custNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cust_email")
    private String custEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cust_name")
    private String custName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cust_password")
    private String custPassword;
    @Size(max = 2147483647)
    @Column(name = "cust_pic")
    private String custPic;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createOn;
    @Size(max = 15)
    @Column(name = "cust_phone")
    private String custPhone;
    @Column(name = "cust_city")
    private Integer custCity;
    @Column(name = "cust_country")
    private Integer custCountry;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "verify_code")
    private String verifyCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "verified_flg")
    private short verifiedFlg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "verify_expir_date")
    @Temporal(TemporalType.DATE)
    private Date verifyExpirDate;
    @Column(name = "verify_password_code")
    private String verifyPasswordCode;
    @Column(name = "verify_password_expir_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date verifyPasswordExpirDate;

    public Customer() {
    }

    public Customer(Integer custNo) {
        this.custNo = custNo;
    }

    public Customer(Integer custNo, String custEmail, String custName, String custPassword, Date createOn, String verifyCode, short verifiedFlg, Date verifyExpirDate) {
        this.custNo = custNo;
        this.custEmail = custEmail;
        this.custName = custName;
        this.custPassword = custPassword;
        this.createOn = createOn;
        this.verifyCode = verifyCode;
        this.verifiedFlg = verifiedFlg;
        this.verifyExpirDate = verifyExpirDate;
    }

    public Integer getCustNo() {
        return custNo;
    }

    public void setCustNo(Integer custNo) {
        this.custNo = custNo;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustPassword() {
        return custPassword;
    }

    public void setCustPassword(String custPassword) {
        this.custPassword = custPassword;
    }

    public String getCustPic() {
        return custPic;
    }

    public void setCustPic(String custPic) {
        this.custPic = custPic;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public Integer getCustCity() {
        return custCity;
    }

    public void setCustCity(Integer custCity) {
        this.custCity = custCity;
    }

    public Integer getCustCountry() {
        return custCountry;
    }

    public void setCustCountry(Integer custCountry) {
        this.custCountry = custCountry;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public short getVerifiedFlg() {
        return verifiedFlg;
    }

    public void setVerifiedFlg(short verifiedFlg) {
        this.verifiedFlg = verifiedFlg;
    }

    public Date getVerifyExpirDate() {
        return verifyExpirDate;
    }

    public void setVerifyExpirDate(Date verifyExpirDate) {
        this.verifyExpirDate = verifyExpirDate;
    }

    public String getVerifyPasswordCode() {
        return verifyPasswordCode;
    }

    public void setVerifyPasswordCode(String verifyPasswordCode) {
        this.verifyPasswordCode = verifyPasswordCode;
    }

    public Date getVerifyPasswordExpirDate() {
        return verifyPasswordExpirDate;
    }

    public void setVerifyPasswordExpirDate(Date verifyPasswordExpirDate) {
        this.verifyPasswordExpirDate = verifyPasswordExpirDate;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (custNo != null ? custNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.custNo == null && other.custNo != null) || (this.custNo != null && !this.custNo.equals(other.custNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.Customer[ custNo=" + custNo + " ]";
    }

}
