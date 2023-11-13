/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 */
public class ProductDTO implements Serializable {

    private Long prodNo;
    @NotBlank(message = "1-prodNameAr")
    @Length(min = 5, message = "18-prodNameAr")
    private String prodNameAr;
    private String prodNameEn;
    private String prodName;
    @NotBlank(message = "1-prodDscAr")
    @Length(min = 50, message = "19-prodDscAr")
    private String prodDscAr;
    private String prodDsc;
    @NotNull(message = "1-style")
    private Integer styleNo;
    @NotNull(message = "1-finish")
    private Integer finishNo;
    @NotNull(message = "1-prodAvailability")
    @Min(value = 0, message = "6-prodAvailability")
    @Max(value = 1, message = "6-prodAvailability")
    private Short prodAvailability;
    @NotNull(message = "1-prodPrice")
    @Min(value = 1, message = "8-prodPrice")
    private Double prodPrice;
    @NotNull(message = "1-prodQuantity")
    @Min(value = 1, message = "8-prodQuantity")
    private Integer prodQuantity;
    @NotNull(message = "1-dimHight")
    @Min(value = 1, message = "8-dimHight")
    private Double dimHight;
    @NotNull(message = "1-dimWidth")
    @Min(value = 1, message = "8-dimWidth")
    private Double dimWidth;
    @NotNull(message = "1-dimDepth")
    @Min(value = 1, message = "8-dimDepth")
    private Double dimDepth;
    private int createAdm;
    private Date createOn;
    private String prodDscEn;
    @NotNull(message = "1-catNo")
    private Integer catNo;
    private String catName;
    private String styleName;
    private String finishName;
    private List<ImageDTO> images;
    @NotEmpty(message = "1-prodColor")
    private List<ProductColorDTO> prodColorList;
    @NotEmpty(message = "1-prodMaterial")
    private List<ProductMaterialsDTO> prodMaterialList;
    private Integer discountNo;
    private Integer warranty;
    private List<ProductDTO> relatedProducts;
    private Double discountValue;
    private Long viewsCount;
 //       @NotEmpty(message = "1-prodImage")
    private List<MultipartFile> prodImages;

    public ProductDTO() {
    }

    public Long getProdNo() {
        return prodNo;
    }

    public void setProdNo(Long prodNo) {
        this.prodNo = prodNo;
    }

    public String getProdNameAr() {
        return prodNameAr;
    }

    public void setProdNameAr(String prodNameAr) {
        this.prodNameAr = prodNameAr;
    }

    public String getProdNameEn() {
        return prodNameEn;
    }

    public void setProdNameEn(String prodNameEn) {
        this.prodNameEn = prodNameEn;
    }

    public String getProdDscAr() {
        return prodDscAr;
    }

    public void setProdDscAr(String prodDscAr) {
        this.prodDscAr = prodDscAr;
    }

    public Short getProdAvailability() {
        return prodAvailability;
    }

    public void setProdAvailability(Short prodAvailability) {
        this.prodAvailability = prodAvailability;
    }

    public Double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(Double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public Integer getProdQuantity() {
        return prodQuantity;
    }

    public void setProdQuantity(Integer prodQuantity) {
        this.prodQuantity = prodQuantity;
    }

    public Double getDimHight() {
        return dimHight;
    }

    public void setDimHight(Double dimHight) {
        this.dimHight = dimHight;
    }

    public Double getDimWidth() {
        return dimWidth;
    }

    public void setDimWidth(Double dimWidth) {
        this.dimWidth = dimWidth;
    }

    public Double getDimDepth() {
        return dimDepth;
    }

    public void setDimDepth(Double dimDepth) {
        this.dimDepth = dimDepth;
    }

    public int getCreateAdm() {
        return createAdm;
    }

    public void setCreateAdm(int createAdm) {
        this.createAdm = createAdm;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public String getProdDscEn() {
        return prodDscEn;
    }

    public void setProdDscEn(String prodDscEn) {
        this.prodDscEn = prodDscEn;
    }

    public Integer getCatNo() {
        return catNo;
    }

    public void setCatNo(Integer catNo) {
        this.catNo = catNo;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdDsc() {
        return prodDsc;
    }

    public void setProdDsc(String prodDsc) {
        this.prodDsc = prodDsc;
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }

    public List<ProductColorDTO> getProdColorList() {
        return prodColorList;
    }

    public void setProdColorList(List<ProductColorDTO> prodColorList) {
        this.prodColorList = prodColorList;
    }

    public List<ProductMaterialsDTO> getProdMaterialList() {
        return prodMaterialList;
    }

    public void setProdMaterialList(List<ProductMaterialsDTO> prodMaterialList) {
        this.prodMaterialList = prodMaterialList;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getFinishName() {
        return finishName;
    }

    public void setFinishName(String finishName) {
        this.finishName = finishName;
    }

    public Integer getStyleNo() {
        return styleNo;
    }

    public void setStyleNo(Integer styleNo) {
        this.styleNo = styleNo;
    }

    public Integer getFinishNo() {
        return finishNo;
    }

    public void setFinishNo(Integer finishNo) {
        this.finishNo = finishNo;
    }
    public Integer getDiscountNo() {
        return discountNo;
    }

    public void setDiscountNo(Integer discountNo) {
        this.discountNo = discountNo;
    }

    public Integer getWarranty() {
        return warranty;
    }

    public void setWarranty(Integer warranty) {
        this.warranty = warranty;
    }

    public List<ProductDTO> getRelatedProducts() {
        return relatedProducts;
    }

    public void setRelatedProducts(List<ProductDTO> relatedProducts) {
        this.relatedProducts = relatedProducts;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Long getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(Long viewsCount) {
        this.viewsCount = viewsCount;
    }

    public List<MultipartFile> getProdImages() {
        return prodImages;
    }

    public void setProdImages(List<MultipartFile> prodImages) {
        this.prodImages = prodImages;
    }

}
