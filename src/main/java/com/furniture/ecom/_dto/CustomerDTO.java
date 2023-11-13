/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author HP
 */
public class CustomerDTO implements Serializable {

    private Integer custNo;
    @NotBlank(message = "1-custEmail")
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$", message = "28-custEmail")
    private String custEmail;
    @NotBlank(message = "1-custName")
    @Length(min = 5, message = "13-custName")
    private String custName;
    @NotBlank(message = "1-password")
    @Length(min = 8, message = "13-password")
    private String custPassword;
    @NotBlank(message = "1-confirmPassword")
    @Length(min = 8, message = "13-confirmPassword")
    private String confirmCustPassword;
    private String custPic;
    private Date createOn;
    private String custPhone;
    private Integer custCity;
    private Integer custCountry;
    // @NotNull(message = "1-verifyCode")
    private String verifyCode;
    // @NotNull(message = "1-verifiedFlg")
    private short verifiedFlg;
    //  @NotNull(message = "1-verifyExpirDate")
    private Date verifyExpirDate;
    private List<CartDTO> cartList;
    private List<OrdersDTO> ordersList;
    private String oldPassword;
    private String verifyPasswordCode;
    private Date verifyPasswordExpirDate;

    public CustomerDTO() {
    }

    public CustomerDTO(Integer custNo) {
        this.custNo = custNo;
    }

    public CustomerDTO(Integer custNo, String custEmail, String custName, String custPassword, Date createOn, String verifyCode, short verifiedFlg, Date verifyExpirDate) {
        this.custNo = custNo;
        this.custEmail = custEmail;
        this.custName = custName;
        this.custPassword = custPassword;
        this.createOn = createOn;
        this.verifyCode = verifyCode;
        this.verifiedFlg = verifiedFlg;
        this.verifyExpirDate = verifyExpirDate;
    }

    public String getConfirmCustPassword() {
        return confirmCustPassword;
    }

    public void setConfirmCustPassword(String confirmCustPassword) {
        this.confirmCustPassword = confirmCustPassword;
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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setVerifyExpirDate(Date verifyExpirDate) {
        this.verifyExpirDate = verifyExpirDate;
    }

    public void setOrdersList(List<OrdersDTO> ordersList) {
        this.ordersList = ordersList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (custNo != null ? custNo.hashCode() : 0);
        return hash;
    }

    public List<CartDTO> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartDTO> cartList) {
        this.cartList = cartList;
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
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerDTO)) {
            return false;
        }
        CustomerDTO other = (CustomerDTO) object;
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
