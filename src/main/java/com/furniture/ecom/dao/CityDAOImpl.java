/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._entity.City;

import java.util.List;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Mostafa Atta
 */
@Repository
public class CityDAOImpl implements CityDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<City> getCityList() {
        Session currentSession = sessionFactory.getCurrentSession();
        String hql = "From City ORDER BY cityNo";
        Query query = currentSession.createQuery(hql);
        query.setMaxResults(10);
        return query.getResultList();

    }

    @Override
    public City getCityByNo(Integer cityNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From City WHERE cityNo = :cityNo";
        javax.persistence.Query query = session.createQuery(hql);
        query.setParameter("cityNo", cityNo);
        try {
            return (City) query.getSingleResult();
        } catch (NoResultException ex) {
            ex.getMessage();
            return null;
        }
    }

    @Override
    public void addCity(City city) {
        Session session = sessionFactory.getCurrentSession();
        session.save(city);

    }

    @Override
    public void editCity(City city) {
        Session session = sessionFactory.getCurrentSession();
        City persistEntity = session.get(City.class, city.getCityNo());
        if (persistEntity != null) {
            persistEntity.setCityNmAr(city.getCityNmAr());
            if (city.getCityNmEn() != null) {
                persistEntity.setCityNmEn(city.getCityNmEn());
            }
            persistEntity.setShipPrice(city.getShipPrice());
            session.merge(persistEntity);
        }
    }

    @Override
    public int deleteCity(Integer cityNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM City WHERE cityNo = :cityNo";
        javax.persistence.Query query = session.createQuery(hql);
        query.setParameter("cityNo", cityNo);
        return query.executeUpdate();
    }

    @Override
    public boolean checkIFCityIsExists(Integer cityNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COALESCE(COUNT (*),0) FROM City WHERE cityNo = :cityNo";
        javax.persistence.Query query = session.createQuery(hql);
        query.setParameter("cityNo", cityNo);
        try {
            Long count = (Long) query.getSingleResult();
            if (count == null) {
                return false;
            }
            if (count == 0) {
                return false;
            }
        } catch (NoResultException ex) {
            return false;
        }
        return true;
    }

}
