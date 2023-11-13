/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 */
public class ImageDTO {

    private Integer imgNo;
    @NotBlank(message = "1-imgPath")
    private String imgPath;
    private MultipartFile imgData;
    private String imgType;
    private int itemNo;

    public ImageDTO() {
    }

    public ImageDTO(Integer imgNo, String imgPath) {
        this.imgNo = imgNo;
        this.imgPath = imgPath;
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

    public MultipartFile getImgData() {
        return imgData;
    }

    public void setImgData(MultipartFile imgData) {
        this.imgData = imgData;
    }
    

}
