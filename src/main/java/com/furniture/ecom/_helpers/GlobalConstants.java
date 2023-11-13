/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._helpers;

/**
 *
 * @author HP
 */
public interface GlobalConstants {

    /* For Security Configuration*/
    public static final String CLAIMS_CREATED = "created";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    /* For Properties of enumeration Configuration*/
    public static final String EN_PROPERTIES = "enum_en.properties";
    public static final String AR_PROPERTIES = "enum_ar.properties";

    /* Enum File Names */
    public static final String ADMIN_ROLE_TYPE = "ADMINROLE";
    public static final String OrderStatus = "OrderStatus";
    public static final String OrderTracking = "OrderTracking";
    public static final String PayType = "PayType";
    /*Default Images */
    public static final String USER_IMG = "default_user.jpg";
    public static final String CATEGORY_IMG = "default_category.jpg";
    public static final String ABOUT_IMG = "default_about.jpg";
    public static final String POLICY_IMG = "default_policy.jpg";
    public static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
    public static final String[] CROSS_ORIGIN = {"*","/**", "http://localhost:4200","","/"};
    public static String COMPANY_ADDRESS = "6th of October , Giza , Egypt";
    public static final String imag_path = "https://fastdo.co/My_Images/";
    /* SHOW TYPE */
    public static final String ADMIN_SHOW = "Admin";
    public static final String USER_SHOW = "User";
    
}
