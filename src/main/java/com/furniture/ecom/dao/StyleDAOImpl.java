/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.StyleDTO;
import com.furniture.ecom._entity.Style;
import java.util.List;
import javax.persistence.NoResultException;
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
public class StyleDAOImpl implements StyleDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Style> getStyleList() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Style ORDER BY styleNo";
        Query query = session.createQuery(hql);
        return query.getResultList();
    }

    @Override
    public Style getStyleByNo(Integer styleNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Style WHERE styleNo = :styleNo";
        Query query = session.createQuery(hql);
        query.setParameter("styleNo", styleNo);
        try {
            Style styleuct = (Style) query.getSingleResult();
            return styleuct;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Integer deleteStyle(Integer styleNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM Style WHERE styleNo = :styleNo";
        Query query = session.createQuery(hql);
        query.setParameter("styleNo", styleNo);
        return query.executeUpdate();
    }

    @Override
    public Style addStyle(Style styleuct) {
        Session session = sessionFactory.getCurrentSession();
        session.save(styleuct);
        return styleuct;
    }

    @Override
    public void updateStyle(StyleDTO styleuctDto) {
        Session session = sessionFactory.getCurrentSession();
        Style persistEntity = session.get(Style.class, styleuctDto.getStyleNo());
        if (persistEntity != null) {
            persistEntity.setStyleNmAr(styleuctDto.getStyleNmAr());
            if (styleuctDto.getStyleNmEn() != null && !styleuctDto.getStyleNmEn().isBlank()) {
                persistEntity.setStyleNmEn(styleuctDto.getStyleNmEn());
            }
            session.merge(persistEntity);
        }
    }

    @Override
    public boolean checkStyleISExists(Integer styleNo) {
        boolean flag = false;
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COALESCE(COUNT (*),0) FROM Style WHERE styleNo = :styleNo";
        Query query = session.createQuery(hql);
        query.setParameter("styleNo", styleNo);
        try {
            Long count = (Long) query.getSingleResult();

            if (count != null && count > 0) {
                return true;
            }
        } catch (NoResultException ex) {
            return flag;
        }
        return flag;
    }

}
