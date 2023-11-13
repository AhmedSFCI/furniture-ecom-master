/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.CustomerDTO;
import com.furniture.ecom._entity.Customer;
import com.furniture.ecom._model.Pagging;
import java.util.List;

/**
 *
 * @author mosta
 */
public interface CustomerDAO {

    public List<Customer> getCustomerList();

    public void addCustomer(Customer customer);

    public Integer deleteCustomer(Integer customerNo);

    public void updateCustomer(Customer customer);

    public void updateCustomerPic(Customer customer);

    public Customer getCustomerByName(String customerName);

    public Customer getCustomerByEmail(String email);

    public Customer getCustomerByNo(Integer customerNo);

    public void updateCustomerPassword(Customer customer);

    public void setCustomerVerified(CustomerDTO customerDto);

    public void updateCustomerVerifiedCode(CustomerDTO customerDto);

    public Boolean checkCustomerISExists(Integer customerNo);

    public List<Customer> getMultiCustomerPageList(Pagging paging);

    public void updateCustomerPasswordCode(CustomerDTO customerDto);
}
