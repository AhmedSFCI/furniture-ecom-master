/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._model;

/**
 *
 * @author HP
 */
public class LoginUser {
   private Long usrNo;
   private String usrCode;
   private String roleTyp;
   private String usrNm;
   private String usrPicture;
   private String email;
   private Short verifiedCode;
   private Short langNo;
   

    public Long getUsrNo() {
        return usrNo;
    }

    public void setUsrNo(Long usrNo) {
        this.usrNo = usrNo;
    }

    public String getUsrCode() {
        return usrCode;
    }

    public void setUsrCode(String usrCode) {
        this.usrCode = usrCode;
    }

    public String getRoleTyp() {
        return roleTyp;
    }

    public void setRoleTyp(String roleTyp) {
        this.roleTyp = roleTyp;
    }

   

    public String getUsrNm() {
        return usrNm;
    }

    public void setUsrNm(String usrNm) {
        this.usrNm = usrNm;
    }

    public String getUsrPicture() {
        return usrPicture;
    }

    public void setUsrPicture(String usrPicture) {
        this.usrPicture = usrPicture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Short getVerifiedCode() {
        return verifiedCode;
    }

    public void setVerifiedCode(Short verifiedCode) {
        this.verifiedCode = verifiedCode;
    }

    public Short getLangNo() {
        return langNo;
    }

    public void setLangNo(Short langNo) {
        this.langNo = langNo;
    }
    

    @Override
    public String toString() {
        return "LoginUser{" + "usrNo=" + usrNo + ", usrCode=" + usrCode + ", roleTyp=" + roleTyp + ", usrNm=" + usrNm + ", usrPicture=" + usrPicture + ", email=" + email + '}';
    }

  
}
