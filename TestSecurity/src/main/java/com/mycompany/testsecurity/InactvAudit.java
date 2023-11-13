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
public class InactvAudit implements AuditFLds {

    long inactvUsrNo;
    Date inactvDate;
    String inactvTrmnlNm;

    public long getInactvUsrNo() {
        return inactvUsrNo;
    }

    public void setInactvUsrNo(long inactvUsrNo) {
        this.inactvUsrNo = inactvUsrNo;
    }

    public Date getInactvDate() {
        return inactvDate;
    }

    public void setInactvDate(Date inactvDate) {
        this.inactvDate = inactvDate;
    }

    public String getInactvTrmnlNm() {
        return inactvTrmnlNm;
    }

    public void setInactvTrmnlNm(String inactvTrmnlNm) {
        this.inactvTrmnlNm = inactvTrmnlNm;
    }

}
