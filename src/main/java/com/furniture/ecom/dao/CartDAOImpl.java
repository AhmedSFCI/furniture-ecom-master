/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.CartDTO;
import com.furniture.ecom._dto.ProductDTO;
import com.furniture.ecom._entity.Cart;
import com.furniture.ecom._entity.Cart;
import com.furniture.ecom._entity.Cart;
import com.furniture.ecom._entity.CartPK;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HP
 */
@Repository
public class CartDAOImpl implements CartDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    GeneralDAO generalDao;

    @Override
    public List<CartDTO> getCartProductList(Integer customerNo, Integer langNo) {
        List<ProductDTO> products = new ArrayList();
        List<CartDTO> carts = new ArrayList();
        Session session = sessionFactory.getCurrentSession();
        String HQL = "SELECT cartPK.prodNo , quantity FROM Cart WHERE cartPK.custNo = :customerNo";
        Query query = session.createQuery(HQL);
        query.setParameter("customerNo", customerNo);
        List<Object[]> objects = query.getResultList();

        if (objects != null && !objects.isEmpty()) {
            String whrQry = " prodNo IN (";
            for (Object[] obj : objects) {
                CartDTO cartDto = new CartDTO();
                cartDto.setCustNo(customerNo);
                cartDto.setProdNo((Integer) obj[0]);
                cartDto.setQuantity((Integer) obj[1]);
                carts.add(cartDto);
                whrQry += (Integer) obj[0] + " ,";
            }
            whrQry = whrQry.substring(0, whrQry.lastIndexOf(","));
            whrQry += " ) ";
            products = generalDao.getProductsInfo(null, null, whrQry,null, langNo);
            if (products != null && !products.isEmpty()) {
                CartDTO cartDto = new CartDTO();
                for (CartDTO cartDTo : carts) {
                    ProductDTO product = products.stream().filter(x -> x.getProdNo().equals(cartDTo.getProdNo())).findAny().get();
                    Double price = product.getProdPrice().doubleValue() * cartDTo.getQuantity();
                    cartDTo.setTotalPrice(price);
                    cartDTo.setProduct(product);
                }
            }
        }
        return carts;
    }

    @Override
    public List<Cart> getCustomerCartList(Integer customerNo) {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "FROM Cart WHERE cartPK.custNo = :customerNo";
        Query query = session.createQuery(HQL);
        query.setParameter("customerNo", customerNo);
        List<Cart> carts = query.getResultList();
        if (carts == null || carts.isEmpty()) {
            return new ArrayList();
        }
        return carts;
    }

    @Override
    public void addCart(Cart cart) {
        Session session = sessionFactory.getCurrentSession();
        session.save(cart);
    }

    @Override
    public void updateCart(CartDTO cart) {
        Session session = sessionFactory.getCurrentSession();
        Cart entity = session.get(Cart.class, new CartPK(cart.getCustNo(), cart.getProdNo()));
        if (entity != null) {
            entity.setQuantity(cart.getQuantity());
            session.merge(entity);
        }
    }

    @Override
    public void deleteCart(Cart cart) {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "DELETE FROM Cart WHERE cartPK.custNo = :customerNo AND cartPK.prodNo =  :prodNo";
        Query query = session.createQuery(HQL);
        query.setParameter("customerNo", cart.getCartPK().getCustNo());
        query.setParameter("prodNo", cart.getCartPK().getProdNo());
        query.executeUpdate();
    }

    @Override
    public void deleteAllCustomerCart(Integer customerNo) {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "DELETE FROM Cart WHERE cartPK.custNo = :customerNo";
        Query query = session.createQuery(HQL);
        query.setParameter("customerNo", customerNo);
        query.executeUpdate();
    }

    @Override
    public boolean checkCartIsExists(Cart cart) {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "SELECT 1 FROM Cart WHERE cartPK.custNo = :customerNo AND cartPK.prodNo =  :prodNo";
        Query query = session.createQuery(HQL);
        query.setParameter("customerNo", cart.getCartPK().getCustNo());
        query.setParameter("prodNo", cart.getCartPK().getProdNo());
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
