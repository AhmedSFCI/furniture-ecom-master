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
public interface ResponseMessages {

    String SUCCESS_CODE = "200"; // SUCCESS status
    String SERVER_ERROR_CODE = "500"; // server error
    String CLIENT_ERROR_CODE = "300"; // error in validation the record into database // or single error validation required
    String CLIENT_FORM_ERROR_CODE = "303"; // form required attributes error
    String CLIENT_WRONG_PARAMETER_CODE = "400"; // doesn't send the body objects for NullPointerException
    String UNAUTHORIZED_CODE = "403";
    String UNFOUND_CODE = "404";
    String SERVER_ERROR_MSG[] = {"خطأ في الخادم", "Server Error !!"};

    String SUCCESS_MSG[] = {"تمت العملية بنجاح", "The Operation Done Successfully !!"};
    String CLIENT_ERROR_MSG[] = {"هناك بعض البيانات التي يجب ادخالها بشكل صحيح", "There are  Parameter Values should entered correctly!!"};
    String ACCESS_DENIED_MSG = "You Should Login To Access This Page";
    String DATE_EPIRED[] = {"الكود غير صالح الان.. أعد أرساله مجددا", " Verified Code is Expired .. Send it Again."};
    String VIERIFY_SUCCESS [] = {"تم تأكيد الحساب بنجاح ...","Email is Verified Successfully..."};
    String RESET_PASSWORD_CODE_ERROR [] = {"إنك لم ترسم بريد الكتروني بكود استعادة الرقم السري ... من فضلك اضغط علي نسيت كلمة المرور","You don't send any email  ... please press on forget password"};
    String PASSWORD_VERIFY_CODE_ERROR []={"رمز استعادة كلمة المرور غير صحيح ... من فضلك حاول مرة اخري ","Password Verify Code is Inccorect... please try again"};
    String PRODUCT_EXISTS_IN_CART [] = {"هذا المنتج موجود بالفعل في سلتك...","This Product is Exists in Your Cart"};
    String PRODUCT_NOT_EXISTS_IN_CART [] = {"هذا المنتج ليس موجود  في سلتك...","This Product is Not Exist in Your Cart"};
    String MAIN_CATEGORY_GET_ITS_PRODUCTS [] = {"هذا القسم لا توجد به منتجات اختر التصنيف الصحيح","This main category doesn't have products .. choose correct category"};
    
    Integer DUPLICATE_RECORD_AR_CODE = 111; // for duplicate Arabic text [name - description ] 
    Integer DUPLICATE_RECORD_EN_CODE = 222;  // for duplicate English text [name - description ] 
    Integer NOT_FOUND_RECORD_CODE = 333; // Record not found in database [ID not found most used in getByCode and delete and update]
    Integer RECORD_IS_EXISTS_CODE = 444;// primary key is exists in datavase
    Integer DUPLICATE_UNIQUE_FIELD_CODE = 555; // // Record is exists in database [most used in update and insert]
    Integer RELATED_DATA_FIELDS = 666;
    Integer SUCCESS_OPERATION = 1; // UPDATE OR ADD OR DELETE SUCCESS CODE 


}
