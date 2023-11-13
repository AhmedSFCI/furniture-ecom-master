/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.TaxesDTO;
import com.furniture.ecom._entity.Taxes;

/**
 *
 * @author HP
 */
public interface TaxesService {

    public TaxesDTO getTaxesList();

//    public Taxes getTaxesByNo(Integer taxesNo);
//    public void addTaxes(Taxes taxes);
    public Integer updateTaxes(TaxesDTO taxesDto);

//    public int deleteTaxes(Integer taxesNo);
    public boolean checkIFTaxesIsExists(Integer taxesNo);

    public boolean checkIFTaxesIsEnabled(Integer taxesNo);

    public TaxesDTO getEnabledTaxesList();
}
