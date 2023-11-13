/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.ImageDTO;
import com.furniture.ecom._entity.Image;
import java.util.ArrayList;
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
public class ImageDAOImpl implements ImageDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Image> getImages(String imgType, Integer itemNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Image WHERE imgType = :imgTyp AND itemNo = :item";
        Query query = session.createQuery(hql);
        query.setParameter("imgTyp", imgType);
        query.setParameter("item", itemNo);
        List<Image> images = query.getResultList();
        
        if (images != null && images.size() > 0) {
            return images;
        }
        return new ArrayList();
    }

    @Override
    public void addImages(List<Image> imageList) {
        Session session = sessionFactory.getCurrentSession();
        for (Image image : imageList) {
            session.save(image);
        }
    }

    @Override
    public Integer deleteImage(Integer imageNo) {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "DELETE FROM Image WHERE imgNo = :imgNo";
        Query query = session.createQuery(HQL);
        query.setParameter("imgNo", imageNo);
        return query.executeUpdate();
    }

    @Override
    public boolean checkImageIsExists(Integer imageNo) {
        Session session = sessionFactory.getCurrentSession();
        Image image = session.get(Image.class, imageNo);
        if (image == null) {
            return false;
        }
        return true;
    }

    @Override
    public Integer updateImage(ImageDTO image) {
        Session session = sessionFactory.getCurrentSession();
        Image persistEntity = session.get(Image.class, image.getImgNo());
        if (persistEntity != null) {
            persistEntity.setImgPath(image.getImgPath());
            session.merge(persistEntity);
            return 1;
        }
        return 0;
    }

    @Override
    public List<Image> getSpecificNoOfImage(String imgType, Integer itemNo, Integer numberOfImg) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Image WHERE imgType = :imgTyp AND itemNo = :item";
        Query query = session.createQuery(hql);
        query.setParameter("imgTyp", imgType);
        query.setParameter("item", itemNo);
        query.setMaxResults(numberOfImg);
        List<Image> images = query.getResultList();
        if (images != null && images.size() > 0) {
            return images;
        }
        return new ArrayList();
    }

}
