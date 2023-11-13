/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._entity.About;
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
public class AboutDAOImpl implements AboutDAO {
    
    @Autowired
    SessionFactory sessionFactory;
    
    @Override
    public List<About> getAboutList() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From About ORDER BY orderNo , aboutNo";
        Query query = session.createQuery(hql);
        return query.getResultList();
    }
    
    @Override
    public About getAboutByNo(Integer aboutNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From About WHERE aboutNo = :aboutNo";
        Query query = session.createQuery(hql);
        query.setParameter("aboutNo", aboutNo);
        try {
            About about = (About) query.getSingleResult();
            return about;
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    @Override
    public void addAbout(About about) {
        Session session = sessionFactory.getCurrentSession();
        session.save(about);
    }
    
    @Override
    public void editAbout(About about) {
        Session session = sessionFactory.getCurrentSession();
        About persistEntity = session.get(About.class, about.getAboutNo());
        if (persistEntity != null) {
            persistEntity.setAboutTxtAr(about.getAboutTxtAr());
            persistEntity.setAboutTxtEn(about.getAboutTxtEn());
            if (about.getOrderNo() >= 1) {
                persistEntity.setOrderNo(about.getOrderNo());
            }
            if ((about.getEnabled() != null) && about.getEnabled() == 0 || about.getEnabled() == 1) {
                persistEntity.setEnabled(about.getEnabled());
            }
            session.merge(persistEntity);
        }
    }
    
    @Override
    public int deleteAbout(Integer aboutNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM About WHERE aboutNo = :aboutNo";
        Query query = session.createQuery(hql);
        query.setParameter("aboutNo", aboutNo);
        return query.executeUpdate(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean checkIFAboutIsExists(Integer aboutNo) {
        boolean flag = false;
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COALESCE(COUNT (*),0) FROM About WHERE aboutNo = :aboutNo";
        Query query = session.createQuery(hql);
        query.setParameter("aboutNo", aboutNo);
        try {
            Long count = (Long) query.getSingleResult();
            System.out.println("check about = " + count);
            if (count != null && count > 0) {
                return true;
            }
        } catch (NoResultException ex) {
            return flag;
        }
        return flag; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<About> getEnabledAboutList() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From About WHERE enabled = :enabled ORDER BY orderNo , aboutNo";
        Query query = session.createQuery(hql);
        query.setParameter("enabled", 1);
        try {
            List<About> aboutList = query.getResultList();
            return aboutList;
        } catch (NoResultException ex) {
            return null;
        }
    }
    
}
