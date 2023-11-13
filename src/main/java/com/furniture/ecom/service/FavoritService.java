/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.FavoritDTO;
import com.furniture.ecom._dto.ProductDTO;
import com.furniture.ecom._entity.Favorit;
import java.util.List;

/**
 *
 * @author HP
 */
public interface FavoritService {

    public List<ProductDTO> getFavoritProductList(Integer customerNo, Integer langNo);

    public List<FavoritDTO> getCustomerFavoritList(Integer customerNo);

    public Integer addFavorit(FavoritDTO favorit);

    public Integer deleteFavorit(FavoritDTO favorit);

    public Integer deleteAllCustomerFavorit(Integer customerNo);
}
