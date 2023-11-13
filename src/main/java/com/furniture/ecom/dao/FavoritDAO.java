/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.ProductDTO;
import com.furniture.ecom._entity.Favorit;
import com.furniture.ecom._entity.Product;
import java.util.List;

/**
 *
 * @author HP
 */
public interface FavoritDAO {

    public List<ProductDTO> getFavoritProductList(Integer customerNo, Integer langNo);

    public List<Favorit> getCustomerFavoritList(Integer customerNo);

    public void addFavorit(Favorit favorit);

    public void deleteFavorit(Favorit favorit);

    public void deleteAllCustomerFavorit(Integer customerNo);

    public boolean checkFavoritIsExists(Favorit favorit);

}
