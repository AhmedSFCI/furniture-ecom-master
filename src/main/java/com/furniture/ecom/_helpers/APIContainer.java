/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._helpers;

import com.furniture.ecom._dto.ResponseDTO;
import com.furniture.ecom._dto.ResultDTO;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author HP
 */
public class APIContainer {

    private static Map<String,Object> data = new HashMap();

    public static ResponseDTO responseWrapper(ResultDTO result) {
        ResponseDTO response = new ResponseDTO();
        response.setData(new HashMap());
        response.setResult(result);
        return response;
    }

    public static ResponseDTO responseWrapper(Map<String,Object> data, ResultDTO result) {
        ResponseDTO response = new ResponseDTO();
        response.setData(data);
        response.setResult(result);
        return response;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    

}
