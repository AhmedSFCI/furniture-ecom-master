/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.CartDTO;
import com.furniture.ecom._dto.FavoritDTO;
import com.furniture.ecom._dto.ProductDTO;
import java.util.List;

/**
 *
 * @author HP
 */
public interface CartService {

    public List<CartDTO> getCartProductList(Integer customerNo, Integer langNo);

    public List<CartDTO> getCustomerCartList(Integer customerNo);

    public Integer addCart(CartDTO cart);

    public Integer deleteCart(CartDTO cart);

    public Integer deleteAllCustomerCart(Integer customerNo);

    public Integer updateCart(CartDTO cart);
}
