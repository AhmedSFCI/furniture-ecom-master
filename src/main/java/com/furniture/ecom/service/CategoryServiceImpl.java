/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.CategoryDTO;
import com.furniture.ecom._dto.ProductDTO;
import com.furniture.ecom._entity.Category;
import com.furniture.ecom._entity.Product;
import com.furniture.ecom.dao.CategoryDAO;
import com.furniture.ecom._dto.ImageDTO;
import com.furniture.ecom._entity.Image;
import com.furniture.ecom.dao.GeneralDAO;
import com.furniture.ecom.dao.ImageDAO;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom._helpers.CommonValidationHelper;
import com.furniture.ecom._helpers.ImageAttributesConstacts;
import com.furniture.ecom._model.ResponseMessages;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mosta
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDAO categoryDao;
    @Autowired
    ModelMapper mapper;
    @Autowired
    GeneralDAO generalDAO;
    @Autowired
    CommonHelper commonHelper;
    @Autowired
    CommonValidationHelper commonValHelper;
    @Autowired
    ImageDAO imageDao;
    
    @Autowired
    ImageService imageService;

    @Override
    @Transactional
    public List<CategoryDTO> getCategoryDTOList(Integer langNo) {
        List<Category> allCategories = categoryDao.getCategoryList();
        if (allCategories.isEmpty()) {
            return new ArrayList<>();
        }
        List<CategoryDTO> catgoriesDTOList = new ArrayList<>();
        for (Category category : allCategories) {
            CategoryDTO categoryDto = mapper.map(category, CategoryDTO.class);
            categoryDto.setCatDsc(commonHelper.getNameBasedOnLangNo(categoryDto.getCatDscAr(), categoryDto.getCatDscEn(), langNo));
            categoryDto.setCategoryName(commonHelper.getNameBasedOnLangNo(categoryDto.getCatNameAr(), categoryDto.getCatNameEn(), langNo));
            categoryDto.setNoOfProducts(generalDAO.getCountOfRecords("Product", " AND catNo = " + categoryDto.getCatNo()));
            categoryDto.setImages(imageService.getSpecificNoOfImage(ImageAttributesConstacts.CATEGORY_IMAGE, categoryDto.getCatNo(), 1));

            catgoriesDTOList.add(categoryDto);

        }
        return catgoriesDTOList;
    }

    @Override
    @Transactional
    public CategoryDTO getCategoryDTOByNo(Integer catNo,Integer langNo) {
        Category category = categoryDao.getCategoryByNo(catNo);
        if (category == null) {
            return null;
        }
        CategoryDTO categoryDto = mapper.map(category, CategoryDTO.class);
        categoryDto.setNoOfProducts(generalDAO.getCountOfRecords("Product", " AND catNo = " + categoryDto.getCatNo()));
        List<Image> imageList = imageDao.getSpecificNoOfImage(ImageAttributesConstacts.CATEGORY_IMAGE, category.getCatNo(), 4);
        List<ImageDTO> imageDtoList = new ArrayList();
        if (imageList != null) {
            for (Image image : imageList) {
                ImageDTO imageDto = mapper.map(image, ImageDTO.class);
                imageDtoList.add(imageDto);
            }
        }
        categoryDto.setImages(imageDtoList);
      //  categoryDto.setProducts(getCategoryProducts(catNo, langNo));
        return categoryDto;
    }

    @Override
    @Transactional
    public Integer addCategory(CategoryDTO categoryDto) {
        if (categoryDto.getCatNo() != null && categoryDao.getCategoryByNo(categoryDto.getCatNo()) != null) {
            return ResponseMessages.RECORD_IS_EXISTS_CODE;
        }
        if (generalDAO.checkUniquePrmtr("Category", "catNameAr", categoryDto.getCatNameAr())) {
            return ResponseMessages.DUPLICATE_RECORD_AR_CODE;
        }
        if (commonValHelper.checkStringValueIsValid(categoryDto.getCatNameEn())) {
            if (generalDAO.checkUniquePrmtr("Category", "catNameEn", categoryDto.getCatNameEn())) {
                return ResponseMessages.DUPLICATE_RECORD_EN_CODE;
            }
        }
        categoryDao.addCategory(mapper.map(categoryDto, Category.class));
        return 1;
    }

    @Override
    @Transactional
    public Integer updateCategory(CategoryDTO categoryDto) {
        Category category = categoryDao.getCategoryByNo(categoryDto.getCatNo());
        if (category == null) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        if (generalDAO.checkRecordIfExists("Category", "catNameAr", categoryDto.getCatNameAr(), "catNo", categoryDto.getCatNo())) {
            return ResponseMessages.DUPLICATE_RECORD_AR_CODE;
        }
        if (commonValHelper.checkStringValueIsValid(categoryDto.getCatNameEn())) {
            if (generalDAO.checkRecordIfExists("Category", "catNameEn", categoryDto.getCatNameEn(), "catNo", categoryDto.getCatNo())) {
                return ResponseMessages.DUPLICATE_RECORD_EN_CODE;
            }
        }
        categoryDao.editCategory(categoryDto);
        return 1;
    }

    @Override
    @Transactional
    public Integer deleteCategory(Integer catNo) {
        Category category = categoryDao.getCategoryByNo(catNo);
        if (category == null) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        return categoryDao.deleteCategory(catNo);
    }

    @Override
    @Transactional
    public boolean checkIFCategoryHasProducts(Integer catNo) {
        return categoryDao.checkIFCategoryHasProducts(catNo);
    }

    @Override
    @Transactional
    public int deleteCategoryProducts(Integer catNo) {
        Category category = categoryDao.getCategoryByNo(catNo);
        if (category == null) {
            return 0;
        }
        return categoryDao.deleteCategoryProducts(catNo);
    }

    @Override
    @Transactional
    public List<ProductDTO> getCategoryProducts(Integer catNo, Integer langNo) {
        List<Product> products = categoryDao.getCategoryProducts(catNo);
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDto = mapper.map(product, ProductDTO.class);
            productDto.setProdName(commonHelper.getNameBasedOnLangNo(productDto.getProdNameAr(), productDto.getProdNameEn(), langNo));
            List<Image> imageList = imageDao.getSpecificNoOfImage(ImageAttributesConstacts.PRODUCT_IMAGE, productDto.getProdNo().intValue(), 1);

            List<ImageDTO> imageDtoList = new ArrayList();
            if (imageList != null) {
                for (Image image : imageList) {
                    ImageDTO imageDto = mapper.map(image, ImageDTO.class);
                    imageDtoList.add(imageDto);
                }
            }
            productDto.setImages(imageDtoList);
            productDTOList.add(productDto);
        }
        return productDTOList;
    }

    @Override
    @Transactional
    public boolean checkIFCategoryIsExists(Integer catNo) {
        return categoryDao.checkIFCategoryIsExists(catNo);
    }
}
