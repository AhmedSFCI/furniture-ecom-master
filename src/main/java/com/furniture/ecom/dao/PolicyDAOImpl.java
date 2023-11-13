/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._entity.Policy;
import com.furniture.ecom._entity.Policy;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mosta
 */
@Repository
public class PolicyDAOImpl implements PolicyDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Policy> getPolicyList() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Policy ORDER BY orderNo , policyNo";
        Query query = session.createQuery(hql);
        return query.getResultList();
    }

    @Override
    public Policy getPolicyByNo(Integer policyNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Policy WHERE policyNo = :policyNo";
        Query query = session.createQuery(hql);
        query.setParameter("policyNo", policyNo);
        try {
            Policy policy = (Policy) query.getSingleResult();
            return policy;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void addPolicy(Policy policy) {
        Session session = sessionFactory.getCurrentSession();
        session.save(policy);
    }

    @Override
    public void editPolicy(Policy policy) {
        Session session = sessionFactory.getCurrentSession();
        Policy persistEntity = session.get(Policy.class, policy.getPolicyNo());
        if (persistEntity != null) {
            persistEntity.setPolicyTxtAr(policy.getPolicyTxtAr());
            persistEntity.setPolicyTxtEn(policy.getPolicyTxtEn());
            if (policy.getOrderNo() >= 1) {
                persistEntity.setOrderNo(policy.getOrderNo());
            }
            if ((policy.getEnabled() != null) && policy.getEnabled() == 0 || policy.getEnabled() == 1) {
                persistEntity.setEnabled(policy.getEnabled());
            }
            session.merge(persistEntity);
        }
    }

    @Override
    public int deletePolicy(Integer policyNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM Policy WHERE policyNo = :policyNo";
        Query query = session.createQuery(hql);
        query.setParameter("policyNo", policyNo);
        return query.executeUpdate(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkIFPolicyIsExists(Integer policyNo) {
        boolean flag = false;
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COALESCE(COUNT (*),0) FROM Policy WHERE policyNo = :policyNo";
        Query query = session.createQuery(hql);
        query.setParameter("policyNo", policyNo);
        try {
            Long count = (Long) query.getSingleResult();
            System.out.println("check policy = " + count);
            if (count != null && count > 0) {
                return true;
            }
        } catch (NoResultException ex) {
            return flag;
        }
        return flag; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Policy> getEnabledPolicyList() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Policy WHERE enabled = :enabled ORDER BY orderNo , policyNo";
        Query query = session.createQuery(hql);
        query.setParameter("enabled", 1);
        try {
            List<Policy> policyList = query.getResultList();
            return policyList;
        } catch (NoResultException ex) {
            return null;
        }
    }

}
