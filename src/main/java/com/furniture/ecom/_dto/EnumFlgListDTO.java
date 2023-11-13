/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

/**
 *
 * @author HP
 */
public class EnumFlgListDTO {
    private String flgName;
    private String flgValue;

    public String getFlgName() {
        return flgName;
    }

    public void setFlgName(String flgName) {
        this.flgName = flgName;
    }

    public String getFlgValue() {
        return flgValue;
    }

    public void setFlgValue(String flgValue) {
        this.flgValue = flgValue;
    }

    @Override
    public String toString() {
        return "EnumFlgListDTO{" + "flgName=" + flgName + ", flgValue=" + flgValue + '}';
    }
    
}
