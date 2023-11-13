/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.FinishDTO;
import com.furniture.ecom._entity.Finish;
import com.furniture.ecom.dao.GeneralDAO;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom._model.ResponseMessages;
import com.furniture.ecom.dao.FinishDAO;
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
public class FinishServiceImpl implements FinishService {

    @Autowired
    FinishDAO productFinsihDao;
    @Autowired
    GeneralDAO generalDao;
    @Autowired
    ModelMapper mapper;
    @Autowired
    CommonHelper commonHelper;

    @Override
    @Transactional
    public Integer addFinish(FinishDTO finishDto) {
        if (finishDto.getFinishNo() != null && finishDto.getFinishNo() >= 1) {
            if (generalDao.checkIdIsExists("Finish", "finishNo", finishDto.getFinishNo(), null)) {
                return ResponseMessages.RECORD_IS_EXISTS_CODE;
            }
        }
        if (generalDao.checkUniquePrmtr("Finish", "finishNmAr", finishDto.getFinishNmAr())) {
            return ResponseMessages.DUPLICATE_RECORD_AR_CODE;
        }
        if (finishDto.getFinishNmEn() != null && generalDao.checkUniquePrmtr("Finish", "finishNmEn", finishDto.getFinishNmEn())) {
            return ResponseMessages.DUPLICATE_RECORD_EN_CODE;
        }
        productFinsihDao.addFinish(mapper.map(finishDto, Finish.class));
        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    @Transactional
    public Integer updateFinish(FinishDTO finishDto) {
        if (finishDto.getFinishNo() != null && finishDto.getFinishNo() >= 1) {
            if (!generalDao.checkIdIsExists("Finish", "finishNo", finishDto.getFinishNo(), null)) {
                return ResponseMessages.NOT_FOUND_RECORD_CODE;
            }
        }
        if (generalDao.checkRecordIfExists("Finish", "finishNmAr", finishDto.getFinishNmAr(), "finishNo", finishDto.getFinishNo())) {
            return ResponseMessages.DUPLICATE_RECORD_AR_CODE;
        }
        if (finishDto.getFinishNmEn() != null && generalDao.checkRecordIfExists("Finish", "finishNmEn", finishDto.getFinishNmAr(), "finishNo", finishDto.getFinishNo())) {
            return ResponseMessages.DUPLICATE_RECORD_EN_CODE;
        }
        productFinsihDao.updateFinish(finishDto);
        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    @Transactional
    public List<FinishDTO> getFinishList(Integer langNo) throws IOException {
        List<Finish> finishList = productFinsihDao.getFinishList();
        List<FinishDTO> finishDtoList = new ArrayList<>();
        if (finishList == null) {
            return null;
        }
        for (Finish finish : finishList) {
            FinishDTO productFinishDTO = mapper.map(finish, FinishDTO.class);
            productFinishDTO.setFinishNm(commonHelper.getNameBasedOnLangNo(finish.getFinishNmAr(), finish.getFinishNmEn(), langNo));
            finishDtoList.add(productFinishDTO);
        }
        return finishDtoList;
    }

    @Override
    @Transactional
    public FinishDTO getFinishByNo(Integer finishNo, Integer langNo) throws IOException {
        Finish productFinish = productFinsihDao.getFinishByNo(finishNo);
        if (productFinish == null) {
            return null;
        }
        FinishDTO productFinishDTO = mapper.map(productFinish, FinishDTO.class);
        productFinishDTO.setFinishNm(commonHelper.getNameBasedOnLangNo(productFinish.getFinishNmAr(), productFinish.getFinishNmEn(), langNo));
        productFinishDTO.setProducts(generalDao.getProductsInfo("finishNo", finishNo, null, null, langNo));
        return productFinishDTO;
    }

    @Override
    @Transactional
    public Integer deleteFinish(Integer finishNo) {
        Finish productFinish = productFinsihDao.getFinishByNo(finishNo);
        if (productFinish == null) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        if (generalDao.checkIdIsExists("Product", "finishNo", finishNo, null)) {
            return ResponseMessages.RELATED_DATA_FIELDS;
        }
        return productFinsihDao.deleteFinish(finishNo);
    }

    @Override
    @Transactional
    public boolean checkFinishISExists(Integer finishNo) {
        return generalDao.checkIdIsExists("Finish", "finishNo", finishNo, null);
    }

}
