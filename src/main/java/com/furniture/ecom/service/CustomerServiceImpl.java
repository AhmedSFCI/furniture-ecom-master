/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom.dao.CustomerDAO;
import com.furniture.ecom._dto.CustomerDTO;
import com.furniture.ecom._entity.Customer;
import com.furniture.ecom.dao.GeneralDAO;
import com.furniture.ecom._model.Pagging;
import com.furniture.ecom._model.ResponseMessages;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mosta
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    ModelMapper mapper;
    @Autowired
    GeneralDAO generalDAO;

    @Override
    @Transactional
    public List<CustomerDTO> getCustomerList() {
        List<Customer> customerList = customerDAO.getCustomerList();
        List<CustomerDTO> customerDtoList = new ArrayList<>();
        if (customerList != null) {
            customerList.forEach(customer -> {
                customerDtoList.add(mapper.map(customer, CustomerDTO.class));
            });
        }
        return customerDtoList;
    }

    @Override
    @Transactional
    public Integer addCustomer(CustomerDTO customerDTO) {
        if (customerDTO.getCustNo() != null && customerDAO.getCustomerByNo(customerDTO.getCustNo()) != null) {
            return ResponseMessages.RECORD_IS_EXISTS_CODE;
        }
        if (customerDAO.getCustomerByEmail(customerDTO.getCustEmail()) != null) {
            return ResponseMessages.DUPLICATE_RECORD_AR_CODE;
        }
        customerDAO.addCustomer(mapper.map(customerDTO, Customer.class));
        return 1;
    }

    @Override
    @Transactional
    public Integer deleteCustomer(Integer customerNo) {
        Customer customer = customerDAO.getCustomerByNo(customerNo);
        if (customer == null) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        return customerDAO.deleteCustomer(customerNo);
    }

    @Override
    @Transactional
    public Integer updateCustomer(CustomerDTO customerDto) {
        Customer customerByNo = customerDAO.getCustomerByNo(customerDto.getCustNo());
        if (customerByNo == null) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        Customer customerByEmail = customerDAO.getCustomerByEmail(customerDto.getCustEmail());
        if (customerByEmail == null) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        if (!Objects.equals(customerByEmail.getCustNo(), customerDto.getCustNo())) {
            return ResponseMessages.RECORD_IS_EXISTS_CODE;
        }
        customerDAO.updateCustomer(mapper.map(customerDto, Customer.class));
        return 1;
    }

    @Override
    @Transactional
    public void updateCustomerPic(CustomerDTO customerDTO) {
        customerDAO.updateCustomerPic(mapper.map(customerDTO, Customer.class));
    }

    @Override
    @Transactional
    public CustomerDTO getCustomerByName(String customerName) {
        Customer customer = customerDAO.getCustomerByName(customerName);
        if (customer == null) {
            return null;
        }
        return mapper.map(customer, CustomerDTO.class);
    }

    @Override
    @Transactional
    public CustomerDTO getCustomerByNo(Integer customerNo) {
        Customer customer = customerDAO.getCustomerByNo(customerNo);
        if (customer == null) {
            return null;
        }
        return mapper.map(customer, CustomerDTO.class);
    }

    @Override
    @Transactional
    public void updateCustomerPassword(CustomerDTO customer) {
        customerDAO.updateCustomerPassword(mapper.map(customer, Customer.class));
    }

    @Override
    @Transactional
    public Boolean checkCustomerISExists(Integer customerNo) {
        return customerDAO.checkCustomerISExists(customerNo);
    }

    @Override
    @Transactional
    public CustomerDTO getCustomerByEmail(String email) {
        Customer customer = customerDAO.getCustomerByEmail(email);
        if (customer == null) {
            return null;
        }
        return mapper.map(customer, CustomerDTO.class);
    }

    @Override
    @Transactional
    public List<CustomerDTO> getMultiCustomerPageList(Pagging paging){
        List<Customer> customerList = customerDAO.getMultiCustomerPageList(paging);
        List<CustomerDTO> customerDtoList = new ArrayList<>();
        if (customerList != null) {
            customerList.forEach(customer -> {
                customerDtoList.add(mapper.map(customer, CustomerDTO.class));
            });
        }
        return customerDtoList;
    }

    @Override
    @Transactional
    public void setCustomerVerified(CustomerDTO customerDto) {
        customerDAO.setCustomerVerified(customerDto);
    }

    @Override
    @Transactional
    public void updateCustomerVerifiedCode(CustomerDTO customerDto) {
        customerDAO.updateCustomerVerifiedCode(customerDto);
    }

    @Override
    @Transactional
    public void updateCustomerPasswordCode(CustomerDTO customerDto) {
        customerDAO.updateCustomerPasswordCode(customerDto);
    }
}
