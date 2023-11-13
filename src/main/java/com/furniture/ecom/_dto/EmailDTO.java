/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */

public class EmailDTO implements Serializable {

    
    private Integer emailNo;
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String email;
    @NotNull
    private short emailType;

    public EmailDTO() {
    }

    public EmailDTO(Integer emailNo) {
        this.emailNo = emailNo;
    }

    public EmailDTO(Integer emailNo, String email, short emailType) {
        this.emailNo = emailNo;
        this.email = email;
        this.emailType = emailType;
    }

    public Integer getEmailNo() {
        return emailNo;
    }

    public void setEmailNo(Integer emailNo) {
        this.emailNo = emailNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public short getEmailType() {
        return emailType;
    }

    public void setEmailType(short emailType) {
        this.emailType = emailType;
    }



    @Override
    public String toString() {
        return "com.furniture.ecom.dto.Email[ emailNo=" + emailNo + " ]";
    }
    
}
