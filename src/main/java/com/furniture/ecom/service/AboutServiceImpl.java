/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom.dao.AboutDAO;
import com.furniture.ecom._dto.AboutDTO;
import com.furniture.ecom._entity.About;
import com.furniture.ecom._helpers.CommonValidationHelper;
import com.furniture.ecom._model.ResponseMessages;
import com.furniture.ecom.dao.GeneralDAO;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class AboutServiceImpl implements AboutService {

    @Autowired
    AboutDAO aboutDao;

    @Autowired
    GeneralDAO generalDao;

    @Autowired
    ModelMapper mapper;

    @Autowired
    CommonValidationHelper commonValHelper;

    @Override
    @Transactional
    public List<AboutDTO> getAboutList() {
        List<About> aboutList = aboutDao.getAboutList();
        List<AboutDTO> aboutDtoList = new ArrayList<>();

        if (aboutList != null) {
            for (About about : aboutList) {
                aboutDtoList.add(mapper.map(about, AboutDTO.class));
            }
        }
        return aboutDtoList;

    }

    @Override
    @Transactional
    public AboutDTO getAboutByNo(Integer aboutNo) {
        About about = aboutDao.getAboutByNo(aboutNo);
        if (about != null) {
            return mapper.map(about, AboutDTO.class);
        }
        return null;
    }

    @Override
    @Transactional
    public Integer addAbout(AboutDTO aboutDto) {
        if (checkIFAboutIsExists(aboutDto.getAboutNo())) {
            return ResponseMessages.RECORD_IS_EXISTS_CODE;
        }
        if (generalDao.checkUniquePrmtr("About", "aboutTxtAr", aboutDto.getAboutTxtAr())) {
            return ResponseMessages.DUPLICATE_RECORD_AR_CODE;
        }
        if (commonValHelper.checkStringValueIsValid(aboutDto.getAboutTxtEn())) {
            if (generalDao.checkUniquePrmtr("About", "aboutTxtEn", aboutDto.getAboutTxtEn())) {
                return ResponseMessages.DUPLICATE_RECORD_EN_CODE;
            }
        }
        if (aboutDto.getOrderNo() == null || aboutDto.getOrderNo() <= 0) {
            aboutDto.setOrderNo(generalDao.getNextOrderNo("About"));
        }
        aboutDao.addAbout(mapper.map(aboutDto, About.class));
        return 1;
    }

    @Override
    @Transactional
    public Integer editAbout(AboutDTO aboutDto) {
        if (!aboutDao.checkIFAboutIsExists(aboutDto.getAboutNo())) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        if (generalDao.checkRecordIfExists("About", "aboutTxtAr", aboutDto.getAboutTxtAr(), "aboutNo", aboutDto.getAboutNo())) {
            return ResponseMessages.DUPLICATE_RECORD_AR_CODE;
        }
        if (commonValHelper.checkStringValueIsValid(aboutDto.getAboutTxtEn())) {
            if (generalDao.checkRecordIfExists("About", "aboutTxtEn", aboutDto.getAboutTxtEn(), "aboutNo", aboutDto.getAboutNo())) {
                return ResponseMessages.DUPLICATE_RECORD_EN_CODE;
            }
        }
        aboutDao.editAbout(mapper.map(aboutDto, About.class));
        return 1;
    }

    @Override
    @Transactional
    public Integer deleteAbout(Integer aboutNo) {
        if (!aboutDao.checkIFAboutIsExists(aboutNo)) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        return aboutDao.deleteAbout(aboutNo);
    }

    @Override
    @Transactional
    public boolean checkIFAboutIsExists(Integer aboutNo) {
        return aboutDao.checkIFAboutIsExists(aboutNo);
    }

    @Override
    @Transactional
    public List<AboutDTO> getEnabledAboutList() {
        List<About> aboutList = aboutDao.getAboutList();
        List<AboutDTO> aboutDtoList = new ArrayList<>();
        if (aboutList != null) {
            for (About about : aboutList) {
                aboutDtoList.add(mapper.map(about, AboutDTO.class));
            }
        }
        return aboutDtoList;
    }

}
