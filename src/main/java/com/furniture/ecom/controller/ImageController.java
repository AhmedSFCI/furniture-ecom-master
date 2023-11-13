/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furniture.ecom._helpers.APIContainer;
import com.furniture.ecom._helpers.JwtToken;
import com.furniture.ecom._dto.ImageDTO;
import com.furniture.ecom._dto.RequestDTO;
import com.furniture.ecom._dto.ResultDTO;
import com.furniture.ecom.service.GeneralService;
import com.furniture.ecom.service.ImageService;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom._helpers.CommonValidationHelper;
import com.furniture.ecom._model.ResponseMessages;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.util.Objects;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.furniture.ecom._helpers.JsonAPIDocs;
import com.furniture.ecom._helpers.RequestResponseObj;
import com.furniture.ecom._helpers.UploadUtils;
import com.furniture.ecom._model.LoginUser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/Image/")
public class ImageController {

    @Autowired
    Validator validator;
    @Autowired
    ImageService imageService;
    @Autowired
    GeneralService generalService;
    @Autowired
    JwtToken jwtToken;
    @Autowired
    CommonHelper commonHelper;
    @Autowired
    CommonValidationHelper commonValHelper;

    @Autowired
    ServletContext servletContext;
    
    @Autowired
    ObjectMapper objectMapper;

    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.DELETE_IMAGE, name = "req", paramType = "body")
            }
    )
    @PostMapping("deleteImage")
    public ResponseEntity<Object> deleteImage(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        jwtToken.decodeToken(Auth);
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        int langNo = 1;

        try {
            langNo = commonHelper.getLngNoFromRequest(req);
            ImageDTO imageDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.IMAGE), ImageDTO.class);
            if (imageDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (imageDto.getImgNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "imgNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!Objects.equals(imageService.deleteImage(imageDto.getImgNo()), ResponseMessages.NOT_FOUND_RECORD_CODE)) {
                result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            } else {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "imgNo", langNo));
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
//
//    @ApiImplicitParams(
//            value = {
//                @ApiImplicitParam(name = "Authorization", paramType = "header"),
//                @ApiImplicitParam(example = JsonAPIDocs.UPDATE_IMAGE, name = "req", paramType = "body")
//            }
//    )
//    @PostMapping("updateImage")
//    public ResponseEntity<Object> updateImage(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
//        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
//        jwtToken.decodeToken(Auth);
//        ResultDTO result;
//        Map<String, Object> data = new HashMap();
//        int langNo = 1;
//        if (req != null && req.getData() != null) {
//            if (req.getData().getLngNo() != null && req.getData().getLngNo() == 2) {
//                langNo = 2;
//            }
//        }
//        try {
//            ImageDTO imageDto = (ImageDTO)req.getData().get(RequestResponseObj.IMAGE);
//            if (imageDto == null) {
//                result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
//                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
//            }
//
//            Map<String, Integer> errors = new HashMap<>();
//            if (imageDto.getImgNo() == null) {
//                errors.put("imgNo", 1);
//            }
//            if (imageDto.getImgPath() == null || imageDto.getImgPath().isBlank()) {
//                errors.put("imgPath", 1);
//            } else if (!imageDto.getImgPath().matches(GlobalConstants.IMAGE_PATTERN)) {
//                errors.put("imgPath", 23);
//            }
//            if (errors.size() > 0) {
//                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errors, langNo));
//                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
//            }
//            if (!Objects.equals(imageService.updateImage(imageDto), ResponseMessages.NOT_FOUND_RECORD_CODE)) {
//                result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
//                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
//            } else {
//                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "imgNo", langNo));
//                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
//            }
//        } catch (NullPointerException ex) {
//            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
//            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
//        } catch (Exception ex) {
//            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
//            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
//        }
//    }

    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.UPDATE_IMAGE, name = "req", paramType = "body")
            }
    )
    @PostMapping(value = "uploadImage/{imgType}/{itemId}")
    public ResponseEntity<Object> uploadImage(@RequestHeader("Authorization") String Auth, @RequestParam("images") MultipartFile[] images,
            @PathVariable(name = "itemId") Integer itemId, @PathVariable(name = "imgType") String imageTyp) throws Exception {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        Integer langNo = 1;
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        if (commonValHelper.checkLoginUsrIsNotAnAdmin(loginUsr)) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        try {

            saveImages(Arrays.asList(images), itemId, imageTyp);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
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

    private void saveImages(List<MultipartFile> images, Integer id, String imgTyp) throws Exception {
        List<ImageDTO> imageDtoList = new ArrayList<>();
        System.out.println("File type : " + imgTyp);
        for (MultipartFile image : images) {
            System.out.println("File name : " + image.getOriginalFilename());
            System.out.println("File size : " + image.getSize());
            System.out.println("File type : " + image.getContentType());

            ImageDTO imageDto = new ImageDTO();
            imageDto.setItemNo(id);
            imageDto.setImgPath(image.getOriginalFilename());
            imageDto.setImgType(imgTyp);
            imageDtoList.add(imageDto);
            UploadUtils uploadUtils = new UploadUtils(servletContext.getContextPath());
            uploadUtils.uploadFile(imgTyp, image);
        }
        imageService.addImages(imageDtoList);
    }
}
