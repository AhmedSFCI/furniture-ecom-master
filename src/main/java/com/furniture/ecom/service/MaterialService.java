/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.MaterialDTO;
import com.furniture.ecom._dto.ProductMaterialsDTO;
import com.furniture.ecom._entity.ProductMaterials;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 *
 * @author HP
 */
public interface MaterialService {

    public Integer addMaterial(MaterialDTO prodMaterialDto);

    public Integer updateMaterial(MaterialDTO prodMaterialDto);

    public List<MaterialDTO> getMaterialList(Integer langNo) throws IOException;

    public MaterialDTO getMaterialByNo(Integer materialNo, Integer langNo) throws IOException;

    public Integer deleteMaterial(Integer materialNo);

    public boolean checkMaterialISExists(Integer materialNo);

//    public List<MaterialDTO> getProductMaterialsByProdNo(Long productNo, Integer langNo);
//
//    public Set<Integer> getProductMaterials(Set<Long> productNoList, Integer langNo);
//
//    public List<ProductMaterialsDTO> getProductsMaterialsListByProdNo(Set<Long> productNoList, Integer langNo);

    public Integer addProductMaterials(ProductMaterialsDTO productMaterial);

    public Integer addProductMaterialsList(List<ProductMaterialsDTO> productMaterialDtoList);

    public Integer updateProductMaterials(ProductMaterialsDTO productMaterial);

    public Integer deleteProductMaterialsByMaterialNo(Integer materialNo);

    public Integer deleteProductMaterialsByProdNo(Long prodNo);

    public Integer deleteProductMaterials(Long prodNo, Integer materialNo);
}
