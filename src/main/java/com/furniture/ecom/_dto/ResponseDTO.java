/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;

/**
 *
 * @author HP
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T>{

//    private ResponseDataDTO data;
    private ResultDTO result;
    private String token;
    private  Map<String,T> data;

    public Map<String, T> getData() {
        return data;
    }

    public void setData(Map<String, T> data) {
        this.data = data;
    }

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    

}
