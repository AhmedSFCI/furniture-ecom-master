/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.TaxesDTO;
import com.furniture.ecom._entity.Taxes;
import java.util.List;

/**
 *
 * @author HP
 */
public interface TaxesDAO {
    
    public Taxes getTaxes();

//    public Taxes getTaxesByNo(Integer taxesNo);
//    public void addTaxes(Taxes taxes);
    
    public void editTaxes(TaxesDTO taxes);

//    public int deleteTaxes(Integer taxesNo);
    
    public boolean checkIFTaxesIsExists(Integer taxesNo);

    public boolean checkIFTaxesIsEnabled(Integer taxesNo);

    public Taxes getEnabledTaxesList();
}
