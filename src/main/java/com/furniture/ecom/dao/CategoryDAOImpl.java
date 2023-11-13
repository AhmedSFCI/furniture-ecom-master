/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.CategoryDTO;
import com.furniture.ecom._entity.Category;
import com.furniture.ecom._entity.Product;
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
public class CategoryDAOImpl implements CategoryDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Category> getCategoryList() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Category";
        Query query = session.createQuery(hql);
        return query.getResultList();
    }

//    @Override
//    public List<Category> getMainCategoryList() {
//        Session session = sessionFactory.getCurrentSession();
//        String hql = "From Category WHERE mainCatNo IS NULL AND catNo IN (SELECT mainCatNo FROM Category WHERE mainCatNo IS NOT NULL)";
//        Query query = session.createQuery(hql);
//        return query.getResultList();
//    }
//
//    @Override
//    public List<Category> getSubCategoryList(Integer mainCatNo) {
//        Session session = sessionFactory.getCurrentSession();
//        String hql = "From Category WHERE mainCatNo =:catNo";
//        Query query = session.createQuery(hql);
//        query.setParameter("catNo", mainCatNo);
//        return query.getResultList();
//    }

    @Override
    public Category getCategoryByNo(Integer catNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Category WHERE catNo = :catNo";
        Query query = session.createQuery(hql);
        query.setParameter("catNo", catNo);
        try {
            Category category = (Category) query.getSingleResult();
            return category;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void addCategory(Category category) {
        Session session = sessionFactory.getCurrentSession();
        session.save(category);

    }

    @Override
    public void editCategory(CategoryDTO category) {
        Session session = sessionFactory.getCurrentSession();
        Category persistEntity = session.get(Category.class, category.getCatNo());
        if (persistEntity != null) {
            persistEntity.setCatDscEn(category.getCatDscEn());
            persistEntity.setCatDscAr(category.getCatDscAr());
            persistEntity.setCatNameAr(category.getCatNameAr());
            persistEntity.setCatNameEn(category.getCatNameEn());
            session.merge(persistEntity);
        }
    }

    @Override
    public int deleteCategory(Integer catNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM Category WHERE catNo = :catNo";
        Query query = session.createQuery(hql);
        query.setParameter("catNo", catNo);
        return query.executeUpdate();
    }

    @Override
    public boolean checkIFCategoryHasProducts(Integer catNo) {
        boolean flag = false;
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COALESCE(COUNT (*),0) FROM Product WHERE catNo = :catNo";
        Query query = session.createQuery(hql);
        query.setParameter("catNo", catNo);
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

    @Override
    public int deleteCategoryProducts(Integer catNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM Product WHERE catNo = :catNo";
        Query query = session.createQuery(hql);
        query.setParameter("catNo", catNo);
        return query.executeUpdate();
    }

    @Override
    public List<Product> getCategoryProducts(Integer catNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Product WHERE catNo = :catNo";
        Query query = session.createQuery(hql);
        query.setParameter("catNo", catNo);
        query.setMaxResults(20);
        return query.getResultList();
    }

    @Override
    public boolean checkIFCategoryIsExists(Integer catNo) {
        boolean flag = false;
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COALESCE(COUNT (*),0) FROM Category WHERE catNo = :catNo";
        Query query = session.createQuery(hql);
        query.setParameter("catNo", catNo);
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
