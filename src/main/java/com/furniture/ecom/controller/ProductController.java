/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furniture.ecom._helpers.GlobalConstants;
import com.furniture.ecom._helpers.APIContainer;
import com.furniture.ecom._helpers.ImageAttributesConstacts;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom._helpers.CommonValidationHelper;
import com.furniture.ecom._helpers.JwtToken;
import com.furniture.ecom._dto.ProductMaterialsDTO;
import com.furniture.ecom._dto.RequestDTO;
import com.furniture.ecom._dto.ImageDTO;
import com.furniture.ecom._dto.ProductColorDTO;
import com.furniture.ecom._dto.ErrorDTO;
import com.furniture.ecom._dto.ProductDTO;
import com.furniture.ecom._dto.ProductFilterDTO;
import com.furniture.ecom._dto.ResultDTO;
import com.furniture.ecom.service.CategoryService;
import com.furniture.ecom.service.DiscountService;
import com.furniture.ecom.service.GeneralService;
import com.furniture.ecom.service.ImageService;
import com.furniture.ecom._model.LoginUser;
import com.furniture.ecom._model.ResponseMessages;
import com.furniture.ecom.service.FinishService;
import com.furniture.ecom.service.MaterialService;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.furniture.ecom._helpers.JsonAPIDocs;
import com.furniture.ecom._helpers.RequestResponseObj;
import com.furniture.ecom.service.ProductService;
import com.furniture.ecom.service.StyleService;
import com.furniture.ecom._util.ObjectChecker;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.*;

/**
 * @author HP
 */
@RestController
@RequestMapping("/Product/")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class ProductController {

    @Autowired
    Validator validator;
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    FinishService finishService;

    @Autowired
    MaterialService materialService;

    @Autowired
    StyleService styleService;

    @Autowired
    ImageService imageService;

    @Autowired
    DiscountService discountService;
    @Autowired
    GeneralService generalService;
    @Autowired
    JwtToken jwtToken;
    @Autowired
    CommonHelper commonHelper;
    @Autowired
    CommonValidationHelper commonValHelper;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("getProductList")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "Authorization", paramType = "header"),
        @ApiImplicitParam(example = JsonAPIDocs.GET_PRODUCT_LIST, name = "req", paramType = "body")})
    public ResponseEntity<Object> getProductList(@RequestBody RequestDTO req) throws Exception {
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        try {
            ProductFilterDTO filter = objectMapper.convertValue(req.getData().get(RequestResponseObj.FILTER), ProductFilterDTO.class);
            data.put(RequestResponseObj.COUNT, productService.getProductFilterCount(filter));
            if (data.get(RequestResponseObj.COUNT).equals(0)) {
                result = new ResultDTO(ResponseMessages.SUCCESS_CODE, "No Result to Display!!");
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }
            if ((ProductFilterDTO) req.getData().get(RequestResponseObj.FILTER) != null && filter.getPageNo() != 0) {
                int itemPerPage = (filter.getItemPerPage() != null && filter.getItemPerPage() != 0) ? filter.getItemPerPage() : 20;
                if (((filter.getPageNo() - 1) * itemPerPage) >= (int) data.get(RequestResponseObj.COUNT)) {
                    result = new ResultDTO(ResponseMessages.SUCCESS_CODE, " Incorrect Page No please try Again with correct value!!");
                    return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
                }
            }
            List<ProductDTO> productList = productService.getProductList((ProductFilterDTO) req.getData().get(RequestResponseObj.FILTER), langNo);
            data.put(RequestResponseObj.PRODUCT_LIST, productList);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            throw new NullPointerException();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("getProductByCode")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "Authorization", paramType = "header"),
        @ApiImplicitParam(example = JsonAPIDocs.GET_PRODUCT_BY_CODE, name = "req", paramType = "body")})
    public ResponseEntity<Object> getProductByCode(@RequestBody RequestDTO req) throws Exception {
        String showType = null;
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();

        try {
            ProductDTO productDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.PRODUCT), ProductDTO.class);
            if (productDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (productDto.getProdNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "prodNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            ProductDTO product = productService.getProductByNo(productDto.getProdNo(), langNo, showType);
            if (product != null) {
                data.put(RequestResponseObj.PRODUCT, product);
                result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            } else {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "prodNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("preAddUpd")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "Authorization", paramType = "header"),
        @ApiImplicitParam(example = JsonAPIDocs.PRE_ADD_UPD, name = "req", paramType = "body")})
    public ResponseEntity<Object> preAddUpd(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();

        if (commonValHelper.checkLoginUsrIsNotAnAdmin(loginUsr)) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        try {
            data.put(RequestResponseObj.FINISH_LIST, finishService.getFinishList(langNo));
            data.put(RequestResponseObj.MATERIAL_LIST, materialService.getMaterialList(langNo));
            data.put(RequestResponseObj.STYLE_LIST, styleService.getStyleList(langNo));
            data.put(RequestResponseObj.CATEGOROY_LIST, categoryService.getCategoryDTOList(langNo));
            data.put(RequestResponseObj.DISCOUNT_LIST, Arrays.asList(discountService.getDiscount()));
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (DataIntegrityViolationException | NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        }
    }

    @PostMapping("addProduct")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "Authorization", paramType = "header"),
        @ApiImplicitParam(example = JsonAPIDocs.ADD_PRODUCT, name = "req", paramType = "body")})
    public ResponseEntity<Object> addProduct(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        if (commonValHelper.checkLoginUsrIsNotAnAdmin(loginUsr)) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        try {
            ProductDTO productDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.PRODUCT), ProductDTO.class);
            if (productDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(productDto);
            if (constraintViolations != null && constraintViolations.size() > 0) {
                List<ErrorDTO> objErrors = generalService.getErrorMessages(constraintViolations, langNo);
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, objErrors);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (productDto.getProdNo() != null && productDto.getProdNo() <= 0) {
                productDto.setProdNo(null);
            }
            Map<String, Integer> errors = new HashMap<>();

            //            if (productDto.getImages() == null || productDto.getImages().size() < 1) {
            //                errors.put("images", 21);
            //            } else {
            //                for (ImageDTO image : productDto.getImages()) {
            //                    if (image.getImgPath() == null || image.getImgPath().isBlank()) {
            //                        errors.put("imgPath", 1);
            //                    } else if (!image.getImgPath().matches(GlobalConstants.IMAGE_PATTERN)) {
            //                        errors.put("imgPath#" + image.getImgPath(), 23);
            //                    }
            //                    image.setImgType(ImageAttributesConstacts.PRODUCT_IMAGE);
            //                }
            //            }
            // check categroy
            if (!generalService.checkIdIsExists("Category", "catNo", productDto.getCatNo())) {
                errors.put("category", 4);
            }
            // check style
            if (!generalService.checkIdIsExists("Style", "styleNo", productDto.getStyleNo())) {
                errors.put("style", 4);
            }
            // check finish
            if (!generalService.checkIdIsExists("Finish", "finishNo", productDto.getFinishNo())) {
                errors.put("finish", 4);
            }
            // check discount
            if (productDto.getDiscountNo() != null && productDto.getDiscountNo() > 0) {
                if (!generalService.checkIdIsExists("Discount", "discountNo", productDto.getDiscountNo())) {
                    errors.put("discountNo", 4);
                }
            }
            // check materials
            if (productDto.getProdMaterialList() == null || productDto.getProdMaterialList().isEmpty()) {
                errors.put("prodMaterial", 1);
            } else {
                Set<Integer> materialNoList = new HashSet<>();
                for (ProductMaterialsDTO material : productDto.getProdMaterialList()) {
                    if (material.getMaterialNo() == null || material.getMaterialNo() <= 0) {
                        errors.put("material", 1);
                        break;
                    } else {
                        materialNoList.add(material.getMaterialNo());
                    }
                }
                if (!materialNoList.isEmpty()) {
                    List<Integer> chkMaterialNoList = generalService.checkIdListAreExist("Material", "materialNo", materialNoList, Auth);
                    if (chkMaterialNoList == null || chkMaterialNoList.isEmpty()) {
                        errors.put("material#" + materialNoList.iterator().next(), 4);
                    } else {
                        for (Integer matNo : materialNoList) {
                            if (!chkMaterialNoList.contains(matNo)) {
                                errors.put("material#" + matNo, 4);
                            }
                        }
                    }
                }
            }
            // check colors
            if (productDto.getProdColorList() == null || productDto.getProdColorList().isEmpty()) {
                errors.put("prodColor", 1);
            } else {
                Set<Integer> colorNoList = new HashSet<>();
                for (ProductColorDTO color : productDto.getProdColorList()) {
                    if (color.getColorNo() == null || color.getColorNo() <= 0) {
                        errors.put("color", 1);
                        break;
                    } else {
                        if (!ObjectChecker.isEmptyOrNull(color.getColorImg()) && !color.getColorImg().matches(GlobalConstants.IMAGE_PATTERN)) {
                            errors.put("colorImg#" + color.getColorImg(), 23);
                            break;
                        }
                        colorNoList.add(color.getColorNo());
                        if (color.getSecondColorNo() != null) {
                            colorNoList.add(color.getSecondColorNo());
                        }

                    }
                }
                if (!colorNoList.isEmpty()) {
                    List<Integer> chkMaterialNoList = generalService.checkIdListAreExist("Color", "colorNo", colorNoList, null);
                    if (chkMaterialNoList == null || chkMaterialNoList.isEmpty()) {
                        errors.put("color#" + colorNoList.iterator().next(), 4);
                    } else {
                        for (Integer colrNo : colorNoList) {
                            if (!chkMaterialNoList.contains(colrNo)) {
                                errors.put("color#" + colrNo, 4);
                            }
                        }
                    }
                }
            }
            // warranty is not less than zero
            if (productDto.getWarranty() != null && productDto.getDiscountNo() < 0) {
                errors.put("warranty", 8);
            }
            // check taxes
//            if (productDto.getTaxNo() != null && productDto.getTaxNo() > 0) {
//                if (!generalService.checkIdIsExists("Taxes", "taxNo", productDto.getTaxNo())) {
//                    errors.put("taxNo", 4);
//                }
//            }
            if (errors.size() > 0) {
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errors, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            productDto.setCreateOn(new Date());
            productDto.setCreateAdm(loginUsr.getUsrNo().intValue());
            Integer effected = productService.addProduct(productDto);
            if (Objects.equals(effected, ResponseMessages.RECORD_IS_EXISTS_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(3, "product", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
//            // Save Images
//            try {
//                saveImages(images, effected);
//            } catch (Exception e) {
//                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
//                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
//            }
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (DataIntegrityViolationException | NullPointerException ex) {
            ex.printStackTrace();
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    //
    //    @PostMapping("updateProduct")
    //    @ApiImplicitParams(
    //            value = {
    //                @ApiImplicitParam(name = "Authorization", paramType = "header"),
    //                @ApiImplicitParam(example = JsonAPIDocs.UPDATE_PRODUCT, name = "req", paramType = "body")
    //            }
    //    )
    //    public ResponseEntity<Object> updateProduct(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
    //        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
    //        LoginUser loginUsr = jwtToken.decodeToken(Auth);
    //        Integer langNo = commonHelper.getLngNoFromRequest(req);
    //        ResultDTO result;
    //        Map<String, Object> data = new HashMap();
    //
    //        if (commonValHelper.checkLoginUsrIsNotAnAdmin(loginUsr)) {
    //            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
    //            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
    //        }
    //        try {
    //            ProductDTO productDto = (ProductDTO)req.getData().get(RequestResponseObj.PRODUCT);
    //            if (productDto == null) {
    //                result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
    //                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
    //            }
    //            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(productDto);
    //            if (constraintViolations != null && constraintViolations.size() > 0) {
    //                List<ErrorDTO> objErrors = generalService.getErrorMessages(constraintViolations, langNo);
    //                if (productDto.getProdNo() == null || productDto.getProdNo() <= 0) {
    //                    objErrors.add(new ErrorDTO("prodNo", generalService.getErrorMsg(1, "prodNo", langNo)));
    //                }
    //                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, objErrors);
    //                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
    //            }
    //            Map<String, Integer> errors = new HashMap<>();
    //            if (productDto.getProdNo() == null || productDto.getProdNo() <= 0) {
    //                errors.put("prodNo", 1);
    //            }
    //
    //            if (!finishService.checkFinishISExists(productDto.getFinish())) {
    //                errors.put("prodFinish", 4);
    //            }
    //            if (!styleService.checkStyleISExists(productDto.getStyle())) {
    //                errors.put("prodStyle", 4);
    //            }
    ////            if (!materialService.checkMaterialISExists(productDto.getProdMaterial())) {
    ////                errors.put("prodMaterial", 4);
    ////            }
    //            if (productDto.getCatNo() == null) {
    //                errors.put("category", 1);
    //            } else if (categoryService.getCategoryDTOByNo(productDto.getCatNo()) == null) {
    //                errors.put("category", 4);
    //            }
    //            if (errors.size() > 0) {
    //                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errors, langNo));
    //                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
    //            }
    //            if (Objects.equals(productService.updateProduct(productDto), ResponseMessages.NOT_FOUND_RECORD_CODE)) {
    //                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "prodNo", langNo));
    //                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
    //            }
    //            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
    //            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
    //        } catch (DataIntegrityViolationException | NullPointerException ex) {
    //            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
    //            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
    //        } catch (Exception ex) {
    //            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
    //            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
    //        }
    //    }
    //    @ApiImplicitParams(
    //            value = {
    //                @ApiImplicitParam(name = "Authorization", paramType = "header"),
    //                @ApiImplicitParam(example = JsonAPIDocs.DELETE_PRODUCT, name = "req", paramType = "body")
    //            }
    //    )
    @PostMapping("deleteProduct")
    public ResponseEntity<Object> deleteProduct(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();

        if (commonValHelper.checkLoginUsrIsNotAnAdmin(loginUsr)) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        try {
            ProductDTO productDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.PRODUCT), ProductDTO.class);
            if (productDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (productDto.getProdNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "prodNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!Objects.equals(productService.deleteProduct(productDto.getProdNo()), ResponseMessages.NOT_FOUND_RECORD_CODE)) {
                result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            } else {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "prodNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    private void saveImages(List<MultipartFile> images, Integer id) {
        List<ImageDTO> imageDtoList = new ArrayList<>();
        for (MultipartFile image : images) {
            System.out.println("File name : " + image.getOriginalFilename());
            System.out.println("File size : " + image.getSize());
            System.out.println("File type : " + image.getContentType());

            ImageDTO imageDto = new ImageDTO();
            imageDto.setItemNo(id);
            imageDto.setImgPath(image.getOriginalFilename());
            imageDto.setImgType(ImageAttributesConstacts.PRODUCT_IMAGE);
            imageDtoList.add(imageDto);
        }
        imageService.addImages(imageDtoList);
    }

    //    @PostMapping("addProductImage")
    //    @ApiImplicitParams(
    //            value = {
    //                @ApiImplicitParam(name = "Authorization", paramType = "header"),
    //                @ApiImplicitParam(example = JsonAPIDocs.ADD_PRODUCT_IMG, name = "req", paramType = "body")
    //            }
    //    )
    //    public ResponseEntity<Object> addProductImage(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
    //        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
    //        LoginUser loginUsr = jwtToken.decodeToken(Auth);
    //        Integer langNo = commonHelper.getLngNoFromRequest(req);
    //        ResultDTO result;
    //        Map<String, Object> data = new HashMap();
    //
    //        if (commonValHelper.checkLoginUsrIsNotAnAdmin(loginUsr)) {
    //            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
    //            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
    //        }
    //        try {
    //            ProductDTO productDto = (ProductDTO)req.getData().get(RequestResponseObj.PRODUCT);
    //            if (productDto == null) {
    //                result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
    //                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
    //            }
    //            Map<String, Integer> errors = new HashMap<>();
    //            if (productDto.getImages() == null || productDto.getImages().size() < 1) {
    //                errors.put("images", 21);
    //            }
    //            if (productDto.getProdNo() == null || productDto.getProdNo() < 1) {
    //                errors.put("prodNo", 8);
    //            } else if (!productService.checkProductISExists(productDto.getProdNo())) {
    //                errors.put("prodNo", 4);
    //            }
    //            List<ImageDTO> imageList = productDto.getImages();
    //            if (imageList != null && imageList.size() > 0) {
    //                for (ImageDTO image : productDto.getImages()) {
    //                    if (image.getImgPath() == null || image.getImgPath().isBlank()) {
    //                        errors.put("imgPath", 1);
    //                    } else if (!image.getImgPath().matches(GlobalConstants.IMAGE_PATTERN)) {
    //                        errors.put("imgPath", 23);
    //                    }
    //                }
    //            }
    //            if (errors.size() > 0) {
    //                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errors, langNo));
    //                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
    //            }
    //            imageService.addImages(imageList);
    //            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
    //            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
    //        } catch (DataIntegrityViolationException | NullPointerException ex) {
    //            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
    //            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
    //        } catch (Exception ex) {
    //            ex.printStackTrace();
    //            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
    //            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
    //        }
    //    }
    //
    //    @PostMapping("getMultiProductPageList")
    //    @ApiImplicitParams(
    //            value = {
    //                @ApiImplicitParam(name = "Authorization", paramType = "header"),
    //                @ApiImplicitParam(example = JsonAPIDocs.GET_PRODUCT_BY_PAGE, name = "req", paramType = "body")
    //            }
    //    )
    //    public ResponseEntity<Object> getMultiProductPageList(@RequestHeader("Authorization") String Auth, @RequestBody(required = false) RequestDTO req) throws Exception {
    //        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
    //        LoginUser loginUsr = jwtToken.decodeToken(Auth);
    //        Integer langNo = commonHelper.getLngNoFromRequest(req);
    //        ResultDTO result;
    //        Map<String, Object> data = new HashMap();
    //
    //        if (commonValHelper.checkLoginUsrIsNotAnAdmin(loginUsr)) {
    //            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
    //            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
    //        }
    //        try {
    //            Pagging pagingDto = req.getData().getPaging();
    //            if (pagingDto == null) {
    //                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
    //                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
    //            }
    //            if (pagingDto.getPageNo() <= 0) {
    //                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(8, "pageNo", langNo));
    //                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
    //            }
    //            if (pagingDto.getItmPerPage() <= 0) {
    //                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(8, "itmPerPage", langNo));
    //                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
    //            }
    //            Long count = generalService.getCountOfRecords("Product", null);
    //            if (count < (pagingDto.getItmPerPage() * pagingDto.getPageNo())) {
    //                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(20, "pageNo", langNo));
    //                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
    //            }
    //            data.setCount(count);
    //            List<ProductDTO> productList = productService.getMultiProductPageList(req.getData().getPaging(), langNo);
    //            if (productList != null || productList.size() > 0) {
    //                for (ProductDTO product : productList) {
    //                    product.setProdName(commonHelper.getNameBasedOnLangNo(product.getProdNameAr(), product.getProdNameEn(), langNo));
    //                    product.setProdDsc(commonHelper.getNameBasedOnLangNo(product.getProdDscAr(), product.getProdDscEn(), langNo));
    //                }
    //                data.setProductList(productList);
    //            }
    //            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
    //            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
    //        } catch (NullPointerException ex) {
    //            throw new NullPointerException();
    //        } catch (Exception ex) {
    //            ex.printStackTrace();
    //            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
    //            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
    //        }
    //    }
}
