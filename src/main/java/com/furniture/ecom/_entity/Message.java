/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findByMsgNo", query = "SELECT m FROM Message m WHERE m.messagePK.msgNo = :msgNo"),
    @NamedQuery(name = "Message.findByMsgText", query = "SELECT m FROM Message m WHERE m.msgText = :msgText")})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MessagePK messagePK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "msg_text")
    private String msgText;

    public Message() {
    }

    public Message(MessagePK messagePK) {
        this.messagePK = messagePK;
    }

    public Message(MessagePK messagePK, String msgText) {
        this.messagePK = messagePK;
        this.msgText = msgText;
    }

    public Message(int msgNo, int langNo) {
        this.messagePK = new MessagePK(msgNo, langNo);
    }

    public MessagePK getMessagePK() {
        return messagePK;
    }

    public void setMessagePK(MessagePK messagePK) {
        this.messagePK = messagePK;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messagePK != null ? messagePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.messagePK == null && other.messagePK != null) || (this.messagePK != null && !this.messagePK.equals(other.messagePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.Message[ messagePK=" + messagePK + " ]";
    }
    
}
