/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._helpers;

import com.furniture.ecom._model.LoginUser;

/**
 *
 * @author HP
 */
public interface CommonValidationHelper {

    public boolean checkLoginUsrIsCustomer(LoginUser loginUsr);

    public boolean checkLoginUsrIsNotAnAdmin(LoginUser loginUsr);

    public boolean checkStringValueIsValid(String value);
}
