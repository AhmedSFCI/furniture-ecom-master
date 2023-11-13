/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "image")
@NamedQueries({
    @NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i"),
    @NamedQuery(name = "Image.findByImgNo", query = "SELECT i FROM Image i WHERE i.imgNo = :imgNo"),
    @NamedQuery(name = "Image.findByImgPath", query = "SELECT i FROM Image i WHERE i.imgPath = :imgPath"),
    @NamedQuery(name = "Image.findByImgType", query = "SELECT i FROM Image i WHERE i.imgType = :imgType"),
    @NamedQuery(name = "Image.findByItemNo", query = "SELECT i FROM Image i WHERE i.itemNo = :itemNo")})
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "img_no")
    private Integer imgNo;
    @Basic(optional = false)
    @Column(name = "img_path")
    private String imgPath;
    @Basic(optional = false)
    @Column(name = "img_type")
    private String imgType;
    @Basic(optional = false)
    @Column(name = "item_no")
    private int itemNo;

    public Image() {
    }

    public Image(Integer imgNo) {
        this.imgNo = imgNo;
    }

    public Image(Integer imgNo, String imgPath, String imgType, int itemNo) {
        this.imgNo = imgNo;
        this.imgPath = imgPath;
        this.imgType = imgType;
        this.itemNo = itemNo;
    }

    public Integer getImgNo() {
        return imgNo;
    }

    public void setImgNo(Integer imgNo) {
        this.imgNo = imgNo;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (imgNo != null ? imgNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Image)) {
            return false;
        }
        Image other = (Image) object;
        if ((this.imgNo == null && other.imgNo != null) || (this.imgNo != null && !this.imgNo.equals(other.imgNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.furniture.entity.Image[ imgNo=" + imgNo + " ]";
    }
    
}
