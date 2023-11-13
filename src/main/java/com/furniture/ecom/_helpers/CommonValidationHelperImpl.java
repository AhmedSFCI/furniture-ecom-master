/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._helpers;

import com.furniture.ecom._enums.RoleType;
import com.furniture.ecom._model.LoginUser;
import com.furniture.ecom._util.ObjectChecker;
import org.springframework.stereotype.Component;

/**
 * @author HP
 */
@Component
public class CommonValidationHelperImpl implements CommonValidationHelper {

    @Override
    public boolean checkLoginUsrIsCustomer(LoginUser loginUsr) {

        if (ObjectChecker.areNotEqual(PasswordEncoder.decrypt(loginUsr.getRoleTyp()), RoleType.CUSTOMER.toString())) {
            return false;
        } else if (!ObjectChecker.isEmptyOrNull(loginUsr.getUsrCode())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkLoginUsrIsNotAnAdmin(LoginUser loginUsr) {
        if (ObjectChecker.isEmptyOrNull(loginUsr.getUsrCode())) {
            return true;
        }
        String role = PasswordEncoder.decrypt(loginUsr.getRoleTyp()).toUpperCase();
        if (ObjectChecker.areNotEqual(role, RoleType.ADMIN.toString())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkStringValueIsValid(String value) {
        return ObjectChecker.isEmptyOrNull(value);
    }
}
