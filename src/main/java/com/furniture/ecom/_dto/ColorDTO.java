/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author HP
 */
public class ColorDTO {

    private Integer colorNo;
    @NotBlank(message = "1-colorNmAr")
    @Length(min = 3, message = "29-colorNmAr")
    private String colorNmAr;
    private String colorNmEn;
    private String colorNm;
    @NotBlank(message = "1-colorCode")
    private String colorCode;

    public Integer getColorNo() {
        return colorNo;
    }

    public void setColorNo(Integer colorNo) {
        this.colorNo = colorNo;
    }

    public String getColorNmAr() {
        return colorNmAr;
    }

    public void setColorNmAr(String colorNmAr) {
        this.colorNmAr = colorNmAr;
    }

    public String getColorNmEn() {
        return colorNmEn;
    }

    public void setColorNmEn(String colorNmEn) {
        this.colorNmEn = colorNmEn;
    }

    public String getColorNm() {
        return colorNm;
    }

    public void setColorNm(String colorNm) {
        this.colorNm = colorNm;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

}
