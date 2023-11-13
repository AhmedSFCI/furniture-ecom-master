/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.CartDTO;
import com.furniture.ecom._dto.ProductDTO;
import com.furniture.ecom._entity.Cart;
import java.util.List;

/**
 *
 * @author HP
 */
public interface CartDAO {
     public List<CartDTO> getCartProductList(Integer customerNo, Integer langNo);

    public List<Cart> getCustomerCartList(Integer customerNo);

    public void addCart(Cart cart);
    
    public void updateCart(CartDTO cart);

    public void deleteCart(Cart cart);

    public void deleteAllCustomerCart(Integer customerNo);

    public boolean checkCartIsExists(Cart cart);
}
