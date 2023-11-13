/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furniture.ecom._helpers.APIContainer;
import com.furniture.ecom._helpers.GlobalConstants;
import com.furniture.ecom._helpers.ImageAttributesConstacts;
import com.furniture.ecom._helpers.JwtToken;
import com.furniture.ecom.service.CategoryService;
import com.furniture.ecom.service.GeneralService;
import com.furniture.ecom._dto.ErrorDTO;
import com.furniture.ecom._dto.ImageDTO;
import com.furniture.ecom._dto.CategoryDTO;
import com.furniture.ecom._model.LoginUser;
import com.furniture.ecom._dto.RequestDTO;
import com.furniture.ecom._model.ResponseMessages;
import com.furniture.ecom._dto.ResultDTO;
import com.furniture.ecom.service.ImageService;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom._helpers.CommonValidationHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.furniture.ecom._helpers.JsonAPIDocs;
import com.furniture.ecom._helpers.RequestResponseObj;
import org.apache.catalina.mapper.Mapper;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/Category/")
public class CategoryController {

    @Autowired
    Validator validator;
    @Autowired
    CategoryService categoryService;
    @Autowired
    GeneralService generalService;
    @Autowired
    ImageService imageService;
    @Autowired
    JwtToken jwtToken;
    @Autowired
    CommonHelper commonHelper;
    @Autowired
    CommonValidationHelper commonValHelper;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("getCategoryList")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_CATEGORY_LIST, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> getCategoryList(@RequestHeader("Authorization") String Auth, @RequestBody(required = false) RequestDTO req) throws Exception {

        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        try {
            List<CategoryDTO> categoryDtoList = categoryService.getCategoryDTOList(langNo);
            data.put(RequestResponseObj.CATEGORY_LIST, categoryDtoList);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (NullPointerException ex) {
            throw new NullPointerException();
        } catch (Exception ex) {
            ex.printStackTrace();
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("getCategoryByCode")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_CATEGORY_BY_CODE, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> getCategoryByCode(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        try {
            CategoryDTO categoryDTO = objectMapper.convertValue(req.getData().get(RequestResponseObj.CATEGORY),CategoryDTO.class);
            if (categoryDTO == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            Map<String, Integer> errorsList = new HashMap<>();
            if (categoryDTO.getCatNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "catNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            CategoryDTO category = categoryService.getCategoryDTOByNo(categoryDTO.getCatNo(), langNo);
            if (category != null) {
                category.setCategoryName(commonHelper.getNameBasedOnLangNo(category.getCatNameAr(), category.getCatNameEn(), langNo));
                category.setCatDsc(commonHelper.getNameBasedOnLangNo(category.getCatDscAr(), category.getCatDscEn(), langNo));
                categoryService.getCategoryProducts(category.getCatNo(), langNo);
                data.put(RequestResponseObj.CATEGORY, category);
                result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            } else {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "catNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("addCategory")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.ADD_CATEGORY, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> addCategory(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        if (commonValHelper.checkLoginUsrIsNotAnAdmin(loginUsr)) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        CategoryDTO categoryDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.CATEGORY), CategoryDTO.class);
        if (categoryDto == null) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(categoryDto);
        if (constraintViolations != null && constraintViolations.size() > 0) {
            List<ErrorDTO> objErrors = generalService.getErrorMessages(constraintViolations, langNo);
            result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, objErrors);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        try {
            Map<String, Integer> errors = new HashMap<>();
            if (categoryDto.getImages() != null && categoryDto.getImages().size() < 1) {
                for (ImageDTO image : categoryDto.getImages()) {
                    if (image.getImgPath() == null || image.getImgPath().isBlank()) {
                        errors.put("imgPath", 21);
                    } else if (!image.getImgPath().matches(GlobalConstants.IMAGE_PATTERN)) {
                        errors.put("imgPath", 23);
                    }
                }
            } else {
                errors.put("images", 21);
            }
            if (errors.size() > 0) {
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errors, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            Integer effectsRow = categoryService.addCategory(categoryDto);
            if (Objects.equals(effectsRow, ResponseMessages.RECORD_IS_EXISTS_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(3, "catNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (Objects.equals(effectsRow, ResponseMessages.DUPLICATE_RECORD_AR_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(3, "catNameAr", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (Objects.equals(effectsRow, ResponseMessages.DUPLICATE_RECORD_EN_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(3, "catNameEn", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (DataIntegrityViolationException | NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("deleteCategory")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.DELETE_CATEGORY, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> deleteCategory(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
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
            CategoryDTO categoryDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.CATEGORY),CategoryDTO.class);
            if (categoryDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (categoryDto.getCatNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "catNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (categoryService.checkIFCategoryHasProducts(categoryDto.getCatNo())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(24, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!Objects.equals(categoryService.deleteCategory(categoryDto.getCatNo()), ResponseMessages.NOT_FOUND_RECORD_CODE)) {
                result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            } else {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "catNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }

        } catch (DataIntegrityViolationException | NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("updateCategory")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.UPDATE_CATEGORY, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> updateCategory(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();

        if (commonValHelper.checkLoginUsrIsNotAnAdmin(loginUsr)) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        Map<String, Integer> errorsList = new HashMap<>();
        try {
            CategoryDTO categoryDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.CATEGORY),CategoryDTO.class);
            if (categoryDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (categoryDto.getCatNo() == null) {
                errorsList.put("catNo", 1);
            }
            if (categoryDto.getCatNameAr() == null || categoryDto.getCatNameAr().isBlank()) {
                errorsList.put("catNameAr", 1);
            }
            if (errorsList.size() > 0) {
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }

            Integer rowsEffect = categoryService.updateCategory(categoryDto);
            if (Objects.equals(rowsEffect, ResponseMessages.DUPLICATE_RECORD_AR_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(3, "catNameAr", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (Objects.equals(rowsEffect, ResponseMessages.DUPLICATE_RECORD_AR_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(3, "catNameEn", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (Objects.equals(rowsEffect, ResponseMessages.NOT_FOUND_RECORD_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "catNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (DataIntegrityViolationException | NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("addCategoryImage")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.ADD_CATEGORY_IMG, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> addCategoryImage(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
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
            CategoryDTO categoryDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.CATEGORY),CategoryDTO.class);
            if (categoryDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            Map<String, Integer> errors = new HashMap<>();
            if (categoryDto.getCatNo() == null || categoryDto.getCatNo() < 1) {
                errors.put("catNo", 1);
            } else if (!categoryService.checkIFCategoryIsExists(categoryDto.getCatNo())) {
                errors.put("catNo", 4);
            }
            if (categoryDto.getImages() == null || categoryDto.getImages().size() < 1) {
                errors.put("image", 21);
            }
            List<ImageDTO> imageList = categoryDto.getImages();
            if (imageList != null) {
                for (ImageDTO image : categoryDto.getImages()) {
                    if (image.getImgPath() == null || image.getImgPath().isBlank()) {
                        errors.put("imgPath", 1);
                    } else if (!image.getImgPath().matches(GlobalConstants.IMAGE_PATTERN)) {
                        errors.put("imgPath", 23);
                    }
                }
            }
            if (errors.size() > 0) {
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errors, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            for (ImageDTO image : categoryDto.getImages()) {
                image.setItemNo(categoryDto.getCatNo());
                image.setImgType(ImageAttributesConstacts.CATEGORY_IMAGE);
            }
            imageService.addImages(imageList);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (DataIntegrityViolationException | NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }
}
