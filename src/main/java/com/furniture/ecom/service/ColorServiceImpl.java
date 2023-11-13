/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.ColorDTO;
import com.furniture.ecom._dto.ProductColorDTO;
import com.furniture.ecom._entity.Color;
import com.furniture.ecom._entity.ProductColor;
import com.furniture.ecom._entity.ProductColorPK;
import com.furniture.ecom.dao.GeneralDAO;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom._helpers.CommonValidationHelper;
import com.furniture.ecom._model.ResponseMessages;
import com.furniture.ecom.dao.ColorDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author HP
 */
@Service
public class ColorServiceImpl implements ColorService {

    @Autowired
    ColorDAO colorDao;
    @Autowired
    GeneralDAO generalDao;
    @Autowired
    ModelMapper mapper;
    @Autowired
    CommonHelper commonHelper;
    @Autowired
    CommonValidationHelper commonValHelper;

    @Override
    @Transactional
    public Integer addColor(ColorDTO colorDto) {
        if (colorDto.getColorNo() != null && colorDto.getColorNo() >= 1) {
            if (generalDao.checkIdIsExists("Color", "colorNo", colorDto.getColorNo(), null)) {
                return ResponseMessages.RECORD_IS_EXISTS_CODE;
            }
        }
        if (generalDao.checkUniquePrmtr("Color", "colorNmAr", colorDto.getColorNmAr())) {
            return ResponseMessages.DUPLICATE_RECORD_AR_CODE;
        }
        if (commonValHelper.checkStringValueIsValid(colorDto.getColorNmEn()) && generalDao.checkUniquePrmtr("Color", "colorNmAr", colorDto.getColorNmEn())) {
            return ResponseMessages.DUPLICATE_RECORD_EN_CODE;
        }
        if (commonValHelper.checkStringValueIsValid(colorDto.getColorNmEn()) && generalDao.checkUniquePrmtr("Color", "colorNmEn", colorDto.getColorNmEn())) {
            return ResponseMessages.DUPLICATE_RECORD_EN_CODE;
        }
        colorDao.addColor(mapper.map(colorDto, Color.class));
        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    @Transactional
    public Integer updateColor(ColorDTO colorDto) {
        if (colorDto.getColorNo() != null && colorDto.getColorNo() >= 1) {
            if (!generalDao.checkIdIsExists("Color", "colorNo", colorDto.getColorNo(), null)) {
                return ResponseMessages.NOT_FOUND_RECORD_CODE;
            }
        }
        if (generalDao.checkRecordIfExists("Color", "colorNmAr", colorDto.getColorNmAr(), "colorNo", colorDto.getColorNo())) {
            return ResponseMessages.DUPLICATE_RECORD_AR_CODE;
        }
        if (commonValHelper.checkStringValueIsValid(colorDto.getColorNmEn()) && generalDao.checkRecordIfExists("Color", "colorNmEn", colorDto.getColorNmEn(), "colorNo", colorDto.getColorNo())) {
            return ResponseMessages.DUPLICATE_RECORD_EN_CODE;
        }
        colorDao.updateColor(colorDto);
        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    @Transactional
    public List<ColorDTO> getColorList(Integer langNo) throws IOException {
        List<Color> colorList = colorDao.getColorList();
        List<ColorDTO> colorDtoList = new ArrayList<>();
        if (colorList == null) {
            return null;
        }
        for (Color color : colorList) {
            ColorDTO productColorDTO = mapper.map(color, ColorDTO.class);
            productColorDTO.setColorNm(commonHelper.getNameBasedOnLangNo(color.getColorNmAr(), color.getColorNmEn(), langNo));
            colorDtoList.add(productColorDTO);
        }
        return colorDtoList;
    }

    @Override
    @Transactional
    public ColorDTO getColorByNo(Integer colorNo, Integer langNo) throws IOException {
        Color productColor = colorDao.getColorByNo(colorNo);
        if (productColor == null) {
            return null;
        }
        ColorDTO productColorDTO = mapper.map(productColor, ColorDTO.class);
        productColorDTO.setColorNm(commonHelper.getNameBasedOnLangNo(productColor.getColorNmAr(), productColor.getColorNmEn(), langNo));
        return productColorDTO;
    }

    @Override
    @Transactional
    public Integer deleteColor(Integer colorNo) {
        Color productColor = colorDao.getColorByNo(colorNo);
        if (productColor == null) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        colorDao.deleteProductColorByColorNo(colorNo);
        return colorDao.deleteColor(colorNo);
    }

    @Override
    @Transactional
    public boolean checkColorISExists(Integer colorNo) {
        return generalDao.checkIdIsExists("Color", "colorNo", colorNo, null);
    }

    @Override
    @Transactional
    public Integer addProductColor(ProductColorDTO productColorDto) {
        if (generalDao.checkIdIsExists("ProductColor", "productColorPK.colorNo", productColorDto.getColorNo(), " AND productColorPK.prodNo = " + productColorDto.getProdNo())) {
            return ResponseMessages.RECORD_IS_EXISTS_CODE;
        }
        ProductColor prodColor = mapper.map(productColorDto, ProductColor.class);
        ProductColorPK colorPK = new ProductColorPK(productColorDto.getColorNo(), productColorDto.getProdNo());
        prodColor.setProductColorPK(colorPK);
        colorDao.addProductColor(prodColor);
        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    @Transactional
    public Integer addProductColorList(List<ProductColorDTO> productColorDtoList) {
        List<ProductColor> productColorList = new ArrayList();
        for (ProductColorDTO productColorDto : productColorDtoList) {
            if (generalDao.checkIdIsExists("ProductColor", "productColorPK.colorNo", productColorDto.getColorNo(), " AND productColorPK.prodNo = " + productColorDto.getProdNo())) {
                return ResponseMessages.RECORD_IS_EXISTS_CODE;
            }
            ProductColor prodColor = mapper.map(productColorDto, ProductColor.class);
            ProductColorPK colorPK = new ProductColorPK(productColorDto.getColorNo(), productColorDto.getProdNo());
            prodColor.setProductColorPK(colorPK);
            productColorList.add(prodColor);
        }
        colorDao.addProductColorList(productColorList);
        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    @Transactional
    public Integer updateProductColor(ProductColorDTO productColorDto) {
        if (!generalDao.checkIdIsExists("ProductColor", "productColorPK.colorNo", productColorDto.getColorNo(), " AND productColorPK.prodNo = " + productColorDto.getProdNo())) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        colorDao.updateProductColor(productColorDto);
        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    @Transactional
    public Integer deleteProductColorByColorNo(Integer colorNo) {
        return colorDao.deleteColor(colorNo);
    }

    @Override
    @Transactional
    public Integer deleteProductColorByProdNo(Long prodNo) {
        return colorDao.deleteProductColorByProdNo(prodNo);
    }

    @Override
    @Transactional
    public Integer deleteProductColor(Long prodNo, Integer colorNo) {
        if (!generalDao.checkIdIsExists("ProductColor", "productColorPK.colorNo", colorNo, " AND productColorPK.prodNo = " + prodNo)) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        return colorDao.deleteProductColor(prodNo, colorNo);
    }

}
