/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom.dao.CityDAO;
import com.furniture.ecom._dto.CityDTO;
import com.furniture.ecom._entity.City;
import com.furniture.ecom.dao.GeneralDAO;
import com.furniture.ecom._helpers.CommonValidationHelper;
import com.furniture.ecom._model.ResponseMessages;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import com.furniture.ecom._util.ObjectChecker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mostafa Atta
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityDAO cityDao;

    @Autowired
    GeneralDAO generalDao;

    @Autowired
    ModelMapper mapper;
    @Autowired
    CommonValidationHelper commonValHelper;

    @Override
    @Transactional
    public List<CityDTO> getCityList() {
        List<City> cityList = cityDao.getCityList();
        List<CityDTO> cityDtoList = new ArrayList<>();
        if (cityList == null || cityList.isEmpty()) {
            return new ArrayList<>();
        }
        cityList.forEach(city -> cityDtoList.add(mapper.map(city, CityDTO.class)));
        return cityDtoList;
    }

    @Override
    @Transactional
    public CityDTO getCityByNo(Integer cityNo) {
        City city = cityDao.getCityByNo(cityNo);
        if (city == null) {
            return null;
        }
        return mapper.map(city, CityDTO.class);
    }

    @Override
    @Transactional
    public Integer addCity(CityDTO cityDto) {
        if (checkIFCityIsExists(cityDto.getCityNo())) {
            return ResponseMessages.RECORD_IS_EXISTS_CODE;
        }
        if (generalDao.checkUniquePrmtr("City", "cityNmAr", cityDto.getCityNmAr())) {
            return ResponseMessages.DUPLICATE_RECORD_AR_CODE;
        }
        if (commonValHelper.checkStringValueIsValid(cityDto.getCityNmEn())) {
            if (generalDao.checkUniquePrmtr("City", "cityNmEn", cityDto.getCityNmEn())) {
                return ResponseMessages.DUPLICATE_RECORD_EN_CODE;
            }
        }
        cityDao.addCity(mapper.map(cityDto, City.class));
        return 1; // mean added successfully -- should refactor this later
    }

    @Override
    @Transactional
    public Integer editCity(CityDTO cityDto) {
        City city = cityDao.getCityByNo(cityDto.getCityNo());
        if (ObjectChecker.isEmptyOrNull(city)) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        if (generalDao.checkRecordIfExists("City", "cityNmAr", cityDto.getCityNmAr(), "cityNo", cityDto.getCityNo())) {
            return ResponseMessages.DUPLICATE_RECORD_AR_CODE;
        }
        if (commonValHelper.checkStringValueIsValid(cityDto.getCityNmEn())) {
            if (generalDao.checkRecordIfExists("City", "cityNmEn", cityDto.getCityNmEn(), "cityNo", cityDto.getCityNo())) {
                return ResponseMessages.DUPLICATE_RECORD_EN_CODE;
            }
        }
        cityDao.editCity(mapper.map(cityDto, City.class));
        return 1;
    }

    @Override
    @Transactional
    public Integer deleteCity(Integer cityNo) {
        City city = cityDao.getCityByNo(cityNo);
        if (city == null) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        return cityDao.deleteCity(cityNo);
    }

    @Override
    @Transactional
    public boolean checkIFCityIsExists(Integer cityNo) {
        return cityDao.checkIFCityIsExists(cityNo);
    }

}
