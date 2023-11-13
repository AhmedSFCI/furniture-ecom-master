/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.ColorDTO;
import com.furniture.ecom._dto.ProductColorDTO;
import com.furniture.ecom._entity.ProductColor;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author HP
 */
public interface ColorService {

    public Integer addColor(ColorDTO prodFinishDto);

    public Integer updateColor(ColorDTO prodFinishDto);

    public List<ColorDTO> getColorList(Integer langNo) throws IOException;

    public ColorDTO getColorByNo(Integer colorNo, Integer langNo) throws IOException;

    public Integer deleteColor(Integer colorNo);

    public boolean checkColorISExists(Integer colorNo);

    public Integer addProductColor(ProductColorDTO productColorDto);

    public Integer addProductColorList(List<ProductColorDTO> productColorDtoList);

    public Integer updateProductColor(ProductColorDTO productColorDto);

    public Integer deleteProductColorByColorNo(Integer colorNo);

    public Integer deleteProductColorByProdNo(Long prodNo);

    public Integer deleteProductColor(Long prodNo, Integer colorNo);
}
