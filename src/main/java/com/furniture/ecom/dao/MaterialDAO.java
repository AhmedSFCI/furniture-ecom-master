/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.MaterialDTO;
import com.furniture.ecom._dto.ProductMaterialsDTO;
import com.furniture.ecom._entity.Material;
import com.furniture.ecom._entity.ProductMaterials;
import java.util.List;
import java.util.Set;

/**
 *
 * @author HP
 */
public interface MaterialDAO {

    public List<Material> getMaterialList();

    public Material getMaterialByNo(Integer materialNo);

    public Integer deleteMaterial(Integer materialNo);

    public Material addMaterial(Material material);

    public void updateMaterial(MaterialDTO materialDto);

    public boolean checkMaterialISExists(Integer materialNo);
    
     public List<ProductMaterialsDTO> getProductMaterialsByProdNo(Long productNo, Integer langNo) ;
     
      public Set<Integer> getProductMaterials(Set<Long> productNoList, Integer langNo);

    public List<ProductMaterials> getProductsMaterialsListByProdNo(Set<Long> productNoList, Integer langNo);

    public void addProductMaterials(ProductMaterials productMaterial);

    public void addProductMaterialsList(List<ProductMaterials> productMaterial);

    public void updateProductMaterials(ProductMaterialsDTO productMaterial);

    public Integer deleteProductMaterialsByMaterialNo(Integer colorNo);

    public Integer deleteProductMaterialsByProdNo(Long prodNo);

    public Integer deleteProductMaterials(Long prodNo, Integer colorNo);
}
