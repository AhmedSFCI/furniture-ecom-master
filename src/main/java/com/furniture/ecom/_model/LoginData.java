/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author HP
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginData {
   private String admCode;
   private String password;
   private String email;

    public String getAdmCode() {
        return admCode;
    }

    public void setAdmCode(String admCode) {
        this.admCode = admCode;
    }

 
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
   
}
