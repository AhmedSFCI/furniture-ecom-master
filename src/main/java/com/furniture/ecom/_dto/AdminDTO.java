/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author HP
 */
public class AdminDTO {

    private Integer admNo;
    @NotBlank(message = "1-admName")
    @Length(min = 5, message = "18-admName")
    private String admName;
    @NotBlank(message = "1-admCode")
    @Length(min = 5, message = "18-admCode")
    private String admCode;
 //   @Min(value = 1, message = "14-admRoleType")
    @NotBlank(message = "1-admCode")
    private String admRoleType;
    private String admRoleTypeDsc;
    @NotBlank(message = "1-password")
    @Length(min = 8, message = "13-password")
    private String admPassword;
    @NotBlank(message = "1-confirmPassword")
    @Length(min = 8, message = "7-confirmPassword")
    private String confirmAdmPassword;
    private String admPic;
    private Short inActive;
    private Date createOn;
    private Date inactivDate;
    private String oldPassword;
    private List<EnumFlgListDTO> admRoleList;

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

    public String getConfirmAdmPassword() {
        return confirmAdmPassword;
    }

    public void setConfirmAdmPassword(String confirmAdmPassword) {
        this.confirmAdmPassword = confirmAdmPassword;
    }

    public String getAdmPic() {
        return admPic;
    }

    public void setAdmPic(String admPic) {
        this.admPic = admPic;
    }

    public Short getInActive() {
        return inActive;
    }

    public void setInActive(Short inActive) {
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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public List<EnumFlgListDTO> getAdmRoleList() {
        return admRoleList;
    }

    public void setAdmRoleList(List<EnumFlgListDTO> admRoleList) {
        this.admRoleList = admRoleList;
    }

    public String getAdmRoleTypeDsc() {
        return admRoleTypeDsc;
    }

    public void setAdmRoleTypeDsc(String admRoleTypeDsc) {
        this.admRoleTypeDsc = admRoleTypeDsc;
    }
    

}
