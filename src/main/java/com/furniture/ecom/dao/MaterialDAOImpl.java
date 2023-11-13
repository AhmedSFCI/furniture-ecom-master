/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.MaterialDTO;
import com.furniture.ecom._dto.ProductMaterialsDTO;
import com.furniture.ecom._dto.MaterialDTO;
import com.furniture.ecom._dto.ProductMaterialsDTO;
import com.furniture.ecom._entity.Material;
import com.furniture.ecom._entity.ProductMaterials;
import com.furniture.ecom._entity.ProductMaterials;
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
public class MaterialDAOImpl implements MaterialDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Material> getMaterialList() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Material ORDER BY materialNo";
        Query query = session.createQuery(hql);
        return query.getResultList();
    }

    @Override
    public Material getMaterialByNo(Integer materialNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Material WHERE materialNo = :materialNo";
        Query query = session.createQuery(hql);
        query.setParameter("materialNo", materialNo);
        try {
            Material material = (Material) query.getSingleResult();
            return material;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Integer deleteMaterial(Integer materialNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM Material WHERE materialNo = :materialNo";
        Query query = session.createQuery(hql);
        query.setParameter("materialNo", materialNo);
        return query.executeUpdate();
    }

    @Override
    public Material addMaterial(Material materialuct) {
        Session session = sessionFactory.getCurrentSession();
        session.save(materialuct);
        return materialuct;
    }

    @Override
    public void updateMaterial(MaterialDTO materialuctDto) {
        Session session = sessionFactory.getCurrentSession();
        Material persistEntity = session.get(Material.class, materialuctDto.getMaterialNo());
        if (persistEntity != null) {
            persistEntity.setMaterialNmAr(materialuctDto.getMaterialNmAr());
            if (materialuctDto.getMaterialNmEn() != null && !materialuctDto.getMaterialNmEn().isBlank()) {
                persistEntity.setMaterialNmEn(materialuctDto.getMaterialNmEn());
            }
            session.merge(persistEntity);
        }
    }

    @Override
    public boolean checkMaterialISExists(Integer materialNo) {
        boolean flag = false;
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COALESCE(COUNT (*),0) FROM Material WHERE materialNo = :materialNo";
        Query query = session.createQuery(hql);
        query.setParameter("materialNo", materialNo);
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
    public List<ProductMaterialsDTO> getProductMaterialsByProdNo(Long productNo, Integer langNo) {
        List<ProductMaterialsDTO> productMaterialList = new ArrayList();
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT m.materialNo ,"
                + " (SELECT (CASE WHEN " + langNo + " = 1 THEN materialNmAr ELSE COALESCE(materialNmEn, materialNmAr)END) FROM Material mt WHERE mt.materialNo = m.materialNo) as materialNm"
                + " FROM ProductMaterials m WHERE m.prodNo =:prodNo";
        Query query = session.createQuery(hql);
        query.setParameter("prodNo", productNo);
        List<Object[]> objects = query.getResultList();
        if (objects != null) {
            for (Object[] obj : objects) {
                ProductMaterialsDTO productMaterial = new ProductMaterialsDTO();
                productMaterial.setMaterialNo((Integer) obj[0]);
                productMaterial.setMaterialNm((String) obj[1]);
                productMaterialList.add(productMaterial);
            }
        }
        return productMaterialList;

    }

    @Override
    public Set<Integer> getProductMaterials(Set<Long> productNoList, Integer langNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT materialNo FROM ProductMaterials  WHERE prodNo IN (:list)";
        Query query = session.createQuery(hql);
        query.setParameter("list", productNoList);
        Set<Integer> colorNoList = new HashSet<>();
        try {
            List<Object> objs = query.getResultList();
            if (objs != null) {
                for (Object obj : objs) {
                    colorNoList.add((Integer) obj);
                }
            }
        } catch (NoResultException ex) {
            return colorNoList;
        }

        return colorNoList;
    }

    @Override
    public List<ProductMaterials> getProductsMaterialsListByProdNo(Set<Long> productNoList, Integer langNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM ProductMaterials  WHERE prodNo IN (:list)";
        Query query = session.createQuery(hql);
        query.setParameter("list", productNoList);
        try {
            return query.getResultList();

        } catch (NoResultException ex) {
            return new ArrayList();
        }
    }

    @Override
    public void addProductMaterials(ProductMaterials productMaterial) {
        Session session = sessionFactory.getCurrentSession();
        session.save(productMaterial);
    }

    @Override
    public void addProductMaterialsList(List<ProductMaterials> productMaterial) {
        Session session = sessionFactory.getCurrentSession();
        if (productMaterial != null) {
            productMaterial.forEach(material -> session.save(material));
        }
    }

    @Override
    public void updateProductMaterials(ProductMaterialsDTO productMaterial) {
        Session session = sessionFactory.getCurrentSession();
        ProductMaterials persistedEntity = session.get(ProductMaterials.class, productMaterial.getProdMatrlNo());
        if (persistedEntity != null) {
            persistedEntity.setMaterialNo(productMaterial.getMaterialNo());
        }
        session.save(productMaterial);
    }

    @Override
    public Integer deleteProductMaterialsByMaterialNo(Integer colorNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM ProductMaterials WHERE productMaterialPK.colorNo = :colorNo OR secondColorNo = :colorNo";
        Query query = session.createQuery(hql);
        query.setParameter("colorNo", colorNo);
        return query.executeUpdate();
    }

    @Override
    public Integer deleteProductMaterialsByProdNo(Long prodNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM ProductMaterials WHERE prodNo = :prodNo";
        Query query = session.createQuery(hql);
        query.setParameter("prodNo", prodNo);
        return query.executeUpdate();
    }

    @Override
    public Integer deleteProductMaterials(Long prodNo, Integer colorNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM ProductMaterials WHERE productMaterialPK.colorNo = :colorNo AND  prodNo = :prodNo";
        Query query = session.createQuery(hql);
        query.setParameter("colorNo", colorNo);
        query.setParameter("prodNo", prodNo);
        return query.executeUpdate();
    }
}
