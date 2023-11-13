/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author HP
 */
@Embeddable
public class MessagePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "msg_no")
    private int msgNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lang_no")
    private int langNo;

    public MessagePK() {
    }

    public MessagePK(int msgNo, int langNo) {
        this.msgNo = msgNo;
        this.langNo = langNo;
    }

    public int getMsgNo() {
        return msgNo;
    }

    public void setMsgNo(int msgNo) {
        this.msgNo = msgNo;
    }

    public int getLangNo() {
        return langNo;
    }

    public void setLangNo(int langNo) {
        this.langNo = langNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) msgNo;
        hash += (int) langNo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MessagePK)) {
            return false;
        }
        MessagePK other = (MessagePK) object;
        if (this.msgNo != other.msgNo) {
            return false;
        }
        if (this.langNo != other.langNo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.MessagePK[ msgNo=" + msgNo + ", langNo=" + langNo + " ]";
    }
    
}
