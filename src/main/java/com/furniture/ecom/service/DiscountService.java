/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.DiscountDTO;
import com.furniture.ecom._entity.Discount;

/**
 *
 * @author HP
 */
public interface DiscountService {

    public DiscountDTO getDiscount();

    DiscountDTO getDiscountByNo(Integer discountNo);

    Integer addDiscount(DiscountDTO discountDTO);

    Integer deleteDiscount(Integer discountNo);

    public DiscountDTO getActiveDiscount();

    public Integer updateDiscount(DiscountDTO discountDto);

    public boolean checkDiscountIsActive(Integer discountNo);

    public boolean checkDiscountIsExists(Integer discountNo);
}
