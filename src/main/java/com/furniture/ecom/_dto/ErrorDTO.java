/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

/**
 *
 * @author Ahmed Hafez
 */
public class ErrorDTO {

    private String fldNm;
    private String errMsg;

    public ErrorDTO() {
    }

    public ErrorDTO(String fldNm, String errMsg) {
        this.fldNm = fldNm;
        this.errMsg = errMsg;
    }

    /**
     * @return the fldNm
     */
    public String getFldNm() {
        return fldNm;
    }

    /**
     * @param fldNm the fldNm to set
     */
    public void setFldNm(String fldNm) {
        this.fldNm = fldNm;
    }

    /**
     * @return the errMsg
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * @param errMsg the errMsg to set
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    
    

}
