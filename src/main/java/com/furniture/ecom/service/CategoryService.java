/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.CategoryDTO;
import com.furniture.ecom._dto.ProductDTO;
import java.util.List;

public interface CategoryService {

    public List<CategoryDTO> getCategoryDTOList(Integer langNo);

    public CategoryDTO getCategoryDTOByNo(Integer catNo,Integer langNo);

    public Integer addCategory(CategoryDTO categoryDto);

    public Integer updateCategory(CategoryDTO categoryDto);

    public Integer deleteCategory(Integer catNo);

    public boolean checkIFCategoryHasProducts(Integer catNo);
    
    public boolean checkIFCategoryIsExists(Integer catNo);

    public int deleteCategoryProducts(Integer catNo);

    public List<ProductDTO> getCategoryProducts(Integer catNo , Integer langNo);

}
