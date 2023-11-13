/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.CustomerDTO;
import com.furniture.ecom._entity.Customer;
import com.furniture.ecom._entity.Product;
import com.furniture.ecom._model.Pagging;
import java.util.List;
import javax.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mosta
 */
@Repository
public class CustomerDAOImpl implements CustomerDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomerList() {
        Session currentSession = sessionFactory.getCurrentSession();
        String hql = "From Customer ORDER BY custNo";
        Query query = currentSession.createQuery(hql);
        query.setMaxResults(10);
        return query.getResultList();
    }

    @Override
    public void addCustomer(Customer customer) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer deleteCustomer(Integer customerNo) {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "DELETE FROM Customer WHERE custNo = :custNo";
        Query query = session.createQuery(HQL);
        query.setParameter("custNo", customerNo);
        return query.executeUpdate();
    }

    @Override
    public void updateCustomer(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        Customer persistEntity = session.get(Customer.class, customer.getCustNo());
        if (persistEntity != null) {
            persistEntity.setCustName(customer.getCustName());
          //  persistEntity.setCustEmail(customer.getCustEmail());
            if (customer.getCustPhone() != null && !customer.getCustPhone().isBlank()) {
                persistEntity.setCustPhone(customer.getCustPhone());
            }
            if (customer.getCustCountry() != null && customer.getCustCountry() >= 1) {
                persistEntity.setCustCountry(customer.getCustCountry());
            }
            if (customer.getCustCity() != null && customer.getCustCity() >= 1) {
                persistEntity.setCustCity(customer.getCustCity());
            }
            session.merge(persistEntity);
        }

    }

    @Override
    public void updateCustomerPic(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        Customer PersistEntity = session.get(Customer.class, customer.getCustNo());
        if (PersistEntity != null) {
            PersistEntity.setCustPic(customer.getCustPic());
        }
        session.merge(PersistEntity);
    }

    @Override
    public Customer getCustomerByName(String customerName) {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "FROM Customer WHERE custName = :custName";
        javax.persistence.Query query = session.createQuery(HQL);
        query.setParameter("custName", customerName);
        try {
            Customer customer = (Customer) query.getSingleResult();
            if (customer == null) {
                return null;
            }
            return customer;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "FROM Customer WHERE custEmail = :custEmail";
        javax.persistence.Query query = session.createQuery(HQL);
        query.setParameter("custEmail", email);
        try {
            Customer customer = (Customer) query.getSingleResult();
            if (customer == null) {
                return null;
            }
            return customer;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Customer getCustomerByNo(Integer customerNo) {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "FROM Customer WHERE custNo = :custNo";
        Query query = session.createQuery(HQL);
        query.setParameter("custNo", customerNo);
        try {
            Customer customer = (Customer) query.getSingleResult();
            if (customer == null) {
                return null;
            }
            return customer;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void updateCustomerPassword(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        Customer PersistEntity = session.get(Customer.class, customer.getCustNo());
        if (PersistEntity != null) {
            PersistEntity.setCustPassword(customer.getCustPassword());
        }
        session.merge(PersistEntity);
    }

    @Override
    public Boolean checkCustomerISExists(Integer customerNo) {
        Session session = sessionFactory.getCurrentSession();
        Customer customer = session.get(Customer.class, customerNo);
        if (customer != null) {
            return true;
        }
        return false;
    }

    @Override
    public List<Customer> getMultiCustomerPageList(Pagging paging) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Customer ORDER BY custNo";
        javax.persistence.Query query = session.createQuery(hql);
        query.setFirstResult(paging.getItmPerPage() * (paging.getPageNo() - 1));
        query.setMaxResults(paging.getItmPerPage());
        List<Customer> customerList = query.getResultList();
        return customerList;
    }

    @Override
    public void setCustomerVerified(CustomerDTO customerDto) {
        Session session = sessionFactory.getCurrentSession();
        Customer persistEntity = session.get(Customer.class, customerDto.getCustNo());
        if (persistEntity != null) {
            persistEntity.setVerifiedFlg((short) 1);
            session.merge(persistEntity);
        }
    }

    @Override
    public void updateCustomerVerifiedCode(CustomerDTO customerDto) {
        Session session = sessionFactory.getCurrentSession();
        Customer persistEntity = session.get(Customer.class, customerDto.getCustNo());
        if (persistEntity != null) {
            persistEntity.setVerifiedFlg((short) 0);
            persistEntity.setVerifyCode(customerDto.getVerifyCode());
            persistEntity.setVerifyExpirDate(customerDto.getVerifyExpirDate());
            session.merge(persistEntity);
        }
    }

    @Override
    public void updateCustomerPasswordCode(CustomerDTO customerDto) {
        Session session = sessionFactory.getCurrentSession();
        Customer persistEntity = session.get(Customer.class, customerDto.getCustNo());
        if (persistEntity != null) {
            persistEntity.setVerifyPasswordCode(customerDto.getVerifyPasswordCode());
            persistEntity.setVerifyPasswordExpirDate(customerDto.getVerifyPasswordExpirDate());
            session.merge(persistEntity);
        }
    }

}
