/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.DiscountDTO;
import com.furniture.ecom._entity.Discount;

import java.util.List;

/**
 * @author HP
 */
public interface DiscountDAO
{

	Discount getDiscountList();

	Discount getDiscountByNo(Integer discountNo);

	void addDiscount(Discount discount);

	Integer deleteDiscount(Integer discountNo);

	Discount getActiveDiscount();

	Double getDiscountValue(Integer discountNo);

	void updateDiscount(Discount discount);

	boolean checkDiscountIsActive(Integer discountNo);

	boolean checkDiscountIsExists(Integer discountNo);
}
