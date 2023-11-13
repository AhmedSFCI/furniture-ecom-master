/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.TaxesDTO;
import com.furniture.ecom._entity.About;
import com.furniture.ecom._entity.Taxes;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HP
 */
@Repository
public class TaxesDAOImpl implements TaxesDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Taxes getTaxes() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Taxes ORDER BY taxNo";
        Query query = session.createQuery(hql);
        List<Taxes> taxes = query.getResultList();
        if (taxes != null && !taxes.isEmpty()) {
            return taxes.get(0);
        }
        return null;
    }

    @Override
    public void editTaxes(TaxesDTO taxes) {
        Session session = sessionFactory.getCurrentSession();
        Taxes persistEntity = session.get(Taxes.class, taxes.getTaxNo());
        if (persistEntity != null) {
            persistEntity.setPercentage(taxes.getPercentage());
            if (taxes.getEnabled() != null && (taxes.getEnabled() == 1 || taxes.getEnabled() == 0)) {
                persistEntity.setEnabled(taxes.getEnabled());
            }
            session.merge(persistEntity);
        }
    }

    @Override
    public boolean checkIFTaxesIsExists(Integer taxesNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Taxes WHERE taxNo = :no";
        Query query = session.createQuery(hql);
        query.setParameter("no", taxesNo);
        List<Taxes> taxes = query.getResultList();
        if (taxes != null && !taxes.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkIFTaxesIsEnabled(Integer taxesNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Taxes WHERE taxNo = :no AND enabled = 1";
        Query query = session.createQuery(hql);
        query.setParameter("no", taxesNo);
        List<Taxes> taxes = query.getResultList();
        if (taxes != null && !taxes.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public Taxes getEnabledTaxesList() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Taxes WHERE enabled = 1";
        Query query = session.createQuery(hql);
        List<Taxes> taxes = query.getResultList();
        if (taxes != null && !taxes.isEmpty()) {
            return taxes.get(0);
        }
        return null;
    }

}
