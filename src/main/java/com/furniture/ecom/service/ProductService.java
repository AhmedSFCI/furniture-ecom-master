/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.ProductDTO;
import com.furniture.ecom._dto.ProductFilterDTO;
import com.furniture.ecom._entity.Product;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author HP
 */
public interface ProductService {

    public List<ProductDTO> getProductList(ProductFilterDTO filter, Integer langNo) throws IOException;

    public ProductDTO getProductByNo(Long productNo, Integer langNo, String showTyp) throws IOException;

    public Integer deleteProduct(Long productNo);

    public Integer addProduct(ProductDTO productDto);

    public Long getProductFilterCount(ProductFilterDTO filter);
//
//    public Integer updateProduct(ProductDTO productDto);
//
//    public boolean checkProductISExists(Long productNo);
//
//    public List<ProductDTO> getMultiProductPageList(Pagging paging,Integer langNo)  throws IOException ;
    
      public Map<Long,Product> getProductListByNos(Set<Long> prodList);
}
