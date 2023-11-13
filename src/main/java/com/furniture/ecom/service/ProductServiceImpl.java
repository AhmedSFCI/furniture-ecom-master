/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.ProductColorDTO;
import com.furniture.ecom._helpers.CommonHelperImpl;
import com.furniture.ecom._helpers.ImageAttributesConstacts;
import com.furniture.ecom.dao.ProductDAO;
import com.furniture.ecom._dto.ProductDTO;
import com.furniture.ecom._dto.ProductFilterDTO;
import com.furniture.ecom._dto.ProductMaterialsDTO;
import com.furniture.ecom._entity.Product;
import com.furniture.ecom._entity.ProductColor;
import com.furniture.ecom._entity.ProductColorPK;
import com.furniture.ecom._entity.ProductMaterials;
import com.furniture.ecom.dao.GeneralDAO;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom.dao.ColorDAO;
import java.io.IOException;
import com.furniture.ecom.dao.DiscountDAO;
import com.furniture.ecom.dao.ImageDAO;
import com.furniture.ecom._helpers.GlobalConstants;
import com.furniture.ecom._model.ResponseMessages;
import com.furniture.ecom.dao.MaterialDAO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author HP
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDAO productDao;

    @Autowired
    GeneralDAO generalDAO;
    @Autowired
    ImageService imageService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CommonHelper commonHelper;

    @Autowired
    DiscountDAO discountDao;

    @Autowired
    ColorDAO colorDao;

    @Autowired
    ImageDAO imageDao;

    @Autowired
    MaterialDAO materialDao;

    @Override
    @Transactional
    public List<ProductDTO> getProductList(ProductFilterDTO filter, Integer langNo) throws IOException {
        CommonHelperImpl utils = new CommonHelperImpl();
        List<Product> allProducts = productDao.getProductList(filter);
        if (allProducts == null || allProducts.isEmpty()) {
            return new ArrayList<>();
        }
        Set<Integer> finishNoList = new HashSet<>();
        Set<Integer> styleNoList = new HashSet<>();
        Set<Integer> categoryNoList = new HashSet<>();
        Set<Integer> materialNoList = new HashSet<>();
        Set<Long> productNoList = new HashSet<>(0);
        List<ProductDTO> productList = new ArrayList<>();
        for (Product product : allProducts) {
            ProductDTO productDto = modelMapper.map(product, ProductDTO.class);
            productDto.setProdName(commonHelper.getNameBasedOnLangNo(product.getProdNameAr(), product.getProdNameEn(), langNo));
            productDto.setImages(imageService.getSpecificNoOfImage(ImageAttributesConstacts.PRODUCT_IMAGE, product.getProdNo().intValue(), 1));
            productList.add(productDto);
            finishNoList.add(productDto.getFinishNo());
            styleNoList.add(productDto.getStyleNo());
            categoryNoList.add(productDto.getCatNo());
            productNoList.add(productDto.getProdNo());
        }
        Set<Integer> colorNoList = colorDao.getProductColors(productNoList, langNo);
        Map<Integer, String> colorMap = generalDAO.getTextListFromId("Color", "colorNm", "colorNo", colorNoList, langNo);
        Map<Integer, String> finishMap = generalDAO.getTextListFromId("Finish", "finishNm", "finishNo", finishNoList, langNo);
        Map<Integer, String> categoryMap = generalDAO.getTextListFromId("Category", "catName", "catNo", categoryNoList, langNo);
        Map<Integer, String> styleMap = generalDAO.getTextListFromId("Style", "styleNm", "styleNo", styleNoList, langNo);
        List<ProductColor> productColors = colorDao.getProductsColorsListByProdNo(productNoList, langNo);
        List<ProductMaterials> productMaterials = materialDao.getProductsMaterialsListByProdNo(productNoList, langNo);
        for (ProductDTO productDto : productList) {
            productDto.setFinishName(finishMap.get(productDto.getFinishNo()));
            productDto.setStyleName(styleMap.get(productDto.getStyleNo()));
            productDto.setCatName(categoryMap.get(productDto.getCatNo()));
            List<ProductColor> productColorList = productColors.stream().filter(prodColor -> Objects.equals(prodColor.getProductColorPK().getProdNo(), productDto.getProdNo())).collect(Collectors.toList());
            List<ProductMaterials> productMaterialList = productMaterials.stream().filter(prodMatrl -> Objects.equals(prodMatrl.getProdNo(), productDto.getProdNo())).collect(Collectors.toList());
            if (productColorList != null) {
                List<ProductColorDTO> colorDtoList = new ArrayList();
                for (ProductColor colr : productColorList) {
                    ProductColorDTO colorDto = modelMapper.map(colr, ProductColorDTO.class);
                    colorDto.setColorNo(colr.getProductColorPK().getColorNo());
                    colorDto.setProdNo(colr.getProductColorPK().getProdNo());
                    colorDto.setColorNm(colorMap.get(colorDto.getColorNo()));
                    colorDto.setSecondColorNm(colorMap.get(colorDto.getSecondColorNo()));
                    colorDtoList.add(colorDto);
                }
                productDto.setProdColorList(colorDtoList);
            }
            if (productMaterialList != null) {
                List<ProductMaterialsDTO> productMaterialsDTOList = new ArrayList();
                for (ProductMaterials prodMtrl : productMaterialList) {
                    ProductMaterialsDTO prodMtrlDto = modelMapper.map(prodMtrl, ProductMaterialsDTO.class);
                    productMaterialsDTOList.add(prodMtrlDto);
                }
                productDto.setProdMaterialList(productMaterialsDTOList);
            }
        }
        return productList;
    }

    @Override
    @Transactional
    public ProductDTO getProductByNo(Long productNo, Integer langNo, String showTyp) throws IOException {
        CommonHelperImpl utils = new CommonHelperImpl();
        Product product = productDao.getProductByNo(productNo);
        if (product == null) {
            return null;
        }
        productDao.updateProductViewCount(productNo);
        ProductDTO productDto = modelMapper.map(product, ProductDTO.class
        );
        productDto.setProdName(commonHelper.getNameBasedOnLangNo(product.getProdNameAr(), product.getProdNameEn(), langNo));
        productDto.setProdDsc(commonHelper.getNameBasedOnLangNo(product.getProdDscAr(), product.getProdDscEn(), langNo));
        productDto.setImages(imageService.getImages(ImageAttributesConstacts.PRODUCT_IMAGE, productNo.intValue()));
        productDto.setFinishName(generalDAO.getTextFromId("Finish", "finishNm", "finishNo", productDto.getFinishNo(), langNo));
        productDto.setStyleName(generalDAO.getTextFromId("Style", "styleNm", "styleNo", productDto.getStyleNo(), langNo));
        productDto.setCatName(generalDAO.getTextFromId("Category", "catName", "catNo", productDto.getCatNo(), langNo));
        Object discountValue = generalDAO.getAttributeValue("Discount", "discountRate", "discountNo", productDto.getDiscountNo());
        productDto.setDiscountValue((discountValue == null) ? null : (Double) discountValue);
//        Object taxValue = generalDAO.getAttributeValue("Taxes", "percentage", "taxNo", productDto.getTaxNo());
//        productDto.setTaxValue((taxValue == null) ? null : (Double) taxValue);
//        if (showTyp == null || !showTyp.equals(GlobalConstants.ADMIN_SHOW)) {
//            productDto.setRelatedProducts(generalDAO.getProductsInfo("catNo", productDto.getCatNo(), null, 6, langNo));
//        }
        productDto.setProdColorList(colorDao.getProductColorByProdNo(productDto.getProdNo(), langNo));
        productDto.setProdMaterialList(materialDao.getProductMaterialsByProdNo(productDto.getProdNo(), langNo));
        return productDto;
    }

    @Override
    @Transactional
    public Integer deleteProduct(Long productNo) {
        if (!productDao.checkProductISExists(productNo)) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        materialDao.deleteProductMaterialsByProdNo(productNo);
        return productDao.deleteProduct(productNo);
    }

    @Override
    @Transactional
    public Integer addProduct(ProductDTO productDto) {
        if (productDto.getProdNo() != null && productDao.checkProductISExists(productDto.getProdNo())) {
            return ResponseMessages.RECORD_IS_EXISTS_CODE;
        }
        productDto.setViewsCount(0L);
        Product product = productDao.addProduct(modelMapper.map(productDto, Product.class));
//        List<ImageDTO> imgageDtoList = productDto.getImages();
//        List<Image> images = new ArrayList();
//        for (ImageDTO imageDto : imgageDtoList) {
//            imageDto.setItemNo(product.getProdNo().intValue());
//            imageDto.setImgType(ImageAttributesConstacts.PRODUCT_IMAGE);
//            images.add(modelMapper.map(imageDto, Image.class
//            ));
//        }
//        imageDao.addImages(images);
        List<ProductColor> productColorList = new ArrayList();
        productDto.getProdColorList().forEach(color -> {
            ProductColor prodColor = modelMapper.map(color, ProductColor.class
            );
            ProductColorPK colorPK = new ProductColorPK(color.getColorNo(), product.getProdNo());
            prodColor.setProductColorPK(colorPK);
            productColorList.add(prodColor);
        });
        colorDao.addProductColorList(productColorList);
        List<ProductMaterials> prodMaterialsList = new ArrayList();
        productDto.getProdMaterialList().forEach(material -> {
            ProductMaterials prodMaterial = new ProductMaterials();
            prodMaterial.setProdNo(product.getProdNo());
            prodMaterial.setMaterialNo(material.getMaterialNo());
            prodMaterialsList.add(prodMaterial);
        });
        materialDao.addProductMaterialsList(prodMaterialsList);

        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    @Transactional
    public Long getProductFilterCount(ProductFilterDTO filter) {
        return productDao.getProductFilterCount(filter);
    }
//
//    @Override
//    @Transactional
//    public Integer updateProduct(ProductDTO productDto) {
//        Product product = productDao.getProductByNo(productDto.getProdNo());
//        if (product == null) {
//            return ResponseMessages.NOT_FOUND_RECORD_CODE;
//        }
//        productDao.updateProduct(productDto);
//        return 1;
//    }
//
//    @Override
//    @Transactional
//    public boolean checkProductISExists(Long productNo) {
//        return productDao.checkProductISExists(productNo);
//    }
//
//    @Override
//    @Transactional
//    public List<ProductDTO> getMultiProductPageList(Pagging paging, Integer langNo) throws IOException {
//        CommonHelperImpl utils = new CommonHelperImpl();
//        List<Product> allProducts = productDao.getMultiProductPageList(paging);
//        if (allProducts == null || allProducts.isEmpty()) {
//            return new ArrayList<>();
//        }
//        List<ProductDTO> productList = new ArrayList<>();
//        for (Product product : allProducts) {
//            ProductDTO productDto = modelMapper.map(product, ProductDTO.class);
//            productDto.setProdColorDsc(utils.getFlgValBasedOnLang(GlobalConstants.PRODUCT_COLOR, productDto.getProdColor(), langNo));
////            productDto.setProdFinishDsc(utils.getFlgValBasedOnLang(GlobalConstants.PRODUCT_FINISH, productDto.getProdFinish(), langNo));
////            productDto.setProdMaterialDsc(utils.getFlgValBasedOnLang(GlobalConstants.PRODUCT_MATERIAL, productDto.getProdMaterial(), langNo));
////            productDto.setProdStyleDsc(utils.getFlgValBasedOnLang(GlobalConstants.PRODUCT_STYLE, productDto.getProdStyle(), langNo));
//            productList.add(productDto);
//        }
//        return productList;
//    }

    @Transactional
    @Override
    public Map<Long, Product> getProductListByNos(Set<Long> prodList) {
        return productDao.getProductListByNos(prodList);
    }
}
