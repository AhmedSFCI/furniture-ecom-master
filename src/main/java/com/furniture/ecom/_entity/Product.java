/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author HP
 */
@Entity
@Table(name = "product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findByProdNo", query = "SELECT p FROM Product p WHERE p.prodNo = :prodNo"),
    @NamedQuery(name = "Product.findByProdNameAr", query = "SELECT p FROM Product p WHERE p.prodNameAr = :prodNameAr"),
    @NamedQuery(name = "Product.findByProdNameEn", query = "SELECT p FROM Product p WHERE p.prodNameEn = :prodNameEn"),
    @NamedQuery(name = "Product.findByProdDscAr", query = "SELECT p FROM Product p WHERE p.prodDscAr = :prodDscAr"),
    @NamedQuery(name = "Product.findByProdAvailability", query = "SELECT p FROM Product p WHERE p.prodAvailability = :prodAvailability"),
    @NamedQuery(name = "Product.findByProdPrice", query = "SELECT p FROM Product p WHERE p.prodPrice = :prodPrice"),
    @NamedQuery(name = "Product.findByProdQuantity", query = "SELECT p FROM Product p WHERE p.prodQuantity = :prodQuantity"),
    @NamedQuery(name = "Product.findByDimHight", query = "SELECT p FROM Product p WHERE p.dimHight = :dimHight"),
    @NamedQuery(name = "Product.findByDimWidth", query = "SELECT p FROM Product p WHERE p.dimWidth = :dimWidth"),
    @NamedQuery(name = "Product.findByDimDepth", query = "SELECT p FROM Product p WHERE p.dimDepth = :dimDepth"),
    @NamedQuery(name = "Product.findByCreateAdm", query = "SELECT p FROM Product p WHERE p.createAdm = :createAdm"),
    @NamedQuery(name = "Product.findByCreateOn", query = "SELECT p FROM Product p WHERE p.createOn = :createOn"),
    @NamedQuery(name = "Product.findByProdDscEn", query = "SELECT p FROM Product p WHERE p.prodDscEn = :prodDscEn"),
    @NamedQuery(name = "Product.findByDiscountNo", query = "SELECT p FROM Product p WHERE p.discountNo = :discountNo"),
    @NamedQuery(name = "Product.findByWarranty", query = "SELECT p FROM Product p WHERE p.warranty = :warranty"),
    @NamedQuery(name = "Product.findByViewsCount", query = "SELECT p FROM Product p WHERE p.viewsCount = :viewsCount")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prod_no")
    private Long prodNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "prod_name_ar")
    private String prodNameAr;
    @Size(max = 255)
    @Column(name = "prod_name_en")
    private String prodNameEn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "prod_dsc_ar")
    private String prodDscAr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prod_availability")
    private short prodAvailability;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prod_price")
    private double prodPrice;
    @Column(name = "prod_quantity")
    private Integer prodQuantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dim_hight")
    private Double dimHight;
    @Column(name = "dim_width")
    private Double dimWidth;
    @Column(name = "dim_depth")
    private Double dimDepth;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_adm")
    private int createAdm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createOn;
    @Size(max = 2147483647)
    @Column(name = "prod_dsc_en")
    private String prodDscEn;
    @Column(name = "discount_no")
    private Integer discountNo;
    @Column(name = "warranty")
    private Integer warranty;
    @Column(name = "cat_no")
    private Integer catNo;
    @Column(name = "finish_no")
    private Integer finishNo;
    @Column(name = "style_no")
    private Integer styleNo;
    @Basic(optional = false)
    @Column(name = "views_count")
    private long viewsCount;

    public Product() {
    }

    public Product(Long prodNo) {
        this.prodNo = prodNo;
    }

    public Product(Long prodNo, String prodNameAr, String prodDscAr, short prodAvailability, double prodPrice, int createAdm, Date createOn,
             int discountNo) {
        this.prodNo = prodNo;
        this.prodNameAr = prodNameAr;
        this.prodDscAr = prodDscAr;
        this.prodAvailability = prodAvailability;
        this.prodPrice = prodPrice;
        this.createAdm = createAdm;
        this.createOn = createOn;
        this.discountNo = discountNo;
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

    public short getProdAvailability() {
        return prodAvailability;
    }

    public void setProdAvailability(short prodAvailability) {
        this.prodAvailability = prodAvailability;
    }

    public double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(double prodPrice) {
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

    public long getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(long viewsCount) {
        this.viewsCount = viewsCount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prodNo != null ? prodNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.prodNo == null && other.prodNo != null) || (this.prodNo != null && !this.prodNo.equals(other.prodNo))) {
            return false;
        }
        return true;
    }

    public Integer getCatNo() {
        return catNo;
    }

    public void setCatNo(Integer catNo) {
        this.catNo = catNo;
    }

    public Integer getFinishNo() {
        return finishNo;
    }

    public void setFinishNo(Integer finishNo) {
        this.finishNo = finishNo;
    }

    public Integer getStyleNo() {
        return styleNo;
    }

    public void setStyleNo(Integer styleNo) {
        this.styleNo = styleNo;
    }

    @Override
    public String toString() {
        return "com.furniture.ecom.entity.Product[ prodNo=" + prodNo + " ]";
    }

}
