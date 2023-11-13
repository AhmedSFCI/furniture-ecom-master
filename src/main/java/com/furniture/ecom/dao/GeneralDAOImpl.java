/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.ImageDTO;
import com.furniture.ecom._dto.ProductDTO;
import com.furniture.ecom._entity.Image;
import com.furniture.ecom._helpers.ImageAttributesConstacts;
import com.furniture.ecom._model.Pagging;
import com.furniture.ecom._util.ObjectChecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.NoResultException;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author HP
 */
@Repository
public class GeneralDAOImpl implements GeneralDAO {

    @Autowired
    ImageDAO imageDao;
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Map<Integer, String> getMessage(Set<Integer> msgNoList, Integer langNo) {
        Map<Integer, String> msgMap = new HashMap<Integer, String>();
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT messagePK.msgNo , msgText FROM Message WHERE messagePK.msgNo IN (:msgNo) AND messagePK.langNo = :langNo";
        Query query = session.createQuery(hql);
        query.setParameter("msgNo", msgNoList);
        query.setParameter("langNo", langNo);
        List<Object[]> objects = query.getResultList();
        if (objects != null) {
            for (Object[] obj : objects) {
                msgMap.put((Integer) obj[0], (String) obj[1]);
            }
        }
        return msgMap;
    }

    @Override
    public Map<String, String> getFieldText(Integer langNo, Set<String> fieldNameList) {

        Map<String, String> fielsMap = new HashMap<String, String>();
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT fieldPK.fieldname , fieldText FROM Field WHERE fieldPK.fieldname IN (:fieldname) AND fieldPK.langNo = :langNo";
        Query query = session.createQuery(hql);
        query.setParameter("fieldname", fieldNameList);
        query.setParameter("langNo", langNo);
        List<Object[]> objects = query.getResultList();
        if (objects != null) {
            for (Object[] obj : objects) {
                fielsMap.put((String) obj[0], (String) obj[1]);
            }
        }
        return fielsMap;
    }

    @Override
    public boolean checkUniquePrmtrBasedOnId(String tblNm, String colNm, String attrValue, String idNm, String idValue) {
        boolean flag = false;
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COUNT(*) FROM " + tblNm + " WHERE " + colNm + " = " + attrValue + " AND " + idNm + " = :idValue";
        Query query = session.createQuery(hql);
        query.setParameter("idValue", idValue);
        List<Object[]> objects = query.getResultList();
        if (objects != null || objects.size() > 0) {
            return true;
        }
        return flag;
    }

    @Override
    public boolean checkUniquePrmtr(String tblNm, String colNm, String attrValue) {
        boolean flag = false;
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COALESCE(COUNT (*),0) FROM " + tblNm + " WHERE " + colNm + " = " + ":attrValue";
        Query query = session.createQuery(hql);
        query.setParameter("attrValue", attrValue);
        Long count = null;
        try {
            count = (Long) query.getSingleResult();
            if (count == null || count <= 0) {
                return flag;
            }
            flag = true;
        } catch (NoResultException ex) {
            return flag;
        }
        return flag;
    }

    @Override
    public String getErrorMsg(Integer msgNo, Integer langNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT msgText FROM Message WHERE messagePK.msgNo = :msgNo AND messagePK.langNo = :langNo";
        Query query = session.createQuery(hql);
        query.setParameter("msgNo", msgNo);
        query.setParameter("langNo", langNo);
        try {
            String message = (String) query.getSingleResult();
            return message;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public String getFieldName(String field, Integer langNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT fieldText FROM Field WHERE fieldPK.fieldname = :fieldname AND fieldPK.langNo = :langNo";
        Query query = session.createQuery(hql);
        query.setParameter("fieldname", field);
        query.setParameter("langNo", langNo);
        try {
            String fieldName = (String) query.getSingleResult();
            return fieldName;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public boolean checkRecordIfExists(String tblNm, String colNm, String colVal, String idNm, Integer id) {
        boolean flag = false;
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COALESCE(COUNT (*),0) FROM " + tblNm + " WHERE " + colNm + " = '" + colVal + "' AND " + idNm + " != :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
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
    public Integer getNextOrderNo(String tblNm) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COALESCE(Max (orderNo),0) FROM " + tblNm;
        Query query = session.createQuery(hql);
        try {
            Integer max = (Integer) query.getSingleResult();
            if (max != null && max >= 1) {
                return (max + 1);
            }
        } catch (NoResultException ex) {
            return 1;
        }
        return 1;
    }

    @Override
    public Long getCountOfRecords(String tableName, String whrQry) {
        Session session = sessionFactory.getCurrentSession();
        if (ObjectChecker.isEmptyOrNull(whrQry)) {
            whrQry = "";
        }
        String hql = "SELECT COALESCE(COUNT (*),0) FROM " + tableName + " WHERE 1=1 ";
        Query query = session.createQuery(hql);
        Long count = null;
        try {
            count = (Long) query.getSingleResult();
            if (count == null && count <= 0) {
                return 0L;
            }
        } catch (NoResultException ex) {
            return 0L;
        }
        return count;
    }

    @Override
    public String getTextFromId(String tableNm, String colTxtNm, String colIdNm, Integer id, Integer langNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT (CASE WHEN " + langNo + " = 1 THEN " + colTxtNm + "Ar ELSE COALESCE(" + colTxtNm + "En," + colTxtNm + "Ar)END) FROM "
                + tableNm + " WHERE " + colIdNm + " = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        try {
            Object obj = query.getSingleResult();
            if (obj != null) {
                return (String) obj;
            }
            return null;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public <T> Map<T, String> getTextListFromId(String tableNm, String colTxtNm, String colIdNm, Set<T> idList, Integer langNo) {
        Map<T, String> fielsMap = new HashMap();
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT " + colIdNm + " , (CASE WHEN " + langNo + " = 1 THEN " + colTxtNm + "Ar ELSE COALESCE(" + colTxtNm + "En," + colTxtNm
                + "Ar)END) FROM " + tableNm + " WHERE " + colIdNm + " IN (:list)";
        Query query = session.createQuery(hql);
        query.setParameter("list", idList);
        List<Object[]> objects = query.getResultList();
        if (objects != null) {
            for (Object[] obj : objects) {
                fielsMap.put((T) obj[0], (String) obj[1]);
            }
        }
        return fielsMap;
    }

    @Override
    public boolean checkIdIsExists(String tblNm, String colNm, Integer idValue, String whrQry) {
        boolean flag = false;
        Session session = sessionFactory.getCurrentSession();
        if (whrQry == null) {
            whrQry = "";
        }
        String hql = "SELECT COALESCE(COUNT (*),0) FROM " + tblNm + " WHERE " + colNm + " = " + idValue + whrQry;

        Query query = session.createQuery(hql);
        //     query.setParameter("id", idValue);
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
    public <T> T getColumnValue(String tableName, String columnName, String PKName, T PKValue, String qryWhere) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT " + columnName + " FROM " + tableName + " WHERE " + PKName + " = :PKName";
        if (qryWhere != null && !qryWhere.isBlank()) {
            hql += "AND " + qryWhere;
        }
        Query query = session.createQuery(hql);
        query.setParameter("PKValue", PKValue);
        query.setMaxResults(1);
        try {
            return (T) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<ProductDTO> getProductsInfo(String fldNm, Integer fldVal, String whrQry, Integer numberOFReconds, Integer langNo) {
        List<ProductDTO> products = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT p.prodNo , p.prodNameAr , p.prodNameEn , p.prodPrice , c.catNameAr , c.catNameEn , p.catNo FROM Product p , Category c WHERE p.catNo = c.catNo ";
        if (ObjectChecker.isNotEmptyOrNull(fldNm)) {
            hql += "AND p." + fldNm + " = :fieldValue";
        }
        if (ObjectChecker.isNotEmptyOrNull(whrQry)) {
            hql += " AND " + whrQry;
        }
        Query query = session.createQuery(hql);
        if (ObjectChecker.isNotEmptyOrNull(fldNm)) {
            query.setParameter("fieldValue", fldVal);
        }
        if (ObjectChecker.isNotEmptyOrZero(numberOFReconds)) {
            query.setMaxResults(numberOFReconds);
        }
        List<Object[]> objects = query.getResultList();
        if (objects != null) {
            for (Object[] obj : objects) {
                ProductDTO product = new ProductDTO();
                product.setProdNo((Long) obj[0]);
                List<Image> images = imageDao.getSpecificNoOfImage(ImageAttributesConstacts.PRODUCT_IMAGE, ((Long) obj[0]).intValue(), 1);
                if (images != null && !images.isEmpty()) {
                    product.setImages(Arrays.asList(new ImageDTO(images.get(0).getImgNo(), images.get(0).getImgPath())));
                }
                String name = (langNo == 2) ? (String) obj[2] : (String) obj[1];
                product.setProdName(name);
                product.setProdPrice((Double) obj[3]);
                String catName = (langNo == 2) ? (String) obj[4] : (String) obj[5];
                product.setCatName(catName);
                product.setCatNo((Integer) obj[6]);
            }
        }
        return products;
    }

    @Override
    public List<ProductDTO> getMaterialProductsInfo(Integer fldVal, String whrQry, Integer langNo) {
        List<ProductDTO> products = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT p.prodNo , p.prodNameAr , p.prodNameEn , p.prodPrice , c.catNameAr , c.catNameEn , p.catNo FROM Product p , Category c , ProductMaterials m WHERE p.catNo = c.catNo AND m.prodMatrlNo = :material AND p.prodNo = m.prodNo";

        if (whrQry != null && !whrQry.isBlank()) {
            hql += " AND " + whrQry;
        }
        Query query = session.createQuery(hql);
        query.setParameter("material", fldVal);
        List<Object[]> objects = query.getResultList();
        if (objects != null) {
            for (Object[] obj : objects) {
                ProductDTO product = new ProductDTO();
                product.setProdNo((Long) obj[0]);
                List<Image> images = imageDao.getSpecificNoOfImage(ImageAttributesConstacts.PRODUCT_IMAGE, (Integer) obj[0], 1);
                if (images != null && !images.isEmpty()) {
                    product.setImages(Arrays.asList(new ImageDTO(images.get(0).getImgNo(), images.get(0).getImgPath())));
                }
                String name = (langNo == 2) ? (String) obj[2] : (String) obj[1];
                product.setProdName(name);
                product.setProdPrice((Double) obj[3]);
                String catName = (langNo == 2) ? (String) obj[4] : (String) obj[5];
                product.setCatName(catName);
                product.setCatNo((Integer) obj[6]);
            }
        }
        return products;
    }

    @Override
    public List<ProductDTO> getProductsPagingList(Pagging paging, Integer langNo) {
        List<ProductDTO> products = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        String hql
                = "SELECT p.prodNo , p.prodNameAr , p.prodNameEn , p.prodPrice , c.catNameAr , c.catNameEn , p.catNo FROM Product p  WHERE p." + paging
                        .getPagingType() + " = :fldVal ORDER BY p.prodNo";
        Query query = session.createQuery(hql);
        query.setParameter("fldVal", paging.getTypeValue());
        query.setFirstResult(paging.getItmPerPage() * (paging.getPageNo() - 1));
        query.setMaxResults(paging.getItmPerPage());
        List<Object[]> objects = query.getResultList();
        if (objects != null) {
            objects.forEach(obj -> {
                ProductDTO product = new ProductDTO();
                product.setProdNo((Long) obj[0]);
                List<Image> images = imageDao.getSpecificNoOfImage(ImageAttributesConstacts.PRODUCT_IMAGE, (Integer) obj[0], 1);
                if (images != null && !images.isEmpty()) {
                    product.setImages(Arrays.asList(new ImageDTO(images.get(0).getImgNo(), images.get(0).getImgPath())));
                }
                String name = (langNo == 2) ? (String) obj[2] : (String) obj[1];
                product.setProdName(name);
                product.setProdPrice((Double) obj[3]);
                String catName = (langNo == 2) ? (String) obj[4] : (String) obj[5];
                product.setCatName(catName);
                product.setCatNo((Integer) obj[6]);
            });
        }
        return products;
    }

    @Override
    public Object getAttributeValue(String tableName, String colNm, String pkNm, Integer pkVal) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT " + colNm + " FROM " + tableName + " WHERE " + pkNm + " = :PKName";
        Query query = session.createQuery(hql);
        query.setParameter("PKName", pkVal);
        query.setMaxResults(1);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public <T> List<T> checkIdListAreExist(String tableName, String columnName, Set<T> list, String whrQry) {
        if (whrQry == null) {
            whrQry = "";
        }
        List<T> data = new ArrayList();
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT " + columnName + " FROM " + tableName + " WHERE " + columnName + " IN (:list)";
        Query query = session.createQuery(hql);
        query.setParameter("list", list);
        try {
            List<Object> objects = query.getResultList();
            if (objects == null || objects.isEmpty()) {
                return data;
            }
            objects.forEach(obj -> data.add((T) obj));
            return data;
        } catch (NoResultException ex) {
            return data;
        }
    }

    @Override
    public Map<Integer, String> getCustomersNm(Set<Integer> list) {
        Map<Integer, String> data = new HashMap();
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT custNo , custName FROM Customer WHERE custNo IN (:list)";
        Query query = session.createQuery(hql);
        query.setParameter("list", list);
        try {
            List<Object[]> objects = query.getResultList();
            if (objects == null || objects.isEmpty()) {
                return data;
            }
            for (Object[] obj : objects) {
                data.put((Integer) obj[0], (String) obj[1]);
            }
            return data;
        } catch (NoResultException ex) {
            return data;
        }
    }

    @Override
    public Object getMaxValue(String tblNm, String colNm) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT MAX(COALESCE(" + colNm + ",0)) FROM " + tblNm;
        Query query = session.createQuery(hql);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return 0;
        }
    }

}
