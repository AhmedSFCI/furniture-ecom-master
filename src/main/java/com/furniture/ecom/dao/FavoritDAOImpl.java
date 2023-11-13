/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.ProductDTO;
import com.furniture.ecom._entity.Favorit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
@Repository
public class FavoritDAOImpl implements FavoritDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    GeneralDAO generalDao;

    @Override
    public List<ProductDTO> getFavoritProductList(Integer customerNo, Integer langNo) {
        List<ProductDTO> products = new ArrayList();
        Session session = sessionFactory.getCurrentSession();
        String HQL = "SELECT favoritPK.prodNo FROM Favorit WHERE favoritPK.custNo = :customerNo";
        Query query = session.createQuery(HQL);
        query.setParameter("customerNo", customerNo);
        List<Object> objects = query.getResultList();

        if (objects != null && !objects.isEmpty()) {
            String whrQry = " prodNo IN (";
            for (Object obj : objects) {
                whrQry += (Integer) obj + " ,";
            }
            whrQry = whrQry.substring(0, whrQry.lastIndexOf(","));
            whrQry += " ) ";
            products = generalDao.getProductsInfo(null, null, whrQry, null, langNo);
        }
        return products;
    }

    @Override
    public List<Favorit> getCustomerFavoritList(Integer customerNo) {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "FROM Favorit WHERE favoritPK.custNo = :customerNo";
        Query query = session.createQuery(HQL);
        query.setParameter("customerNo", customerNo);
        List<Favorit> favorits = query.getResultList();
        if (favorits == null || favorits.isEmpty()) {
            return new ArrayList();
        }
        return favorits;
    }

    @Override
    public void addFavorit(Favorit favorit) {
        Session session = sessionFactory.getCurrentSession();
        session.save(favorit);
    }

    @Override
    public void deleteFavorit(Favorit favorit) {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "DELETE FROM Favorit WHERE favoritPK.custNo = :customerNo AND favoritPK.prodNo =  :prodNo";
        Query query = session.createQuery(HQL);
        query.setParameter("customerNo", favorit.getFavoritPK().getCustNo());
        query.setParameter("prodNo", favorit.getFavoritPK().getProdNo());
        query.executeUpdate();
    }

    @Override
    public void deleteAllCustomerFavorit(Integer customerNo) {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "DELETE FROM Favorit WHERE favoritPK.custNo = :customerNo";
        Query query = session.createQuery(HQL);
        query.setParameter("customerNo", customerNo);
        query.executeUpdate();
    }

    @Override
    public boolean checkFavoritIsExists(Favorit favorit) {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "SELECT 1 FROM Favorit WHERE favoritPK.custNo = :customerNo AND favoritPK.prodNo =  :prodNo";
        Query query = session.createQuery(HQL);
        query.setParameter("customerNo", favorit.getFavoritPK().getCustNo());
        query.setParameter("prodNo", favorit.getFavoritPK().getProdNo());
        try {
            List<Object> objs = query.getResultList();
            if (objs != null && objs.size() > 0) {
                return true;
            }
            return false;
        } catch (NoResultException ex) {
            return false;
        }
    }

}
