/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.CartDTO;
import com.furniture.ecom._entity.Cart;
import com.furniture.ecom._entity.CartPK;
import com.furniture.ecom._model.ResponseMessages;
import com.furniture.ecom.dao.CartDAO;
import com.furniture.ecom.dao.GeneralDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author HP
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartDAO cartDao;
    @Autowired
    GeneralDAO gnrDao;

    @Override
    @Transactional
    public List<CartDTO> getCartProductList(Integer customerNo, Integer langNo) {
        return cartDao.getCartProductList(customerNo, langNo);
    }

    @Override
    @Transactional
    public List<CartDTO> getCustomerCartList(Integer customerNo) {
        List<Cart> carts = cartDao.getCustomerCartList(customerNo);
        List<CartDTO> cartDtoList = new ArrayList();
        if (carts != null && !carts.isEmpty()) {
            carts.stream().map(cart -> {
                CartDTO cartDto = new CartDTO();
                cartDto.setCustNo(cart.getCartPK().getCustNo());
                cartDto.setProdNo(cart.getCartPK().getProdNo());
                return cartDto;
            }).forEachOrdered(cartDto -> {
                cartDtoList.add(cartDto);
            });
        }
        return cartDtoList;
    }

    @Override
    @Transactional
    public Integer addCart(CartDTO cart) {
        Cart favrt = new Cart();
        favrt.setCartPK(new CartPK(cart.getCustNo(), cart.getProdNo()));
        favrt.setAddedDate(new Date());
        if (cartDao.checkCartIsExists(favrt)) {
            return ResponseMessages.RECORD_IS_EXISTS_CODE;
        }
        cartDao.addCart(favrt);
        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    @Transactional
    public Integer deleteCart(CartDTO cart) {
        Cart favrt = new Cart();
        favrt.setCartPK(new CartPK(cart.getCustNo(), cart.getProdNo()));
        if (!cartDao.checkCartIsExists(favrt)) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        cartDao.deleteCart(favrt);
        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    @Transactional
    public Integer deleteAllCustomerCart(Integer customerNo) {
        if (!gnrDao.checkIdIsExists("Cart", " cartPK.custNo ", customerNo,null)) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        cartDao.deleteAllCustomerCart(customerNo);
        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    public Integer updateCart(CartDTO cart) {
        Cart favrt = new Cart();
        favrt.setCartPK(new CartPK(cart.getCustNo(), cart.getProdNo()));
        favrt.setAddedDate(new Date());
        favrt.setQuantity(cart.getQuantity());
        if (!cartDao.checkCartIsExists(favrt)) {
            cartDao.addCart(favrt);
        } else {
            cartDao.updateCart(cart);
        }

        return ResponseMessages.SUCCESS_OPERATION;
    }

}
