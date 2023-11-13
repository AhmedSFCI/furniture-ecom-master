/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.TaxesDTO;
import com.furniture.ecom._entity.Taxes;
import com.furniture.ecom.dao.TaxesDAO;
import com.furniture.ecom._model.ResponseMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author HP
 */
@Service
public class TaxesServiceImpl implements TaxesService {

    @Autowired
    TaxesDAO taxesDao;

    @Autowired
    ModelMapper mapper;

    @Override
    @Transactional
    public TaxesDTO getTaxesList() {
        Taxes taxes = taxesDao.getTaxes();
        if (taxes == null) {
            return null;
        }
        return mapper.map(taxes, TaxesDTO.class);
    }

    @Override
    @Transactional
    public Integer updateTaxes(TaxesDTO taxesDto) {
        if (!taxesDao.checkIFTaxesIsExists(taxesDto.getTaxNo())) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        taxesDao.editTaxes(taxesDto);
        return 1;
    }

    @Override
    @Transactional
    public boolean checkIFTaxesIsExists(Integer taxesNo) {
        return taxesDao.checkIFTaxesIsExists(taxesNo);
    }

    @Override
    @Transactional
    public boolean checkIFTaxesIsEnabled(Integer taxesNo) {
        return taxesDao.checkIFTaxesIsEnabled(taxesNo);
    }

    @Override
    @Transactional
    public TaxesDTO getEnabledTaxesList() {
        Taxes taxes = taxesDao.getEnabledTaxesList();
        if (taxes == null) {
            return null;
        }
        return mapper.map(taxes, TaxesDTO.class);
    }

}
