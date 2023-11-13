/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.CategoryDTO;
import com.furniture.ecom._entity.Category;
import com.furniture.ecom._entity.Product;
import java.util.List;

/**
 *
 * @author HP
 */
public interface CategoryDAO {

    public List<Category> getCategoryList();
//
//    public List<Category> getMainCategoryList();
//
//    public List<Category> getSubCategoryList(Integer mainCatNo);
    
    public Category getCategoryByNo(Integer catNo);

    public void addCategory(Category category);

    public void editCategory(CategoryDTO category);

    public int deleteCategory(Integer catNo);

    public boolean checkIFCategoryHasProducts(Integer catNo);
    
     public boolean checkIFCategoryIsExists(Integer catNo);

    public int deleteCategoryProducts(Integer catNo);

    public List<Product> getCategoryProducts(Integer catNo);
    
}
