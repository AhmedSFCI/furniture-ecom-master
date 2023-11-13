/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.ColorDTO;
import com.furniture.ecom._dto.ProductColorDTO;
import com.furniture.ecom._entity.Color;
import com.furniture.ecom._entity.ProductColor;
import java.util.List;
import java.util.Set;

/**
 *
 * @author HP
 */
public interface ColorDAO {

    public List<Color> getColorList();

    public Color getColorByNo(Integer colorNo);

    public Integer deleteColor(Integer colorNo);

    public Color addColor(Color color);

    public void updateColor(ColorDTO colorDto);

    public boolean checkColorISExists(Integer colorNo);

    public Set<Integer> getProductColors(Set<Long> productNoList, Integer langNo);

    public List<ProductColor> getProductsColorsListByProdNo(Set<Long> productNoList, Integer langNo);

    public List<ProductColorDTO> getProductColorByProdNo(Long productNo, Integer langNo);

    public void addProductColor(ProductColor productColor);

    public void addProductColorList(List<ProductColor> productColor);

    public void updateProductColor(ProductColorDTO productColor);

    public Integer deleteProductColorByColorNo(Integer colorNo);

    public Integer deleteProductColorByProdNo(Long prodNo);

    public Integer deleteProductColor(Long prodNo, Integer colorNo);
}
