/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.ProductDTO;
import com.furniture.ecom._dto.ProductFilterDTO;
import com.furniture.ecom._entity.Product;
import com.furniture.ecom._model.Pagging;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author HP
 */
public interface ProductDAO {

    public List<Product> getProductList(ProductFilterDTO filter);

    public Product getProductByNo(Long productNo);

    public Integer deleteProduct(Long productNo);

    public Product addProduct(Product product);

    public void updateProduct(ProductDTO productDto);

    public boolean checkProductISExists(Long productNo);

    public List<Product> getMultiProductPageList(Pagging paging);

    public void updateProductViewCount(Long prodNo);

    public Long getProductFilterCount(ProductFilterDTO filter);
     public Map<Long,Product> getProductListByNos(Set<Long> prodList);
    
}
