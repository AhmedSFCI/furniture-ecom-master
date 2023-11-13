/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 *
 * @author HP
 */
public class ResultDTO {
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String resultMsg;  // status 200 - 300 - 500
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorDTO> errors; // status 400

    
    public ResultDTO(String status, String resultMsg) {
        this.status = status;
        this.resultMsg = resultMsg;
    }

    public ResultDTO(String status, List<ErrorDTO> errors) {
        this.status = status;
        this.errors = errors;
    }
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public List<ErrorDTO> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDTO> errors) {
        this.errors = errors;
    }

}
