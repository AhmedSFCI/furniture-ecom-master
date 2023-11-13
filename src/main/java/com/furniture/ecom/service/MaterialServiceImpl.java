/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.MaterialDTO;
import com.furniture.ecom._dto.ProductMaterialsDTO;
import com.furniture.ecom._entity.Material;
import com.furniture.ecom._entity.ProductMaterials;
import com.furniture.ecom.dao.GeneralDAO;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom._model.ResponseMessages;
import com.furniture.ecom.dao.MaterialDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author HP
 */
@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    MaterialDAO productMaterialDao;
    @Autowired
    GeneralDAO generalDao;
    @Autowired
    ModelMapper mapper;
    @Autowired
    CommonHelper commonHelper;

    @Override
    @Transactional
    public Integer addMaterial(MaterialDTO prodMaterialDto) {
        if (prodMaterialDto.getMaterialNo() != null && prodMaterialDto.getMaterialNo() >= 1) {
            if (generalDao.checkIdIsExists("Material", "materialNo", prodMaterialDto.getMaterialNo(), null)) {
                return ResponseMessages.RECORD_IS_EXISTS_CODE;
            }
        }
        if (generalDao.checkUniquePrmtr("Material", "materialNmAr", prodMaterialDto.getMaterialNmAr())) {
            return ResponseMessages.DUPLICATE_RECORD_AR_CODE;
        }
        if (prodMaterialDto.getMaterialNmEn() != null && generalDao.checkUniquePrmtr("Material", "materialNmEn", prodMaterialDto.getMaterialNmEn())) {
            return ResponseMessages.DUPLICATE_RECORD_EN_CODE;
        }
        productMaterialDao.addMaterial(mapper.map(prodMaterialDto, Material.class));
        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    @Transactional
    public Integer updateMaterial(MaterialDTO prodMaterialDto) {
        if (prodMaterialDto.getMaterialNo() != null && prodMaterialDto.getMaterialNo() >= 1) {
            if (!generalDao.checkIdIsExists("Material", "materialNo", prodMaterialDto.getMaterialNo(), null)) {
                return ResponseMessages.NOT_FOUND_RECORD_CODE;
            }
        }
        if (generalDao.checkRecordIfExists("Material", "materialNmAr", prodMaterialDto.getMaterialNmAr(), "materialNo", prodMaterialDto.getMaterialNo())) {
            return ResponseMessages.DUPLICATE_RECORD_AR_CODE;
        }
        if (prodMaterialDto.getMaterialNmEn() != null && generalDao.checkRecordIfExists("Material", "materialNmEn", prodMaterialDto.getMaterialNmAr(), "materialNo", prodMaterialDto.getMaterialNo())) {
            return ResponseMessages.DUPLICATE_RECORD_EN_CODE;
        }
        productMaterialDao.updateMaterial(prodMaterialDto);
        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    @Transactional
    public List<MaterialDTO> getMaterialList(Integer langNo) throws IOException {
        List<Material> materialList = productMaterialDao.getMaterialList();
        List<MaterialDTO> materialDtoList = new ArrayList<>();
        if (materialList == null) {
            return null;
        }
        for (Material material : materialList) {
            MaterialDTO productMaterialDTO = mapper.map(material, MaterialDTO.class);
            productMaterialDTO.setMaterialNm(commonHelper.getNameBasedOnLangNo(material.getMaterialNmAr(), material.getMaterialNmEn(), langNo));
            materialDtoList.add(productMaterialDTO);
        }
        return materialDtoList;
    }

    @Override
    @Transactional
    public MaterialDTO getMaterialByNo(Integer materialNo, Integer langNo) throws IOException {
        Material productMaterial = productMaterialDao.getMaterialByNo(materialNo);
        if (productMaterial == null) {
            return null;
        }
        MaterialDTO productMaterialDTO = mapper.map(productMaterial, MaterialDTO.class);
        productMaterialDTO.setMaterialNm(commonHelper.getNameBasedOnLangNo(productMaterial.getMaterialNmAr(), productMaterial.getMaterialNmEn(), langNo));
        productMaterialDTO.setProducts(generalDao.getMaterialProductsInfo(materialNo, null, langNo));
        return productMaterialDTO;
    }

    @Override
    @Transactional
    public Integer deleteMaterial(Integer materialNo) {
        Material productMaterial = productMaterialDao.getMaterialByNo(materialNo);
        if (productMaterial == null) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        if (generalDao.checkIdIsExists("ProductMaterials", "prodMatrlNo", materialNo, null)) {
            return ResponseMessages.RELATED_DATA_FIELDS;
        }
        return productMaterialDao.deleteMaterial(materialNo);
    }

    @Override
    @Transactional
    public boolean checkMaterialISExists(Integer materialNo) {
        return generalDao.checkIdIsExists("Material", "materialNo", materialNo, null);
    }

    @Override
    @Transactional
    public Integer addProductMaterials(ProductMaterialsDTO productMaterial) {
        if (generalDao.checkIdIsExists("ProductMaterials", "materialNo", productMaterial.getMaterialNo(), " AND prodNo = " + productMaterial.getProdNo())) {
            return ResponseMessages.RECORD_IS_EXISTS_CODE;
        }
        ProductMaterials prodMaterial = mapper.map(productMaterial, ProductMaterials.class);
        productMaterialDao.addProductMaterials(prodMaterial);
        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    @Transactional
    public Integer addProductMaterialsList(List<ProductMaterialsDTO> productMaterialDtoList) {
        List<ProductMaterials> productMaterialList = new ArrayList();
        for (ProductMaterialsDTO productMaterialDto : productMaterialDtoList) {
            if (generalDao.checkIdIsExists("ProductMaterials", "materialNo", productMaterialDto.getMaterialNo(), " AND prodNo = " + productMaterialDto.getProdNo())) {
                return ResponseMessages.RECORD_IS_EXISTS_CODE;
            }
            ProductMaterials prodMaterial = mapper.map(productMaterialDto, ProductMaterials.class);
            productMaterialList.add(prodMaterial);
        }
        productMaterialDao.addProductMaterialsList(productMaterialList);
        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    @Transactional
    public Integer updateProductMaterials(ProductMaterialsDTO productMaterial) {
        if (!generalDao.checkIdIsExists("ProductMaterials", "materialNo", productMaterial.getMaterialNo(), " AND prodNo = " + productMaterial.getProdNo())) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        productMaterialDao.updateProductMaterials(productMaterial);
        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    @Transactional
    public Integer deleteProductMaterialsByMaterialNo(Integer materialNo) {
        return productMaterialDao.deleteProductMaterialsByMaterialNo(materialNo);
    }

    @Override
    @Transactional
    public Integer deleteProductMaterialsByProdNo(Long prodNo) {
        return productMaterialDao.deleteProductMaterialsByProdNo(prodNo);
    }

    @Override
    @Transactional
    public Integer deleteProductMaterials(Long prodNo, Integer materialNo) {
        return productMaterialDao.deleteProductMaterials(prodNo, materialNo);
    }
}
