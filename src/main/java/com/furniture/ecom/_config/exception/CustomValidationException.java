/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._config.exception;

import com.furniture.ecom._dto.ErrorDTO;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

/**
 *
 * @author Ahmed Hafez
 */
public class CustomValidationException extends RuntimeException {

    private Errors errors;

    public CustomValidationException(Errors errors) {
        this.errors = errors;
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }

    public List<ErrorDTO> handleValidation() {
        List<FieldError> fieldErrors = this.errors.getFieldErrors();
        List<ErrorDTO> objErrors = new ArrayList<>();
        for (FieldError fe : fieldErrors) {
            ErrorDTO objError = new ErrorDTO(fe.getField(), fe.getDefaultMessage());
            objErrors.add(objError);
        }
        return objErrors;
    }

}
