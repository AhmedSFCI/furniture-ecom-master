/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testsecurity;

import java.util.Date;

/**
 *
 * @author HP
 */
public class UpdAudit implements AuditFLds {

    long updUsrNo;
    Date updDate;
    String updTrmnlNm;

    public long getUpdUsrNo() {
        return updUsrNo;
    }

    public void setUpdUsrNo(long updUsrNo) {
        this.updUsrNo = updUsrNo;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public String getUpdTrmnlNm() {
        return updTrmnlNm;
    }

    public void setUpdTrmnlNm(String updTrmnlNm) {
        this.updTrmnlNm = updTrmnlNm;
    }
    
}
