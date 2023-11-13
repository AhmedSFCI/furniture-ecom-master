/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.ProductDTO;
import com.furniture.ecom._dto.StyleDTO;
import com.furniture.ecom._entity.Style;
import com.furniture.ecom.dao.GeneralDAO;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom._model.ResponseMessages;
import com.furniture.ecom.dao.StyleDAO;
import com.furniture.ecom._util.ObjectChecker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HP
 */
@Service
public class StyleServiceImpl implements StyleService
{

	@Autowired
	StyleDAO productStyleDao;
	@Autowired
	GeneralDAO generalDao;
	@Autowired
	ModelMapper mapper;
	@Autowired
	CommonHelper commonHelper;

	@Override
	@Transactional
	public Integer addStyle(StyleDTO prodStyleDto)
	{
		if (prodStyleDto.getStyleNo() != null && prodStyleDto.getStyleNo() >= 1)
		{
			if (generalDao.checkIdIsExists("Style", "style_no", prodStyleDto.getStyleNo(), null))
			{
				return ResponseMessages.RECORD_IS_EXISTS_CODE;
			}
		}
		if (generalDao.checkUniquePrmtr("Style", "style_nm_ar", prodStyleDto.getStyleNmAr()))
		{
			return ResponseMessages.DUPLICATE_RECORD_AR_CODE;
		}
		if (prodStyleDto.getStyleNmEn() != null && generalDao.checkUniquePrmtr("Style", "style_nm_en", prodStyleDto.getStyleNmEn()))
		{
			return ResponseMessages.DUPLICATE_RECORD_EN_CODE;
		}
		productStyleDao.addStyle(mapper.map(prodStyleDto, Style.class));
		return ResponseMessages.SUCCESS_OPERATION;
	}

	@Override
	@Transactional
	public Integer updateStyle(StyleDTO prodStyleDto)
	{
		if (prodStyleDto.getStyleNo() != null && prodStyleDto.getStyleNo() >= 1)
		{
			if (!generalDao.checkIdIsExists("Style", "style_no", prodStyleDto.getStyleNo(), null))
			{
				return ResponseMessages.NOT_FOUND_RECORD_CODE;
			}
		}
		if (generalDao.checkRecordIfExists("Style", "style_nm_ar", prodStyleDto.getStyleNmAr(), "style_no", prodStyleDto.getStyleNo()))
		{
			return ResponseMessages.DUPLICATE_RECORD_AR_CODE;
		}
		if (prodStyleDto.getStyleNmEn() != null && generalDao
				.checkRecordIfExists("Style", "style_nm_en", prodStyleDto.getStyleNmAr(), "style_no", prodStyleDto.getStyleNo()))
		{
			return ResponseMessages.DUPLICATE_RECORD_EN_CODE;
		}
		productStyleDao.updateStyle(prodStyleDto);
		return ResponseMessages.SUCCESS_OPERATION;
	}

	@Override
	@Transactional
	public List<StyleDTO> getStyleList(Integer langNo) throws IOException
	{
		List<Style> styleList = productStyleDao.getStyleList();
		List<StyleDTO> styleDtoList = new ArrayList<>();
		if (styleList == null)
		{
			return null;
		}
		for (Style style : styleList)
		{
			StyleDTO productStyleDTO = mapper.map(style, StyleDTO.class);
			productStyleDTO.setStyleNm(commonHelper.getNameBasedOnLangNo(style.getStyleNmAr(), style.getStyleNmEn(), langNo));
			styleDtoList.add(productStyleDTO);
		}
		return styleDtoList;
	}

	@Override
	@Transactional
	public StyleDTO getStyleByNo(Integer styleNo, Integer langNo) throws IOException
	{
		Style productStyle = productStyleDao.getStyleByNo(styleNo);
		if (ObjectChecker.isEmptyOrNull(productStyle))
		{
			return null;
		}
		StyleDTO productStyleDTO = mapper.map(productStyle, StyleDTO.class);
		productStyleDTO.setStyleNm(commonHelper.getNameBasedOnLangNo(productStyle.getStyleNmAr(), productStyle.getStyleNmEn(), langNo));
		List<ProductDTO> products = generalDao.getProductsInfo("styleNo", styleNo, null, null, langNo);
		productStyleDTO.setProducts(products);
		return productStyleDTO;
	}

	@Override
	@Transactional
	public Integer deleteStyle(Integer styleNo)
	{
		Style productStyle = productStyleDao.getStyleByNo(styleNo);
		if (productStyle == null)
		{
			return ResponseMessages.NOT_FOUND_RECORD_CODE;
		}
		if (generalDao.checkIdIsExists("Product", "style_no", styleNo, null))
		{
			return ResponseMessages.RELATED_DATA_FIELDS;
		}
		return productStyleDao.deleteStyle(styleNo);
	}

	@Override
	@Transactional
	public boolean checkStyleISExists(Integer styleNo)
	{
		return generalDao.checkIdIsExists("Style", "style_no", styleNo, null);
	}
}
