/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.ColorDTO;
import com.furniture.ecom._dto.ProductColorDTO;
import com.furniture.ecom._entity.ProductColor;
import com.furniture.ecom._entity.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class ColorDAOImpl implements ColorDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Color> getColorList() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Color ORDER BY colorNo";
        Query query = session.createQuery(hql);
        return query.getResultList();
    }

    @Override
    public Color getColorByNo(Integer colorNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Color WHERE colorNo = :colorNo";
        Query query = session.createQuery(hql);
        query.setParameter("colorNo", colorNo);
        try {
            Color color = (Color) query.getSingleResult();
            return color;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Integer deleteColor(Integer colorNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM Color WHERE colorNo = :colorNo";
        Query query = session.createQuery(hql);
        query.setParameter("colorNo", colorNo);
        return query.executeUpdate();
    }

    @Override
    public Color addColor(Color color) {
        Session session = sessionFactory.getCurrentSession();
        session.save(color);
        return color;
    }

    @Override
    public void updateColor(ColorDTO colorDto) {
        Session session = sessionFactory.getCurrentSession();
        Color persistEntity = session.get(Color.class, colorDto.getColorNo());
        if (persistEntity != null) {
            persistEntity.setColorNmAr(colorDto.getColorNmAr());
            persistEntity.setColorCode(colorDto.getColorCode());
            if (colorDto.getColorNmEn() != null && !colorDto.getColorNmEn().isBlank()) {
                persistEntity.setColorNmEn(colorDto.getColorNmEn());
            }
            session.merge(persistEntity);
        }
    }

    @Override
    public boolean checkColorISExists(Integer colorNo) {
        boolean flag = false;
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COALESCE(COUNT (*),0) FROM Color WHERE colorNo = :colorNo";
        Query query = session.createQuery(hql);
        query.setParameter("colorNo", colorNo);
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
    public Integer deleteProductColorByColorNo(Integer colorNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM ProductColor WHERE productColorPK.colorNo = :colorNo OR secondColorNo = :colorNo";
        Query query = session.createQuery(hql);
        query.setParameter("colorNo", colorNo);
        return query.executeUpdate();
    }

    @Override
    public Set<Integer> getProductColors(Set<Long> productNoList, Integer langNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT productColorPK.colorNo , secondColorNo FROM ProductColor  WHERE productColorPK.prodNo IN (:list)";
        Query query = session.createQuery(hql);
        query.setParameter("list", productNoList);
        Set<Integer> colorNoList = new HashSet<>();
        try {
            List<Object[]> objs = query.getResultList();
            if (objs != null) {
                for (Object obj[] : objs) {
                    colorNoList.add((Integer) obj[0]);
                    colorNoList.add((Integer) obj[1]);
                }
            }

            return colorNoList;
        } catch (NoResultException ex) {
            return colorNoList;
        }
    }

    @Override
    public List<ProductColor> getProductsColorsListByProdNo(Set<Long> productNoList, Integer langNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM ProductColor  WHERE productColorPK.prodNo IN (:list)";
        Query query = session.createQuery(hql);
        query.setParameter("list", productNoList);
        try {
            return query.getResultList();

        } catch (NoResultException ex) {
            return new ArrayList();
        }
    }

    @Override
    public List<ProductColorDTO> getProductColorByProdNo(Long productNo, Integer langNo) {
        List<ProductColorDTO> productColorList = new ArrayList();
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT pc.productColorPK.colorNo ,pc.secondColorNo , pc.colorImg ,"
                + " (SELECT (CASE WHEN " + langNo + " = 1 THEN colorNmAr ELSE COALESCE(colorNmEn, colorNmAr)END) FROM Color c WHERE c.colorNo = pc.productColorPK.colorNo) as colorNm , "
                + " (SELECT (CASE WHEN " + langNo + " = 1 THEN colorNmAr ELSE COALESCE(colorNmEn, colorNmAr)END) FROM Color c WHERE pc.secondColorNo IS NOT NULL AND c.colorNo = pc.secondColorNo) as secColorNm "
                + " FROM ProductColor  pc WHERE productColorPK.prodNo =:prodNo";
        Query query = session.createQuery(hql);
        query.setParameter("prodNo", productNo);
        List<Object[]> objects = query.getResultList();
        if (objects != null) {
            for (Object[] obj : objects) {
                ProductColorDTO productColor = new ProductColorDTO();
                productColor.setColorNo((Integer) obj[0]);
                if (obj[1] != null) {
                    productColor.setSecondColorNo((Integer) obj[1]);
                    productColor.setSecondColorNm((String) obj[4]);
                }
                if (obj[2] != null) {
                    productColor.setColorImg((String) obj[2]);
                }
                productColor.setColorNm((String) obj[3]);
                productColorList.add(productColor);
            }
        }
        return productColorList;

    }

    @Override
    public void addProductColor(ProductColor productColor) {
        Session session = sessionFactory.getCurrentSession();
        session.save(productColor);
    }

    @Override
    public void addProductColorList(List<ProductColor> productColor) {
        Session session = sessionFactory.getCurrentSession();
        if(productColor!=null){
            productColor.forEach(color->session.save(color));
        }

    }

    @Override
    public void updateProductColor(ProductColorDTO productColor) {
         Session session = sessionFactory.getCurrentSession();
         ProductColor persistedEntity = session.get(ProductColor.class, productColor);
         if(persistedEntity!=null){
             persistedEntity.setColorImg(productColor.getColorImg());
             persistedEntity.setSecondColorNo(productColor.getSecondColorNo());
         }
        session.save(productColor);
    }
    
    @Override
    public Integer deleteProductColorByProdNo(Long prodNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM ProductColor WHERE productColorPK.prodNo = :prodNo";
        Query query = session.createQuery(hql);
        query.setParameter("prodNo", prodNo);
        return query.executeUpdate();
    }

    @Override
    public Integer deleteProductColor(Long prodNo, Integer colorNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM ProductColor WHERE productColorPK.colorNo = :colorNo AND  productColorPK.prodNo = :prodNo";
        Query query = session.createQuery(hql);
        query.setParameter("colorNo", colorNo);
        query.setParameter("prodNo", prodNo);
        return query.executeUpdate();
    }

}
