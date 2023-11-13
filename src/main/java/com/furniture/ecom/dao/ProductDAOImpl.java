/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.ProductDTO;
import com.furniture.ecom._dto.ProductFilterDTO;
import com.furniture.ecom._entity.Product;
import com.furniture.ecom._model.Pagging;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HP
 */
@Repository
public class ProductDAOImpl implements ProductDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Product> getProductList(ProductFilterDTO filter) {
        boolean catFlg = false;
        boolean styleFlg = false;
        boolean materialFlg = false;
        boolean colorFlg = false;
        boolean finishFlg = false;
        Integer pageNo = null;
        Integer itemPerPAge = null;
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Product WHERE 1=1";
        if (filter != null) {
            if (filter.getAvailablility() != null && (filter.getAvailablility().equals(0) || filter.getAvailablility().equals(1))) {
                hql += " AND prodAvailability = " + filter.getAvailablility();
            }
            if (filter.getCategoryList() != null && filter.getCategoryList().size() > 0) {
                if (!(filter.getCategoryList().size() == 1 && filter.getCategoryList().iterator().next() == null)) {
                    hql += " AND catNo IN (:catList)";
                    catFlg = true;
                }
            }
            if ((filter.getStyleList() != null && filter.getStyleList().size() > 0)) {
                if (!(filter.getStyleList().size() == 1 && filter.getStyleList().iterator().next() == null)) {
                    hql += " AND styleNo IN (:styleList)";
                    styleFlg = true;
                }
            }
            if (filter.getFinishList() != null && filter.getFinishList().size() > 0) {
                if (!(filter.getFinishList().size() == 1 && filter.getFinishList().iterator().next() == null)) {
                    hql += " AND finishNo IN (:finishList)";
                    finishFlg = true;
                }
            }
            if (filter.getMaterialList() != null && filter.getMaterialList().size() > 0) {
                if (filter.getMaterialList().size() == 1 && filter.getMaterialList().iterator().next() != null) {
                    hql += " AND prodNo IN (SELECT prodNo FROM ProductMaterials WHERE materialNo IN (:materialList)";
                    materialFlg = true;
                }
            }
            if (filter.getColorList() != null && filter.getColorList().size() > 0) {
                if (!(filter.getColorList().size() == 1 && filter.getColorList().iterator().next() == null)) {
                    hql += " AND prodNo IN ( SELECT productColorPK.prodNo FROM ProductColor WHERE productColorPK.colorNo IN (:colorList))";
                    colorFlg = true;
                }
            }
            if ((filter.getMaxPrice() != null && filter.getMaxPrice() > 0) && (filter.getMinPrice() != null && filter.getMinPrice() > 0)) {
                hql += " AND prodPrice BETWEEN " + filter.getMinPrice() + " AND " + filter.getMaxPrice();
            } else if ((filter.getMaxPrice() != null && filter.getMaxPrice() > 0) && (filter.getMinPrice() == null || filter.getMinPrice() <= 0)) {
                hql += " AND prodPrice <= " + filter.getMaxPrice();
            } else if ((filter.getMinPrice() != null && filter.getMinPrice() > 0) && (filter.getMaxPrice() == null || filter.getMaxPrice() <= 0)) {
                hql += " AND prodPrice >= " + filter.getMinPrice();
            }
            if (filter.getItemPerPage() != null && filter.getItemPerPage() > 0) {
                itemPerPAge = filter.getItemPerPage();
            }
            if (filter.getPageNo() != null && filter.getPageNo() > 0) {
                pageNo = filter.getPageNo();
            }
        }

        hql += " ORDER BY prodNo";
        System.out.println("HQL : "+hql);
        Query query = session.createQuery(hql);
        
        if (catFlg) {
            query.setParameter("catList", filter.getCategoryList());
        }
        if (styleFlg) {
            query.setParameter("styleList", filter.getStyleList());
        }
        if (materialFlg) {
            query.setParameter("materialList", filter.getMaterialList());
        }
        if (colorFlg) {
            query.setParameter("colorList", filter.getColorList());
        }
        if (finishFlg) {
            query.setParameter("finishList", filter.getFinishList());
        }

        if (itemPerPAge == null && pageNo == null) {
            query.setMaxResults(20);
        } else if (itemPerPAge != null && pageNo == null) {
            query.setMaxResults(itemPerPAge);
        } else if (itemPerPAge == null && pageNo != null) {
            query.setFirstResult((pageNo - 1) * 20);
            query.setMaxResults(20);
        } else {
            query.setFirstResult((pageNo - 1) * itemPerPAge);
            query.setMaxResults(20);
        }
        List<Product> products = query.getResultList();
        return products;
    }

    @Override
    public Product getProductByNo(Long productNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Product WHERE prodNo = :prodNo";
        Query query = session.createQuery(hql);
        query.setParameter("prodNo", productNo);
        try {
            Product product = (Product) query.getSingleResult();
            return product;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Integer deleteProduct(Long productNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM Product WHERE prodNo = :prodNo";
        Query query = session.createQuery(hql);
        query.setParameter("prodNo", productNo);
        return query.executeUpdate();
    }

    @Override
    public Product addProduct(Product product
    ) {
        Session session = sessionFactory.getCurrentSession();
        session.save(product);
        return product;
    }

    @Override
    public void updateProduct(ProductDTO productDto) {
        Session session = sessionFactory.getCurrentSession();
        Product persistEntity = session.get(Product.class, productDto.getProdNo());
        if (persistEntity != null) {
            persistEntity.setProdDscEn(productDto.getProdDscEn());
            persistEntity.setProdDscAr(productDto.getProdDscAr());
            persistEntity.setProdNameAr(productDto.getProdNameAr());
            persistEntity.setProdNameEn(productDto.getProdNameEn());
            persistEntity.setDimDepth(productDto.getDimDepth());
            persistEntity.setDimWidth(productDto.getDimWidth());
            persistEntity.setDimHight(productDto.getDimHight());
            persistEntity.setProdAvailability(productDto.getProdAvailability());
            persistEntity.setFinishNo(productDto.getFinishNo());
            persistEntity.setProdPrice(productDto.getProdPrice());
            persistEntity.setStyleNo(productDto.getStyleNo());
            persistEntity.setProdQuantity(productDto.getProdQuantity());
            persistEntity.setWarranty(productDto.getWarranty());
        //    persistEntity.setTaxNo(productDto.getTaxNo());
            persistEntity.setDiscountNo(productDto.getDiscountNo());
            session.merge(persistEntity);
        }
    }

    @Override
    public boolean checkProductISExists(Long productNo) {
        boolean flag = false;
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COALESCE(COUNT (*),0) FROM Product WHERE prodNo = :prodNo";
        Query query = session.createQuery(hql);
        query.setParameter("prodNo", productNo);
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
    public List<Product> getMultiProductPageList(Pagging paging) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Product ORDER BY prodNo";
        Query query = session.createQuery(hql);
        query.setFirstResult(paging.getItmPerPage() * (paging.getPageNo() - 1));
        query.setMaxResults(paging.getItmPerPage());
        List<Product> productList = query.getResultList();
        return productList;
    }

    @Override
    public void updateProductViewCount(Long ProdNo) {
        Session session = sessionFactory.getCurrentSession();
        Product persistEntity = session.get(Product.class, ProdNo);
        if (persistEntity != null) {
            persistEntity.setViewsCount(persistEntity.getViewsCount() + 1);
            session.merge(persistEntity);
        }

    }

    @Override
    public Long getProductFilterCount(ProductFilterDTO filter) {
        boolean catFlg = false;
        boolean styleFlg = false;
        boolean materialFlg = false;
        boolean colorFlg = false;
        boolean finishFlg = false;
        try {
            Session session = sessionFactory.getCurrentSession();
            String hql = "SELECT COUNT(*) FROM Product WHERE 1=1";
            if (filter != null) {
                if (filter.getAvailablility() != null && (filter.getAvailablility().equals(0) || filter.getAvailablility().equals(1))) {
                    hql += " AND prodAvailability = " + filter.getAvailablility();
                }
                if (filter.getCategoryList() != null && filter.getCategoryList().size() > 0) {
                    if (!(filter.getCategoryList().size() == 1 && filter.getCategoryList().iterator().next() == null)) {
                        hql += " AND catNo IN (:catList)";
                        catFlg = true;
                    }
                }
                if ((filter.getStyleList() != null && filter.getStyleList().size() > 0)) {
                    if (!(filter.getStyleList().size() == 1 && filter.getStyleList().iterator().next() == null)) {
                        hql += " AND styleNo IN (:styleList)";
                        styleFlg = true;
                    }
                }
                if (filter.getFinishList() != null && filter.getFinishList().size() > 0) {
                    if (!(filter.getFinishList().size() == 1 && filter.getFinishList().iterator().next() == null)) {
                        hql += " AND finishNo IN (:finishList)";
                        finishFlg = true;
                    }
                }
                if (filter.getMaterialList() != null && filter.getMaterialList().size() > 0) {
                    if (filter.getMaterialList().size() == 1 && filter.getMaterialList().iterator().next() != null) {
                        hql += " AND prodNo IN (SELECT prodNo FROM ProductMaterials WHERE materialNo IN (:materialList)";
                        materialFlg = true;
                    }
                }
                if (filter.getColorList() != null && filter.getColorList().size() > 0) {
                    if (!(filter.getColorList().size() == 1 && filter.getColorList().iterator().next() == null)) {
                        hql += " AND prodNo IN ( SELECT productColorPK.prodNo FROM ProductColor WHERE productColorPK.colorNo IN (:colorList))";
                        colorFlg = true;
                    }
                }
                if ((filter.getMaxPrice() != null && filter.getMaxPrice() > 0) && (filter.getMinPrice() != null && filter.getMinPrice() > 0)) {
                    hql += " AND prodPrice BETWEEN " + filter.getMinPrice() + " AND " + filter.getMaxPrice();
                } else if ((filter.getMaxPrice() != null && filter.getMaxPrice() > 0) && (filter.getMinPrice() == null || filter.getMinPrice() <= 0)) {
                    hql += " AND prodPrice <= " + filter.getMaxPrice();
                } else if ((filter.getMinPrice() != null && filter.getMinPrice() > 0) && (filter.getMaxPrice() == null || filter.getMaxPrice() <= 0)) {
                    hql += " AND prodPrice >= " + filter.getMinPrice();
                }
            }

            Query query = session.createQuery(hql);
            if (catFlg) {
                query.setParameter("catList", filter.getCategoryList());
            }
            if (styleFlg) {
                query.setParameter("styleList", filter.getStyleList());
            }
            if (materialFlg) {
                query.setParameter("materialList", filter.getMaterialList());
            }
            if (colorFlg) {
                query.setParameter("colorList", filter.getColorList());
            }
            if (finishFlg) {
                query.setParameter("finishList", filter.getFinishList());
            }
            Object object = query.getSingleResult();
            if (object == null) {
                return 0L;
            } else {
                return (Long) object;
            }
        } catch (NoResultException ex) {
            return 0L;
        }

    }
    

    @Override
    public Map<Long,Product> getProductListByNos(Set<Long> prodList){
        Session session = sessionFactory.getCurrentSession();
        Map<Long,Product> data = new HashMap();
        Query query = session.createQuery("FROM Product WHERE prodNo IN (:list)");
        query.setParameter("list",prodList);
        List<Product> products = query.getResultList();
        if(products!=null){
           for(Product product :products){
               data.put(product.getProdNo(), product);
           }
        }
        return data;
    }
}
