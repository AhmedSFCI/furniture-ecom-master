/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.DiscountDTO;
import com.furniture.ecom._entity.Discount;
import com.furniture.ecom.dao.DiscountDAO;
import com.furniture.ecom.dao.GeneralDAO;
import com.furniture.ecom._helpers.CommonValidationHelper;
import com.furniture.ecom._model.ResponseMessages;
import com.furniture.ecom._util.ObjectChecker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author HP
 */
@Service
public class DiscountServiceImpl implements DiscountService
{

	@Autowired
	DiscountDAO discountDao;
	@Autowired
	ModelMapper mapper;

	@Autowired
	GeneralDAO generalDao;
	@Autowired
	CommonValidationHelper commonValHelper;

	@Override
	@Transactional
	public DiscountDTO getDiscount()
	{
		Discount discount = discountDao.getDiscountList();
		if (discount == null)
		{
			return null;
		}
		return mapper.map(discount, DiscountDTO.class);
	}

	@Override
	@Transactional
	public DiscountDTO getDiscountByNo(Integer discountNo)
	{
		Discount discount = discountDao.getDiscountByNo(discountNo);
		if (ObjectChecker.isEmptyOrNull(discount))
		{
			return null;
		}
		return mapper.map(discount, DiscountDTO.class);
	}

	@Override
	@Transactional
	public Integer addDiscount(DiscountDTO discountDTO)
	{
		if (checkDiscountIsExists(discountDTO.getDiscountNo()))
			return ResponseMessages.RECORD_IS_EXISTS_CODE;
		discountDao.addDiscount(mapper.map(discountDTO, Discount.class));
		return 1; // mean added successfully -- should refactor this later
	}

	@Override
	@Transactional
	public Integer deleteDiscount(Integer discountNo)
	{
		Discount discount = discountDao.getDiscountByNo(discountNo);
		if (ObjectChecker.isEmptyOrNull(discount))
			return ResponseMessages.NOT_FOUND_RECORD_CODE;
		return discountDao.deleteDiscount(discountNo);
	}

	@Override
	@Transactional
	public DiscountDTO getActiveDiscount()
	{
		Discount discount = discountDao.getActiveDiscount();
		if (discount == null)
		{
			return null;
		}
		return mapper.map(discount, DiscountDTO.class);
	}

	@Override
	@Transactional
	public Integer updateDiscount(DiscountDTO discountDto)
	{
		if (discountDao.checkDiscountIsExists(discountDto.getDiscountNo()))
		{
			discountDao.updateDiscount(mapper.map(discountDto, Discount.class));
			return 1;
		}
		return ResponseMessages.NOT_FOUND_RECORD_CODE;

	}

	@Override
	@Transactional
	public boolean checkDiscountIsActive(Integer discountNo)
	{
		return discountDao.checkDiscountIsActive(discountNo);
	}

	@Override
	@Transactional
	public boolean checkDiscountIsExists(Integer discountNo)
	{
		return discountDao.checkDiscountIsExists(discountNo);
	}

}
