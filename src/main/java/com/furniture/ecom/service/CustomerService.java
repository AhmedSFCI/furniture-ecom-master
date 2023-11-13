/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.CustomerDTO;
import com.furniture.ecom._model.Pagging;
import java.util.List;

/**
 *
 * @author mosta
 */
public interface CustomerService {

    public List<CustomerDTO> getCustomerList();

    public CustomerDTO getCustomerByNo(Integer customerNo);

    public CustomerDTO getCustomerByName(String customerName);

    public CustomerDTO getCustomerByEmail(String email);

    public Integer addCustomer(CustomerDTO customerDto);

    public Integer deleteCustomer(Integer customerNo);

    public Integer updateCustomer(CustomerDTO customerDto);

    public void updateCustomerPic(CustomerDTO customerDto);

    public void updateCustomerPassword(CustomerDTO customerDto);

    public Boolean checkCustomerISExists(Integer customerNo);

    public void setCustomerVerified(CustomerDTO customerDto);

    public List<CustomerDTO> getMultiCustomerPageList(Pagging paging);
    
    public void updateCustomerVerifiedCode(CustomerDTO customerDto);
    
    public void updateCustomerPasswordCode(CustomerDTO customerDto);
}
