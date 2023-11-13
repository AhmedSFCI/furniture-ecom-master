/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furniture.ecom.service.PolicyService;
import com.furniture.ecom._helpers.APIContainer;
import com.furniture.ecom._helpers.GlobalConstants;
import com.furniture.ecom._helpers.ImageAttributesConstacts;
import com.furniture.ecom._helpers.JwtToken;
import com.furniture.ecom._dto.PolicyDTO;
import com.furniture.ecom.service.GeneralService;
import com.furniture.ecom._dto.ErrorDTO;
import com.furniture.ecom._dto.ImageDTO;
import com.furniture.ecom._model.LoginUser;
import com.furniture.ecom._dto.RequestDTO;
import com.furniture.ecom._model.ResponseMessages;
import com.furniture.ecom._dto.ResultDTO;
import com.furniture.ecom.service.ImageService;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom._helpers.CommonValidationHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/Policy/")
public class PolicyController {

    @Autowired
    Validator validator;
    @Autowired
    PolicyService policyService;
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

    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_POLICY_LIST, name = "req", paramType = "body")
            }
    )
    @PostMapping("getPolicyList")
    public ResponseEntity<Object> getPolicyList(@RequestHeader("Authorization") String Auth, @RequestBody(required = false) RequestDTO req) throws Exception {
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
            List<PolicyDTO> policyList = policyService.getPolicyList();
            for (PolicyDTO policy : policyList) {
                policy.setPolicyTxt(commonHelper.getNameBasedOnLangNo(policy.getPolicyTxtAr(), policy.getPolicyTxtEn(), langNo));
            }
           data.put(RequestResponseObj.POLICY_LIST,policyList);
            List<ImageDTO> imageList = imageService.getSpecificNoOfImage(ImageAttributesConstacts.POLICY_IMAGE, 0, 4);
            if (imageList != null && imageList.size() > 0) {
               data.put(RequestResponseObj.IMAGES_LIST,imageList);
            } else {
                data.put(RequestResponseObj.IMAGES_LIST,new ArrayList<>());
            }
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (NullPointerException ex) {
            throw new NullPointerException();
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_ABOUT_ACTIVE_LIST, name = "req", paramType = "body")
            }
    )
    @PostMapping("getActivePolicyList")
    public ResponseEntity<Object> getActivePolicyList(@RequestBody(required = false) RequestDTO req) throws Exception {
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        try {
            List<PolicyDTO> policyList = policyService.getEnabledPolicyList();
            for (PolicyDTO policy : policyList) {
                policy.setPolicyTxt(commonHelper.getNameBasedOnLangNo(policy.getPolicyTxtAr(), policy.getPolicyTxtEn(), langNo));
            }
            List<ImageDTO> imageList = new ArrayList();
            if (policyList != null && !policyList.isEmpty()) {
                imageList = imageService.getImages(ImageAttributesConstacts.POLICY_IMAGE, 0);
            }
            data.put(RequestResponseObj.POLICY_LIST,policyList);
            data.put(RequestResponseObj.IMAGES_LIST,imageList);
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

    @PostMapping("getPolicyByCode")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_POLICY_BY_CODE, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> getPolicyByCode(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
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
            PolicyDTO policyDto = objectMapper.convertValue(data.get(RequestResponseObj.POLICY),PolicyDTO.class);
            if (policyDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (policyDto.getPolicyNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "policyNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            PolicyDTO policy = policyService.getPolicyByNo(policyDto.getPolicyNo());
            if (policy != null) {
                policy.setPolicyTxt(commonHelper.getNameBasedOnLangNo(policy.getPolicyTxtAr(), policy.getPolicyTxtEn(), langNo));
                data.put(RequestResponseObj.POLICY,policy);
                result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            } else {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "policyNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("deletePolicy")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.DELETE_POLICY, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> deletePolicy(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
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
            PolicyDTO policyDto = objectMapper.convertValue(data.get(RequestResponseObj.POLICY),PolicyDTO.class);
            if (policyDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (policyDto.getPolicyNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "policyNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!Objects.equals(policyService.deletePolicy(policyDto.getPolicyNo()), ResponseMessages.NOT_FOUND_RECORD_CODE)) {
                result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            } else {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "policyNo", langNo));
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

    @PostMapping("addPolicy")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.ADD_POLICY, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> addPolicy(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
        Auth = Auth.substring(Auth.lastIndexOf(" ") + 1);
        LoginUser loginUsr = jwtToken.decodeToken(Auth);
        System.out.println(loginUsr);
        Integer langNo = commonHelper.getLngNoFromRequest(req);
        ResultDTO result;
        Map<String, Object> data = new HashMap();

        if (commonValHelper.checkLoginUsrIsNotAnAdmin(loginUsr)) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(27, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        try {
            PolicyDTO policyDto = objectMapper.convertValue(data.get(RequestResponseObj.POLICY),PolicyDTO.class);
            if (policyDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(policyDto);
            if (constraintViolations != null && constraintViolations.size() > 0) {
                List<ErrorDTO> objErrors = generalService.getErrorMessages(constraintViolations, langNo);
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, objErrors);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }

            if (policyDto.getPolicyNo() != null && policyDto.getPolicyNo() <= 0) {
                policyDto.setPolicyNo(null);
            }
            policyDto.setCreateOn(new Date());
            policyDto.setCreateAdm(loginUsr.getUsrNo().intValue());
            policyDto.setCreateOn(new Date());
            if (policyDto.getEnabled() == null || policyDto.getEnabled() < 0 || policyDto.getEnabled() > 1) {
                short enabled = 1;
                policyDto.setEnabled(enabled);
            }
            Integer effectsRow = policyService.addPolicy(policyDto);
            if (Objects.equals(effectsRow, ResponseMessages.RECORD_IS_EXISTS_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(3, "policyNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (Objects.equals(effectsRow, ResponseMessages.DUPLICATE_RECORD_AR_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(3, "policyTxtAr", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (Objects.equals(effectsRow, ResponseMessages.DUPLICATE_RECORD_EN_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(3, "policyTxtEn", langNo));
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

    @PostMapping("updatePolicy")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.UPDATE_POLICY, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> updatePolicy(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
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
        PolicyDTO policyDto = objectMapper.convertValue(data.get(RequestResponseObj.POLICY),PolicyDTO.class);
        if (policyDto == null) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        if (policyDto.getPolicyNo() == null) {
            errorsList.put("policyNo", 1);
        }
        if (policyDto.getPolicyTxtAr() == null || policyDto.getPolicyTxtAr().isBlank()) {
            errorsList.put("policyTxtAr", 1);
        } else if (policyDto.getPolicyTxtAr().length() < 50) {
            errorsList.put("policyTxtAr", 19);
        }
        if (policyDto.getEnabled() == null || policyDto.getEnabled() > 1 || policyDto.getEnabled() < 0) {
            errorsList.put("enabled", 6);
        }
        if (errorsList.size() > 0) {
            result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        try {
            Integer effectsRow = policyService.editPolicy(policyDto);
            if (Objects.equals(effectsRow, ResponseMessages.NOT_FOUND_RECORD_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "policyNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (Objects.equals(effectsRow, ResponseMessages.DUPLICATE_RECORD_AR_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(3, "policyTxtAr", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (Objects.equals(effectsRow, ResponseMessages.DUPLICATE_RECORD_EN_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(3, "policyTxtEn", langNo));
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

    @PostMapping("addPolicyImage")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.ADD_POLICY_IMG, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> addPolicyImage(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
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
            PolicyDTO policyDto = objectMapper.convertValue(data.get(RequestResponseObj.POLICY),PolicyDTO.class);
            if (policyDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            Map<String, Integer> errors = new HashMap<>();
            if (policyDto.getPolicyNo() == null || policyDto.getPolicyNo() < 1) {
                errors.put("policyNo", 1);
            } else if (!policyService.checkIFPolicyIsExists(policyDto.getPolicyNo())) {
                errors.put("policyNo", 4);
            }
            if (policyDto.getImages() == null || policyDto.getImages().size() < 1) {
                errors.put("image", 21);
            }
            List<ImageDTO> imageList = policyDto.getImages();
            if (imageList != null) {
                for (ImageDTO image : policyDto.getImages()) {
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
            for (ImageDTO image : policyDto.getImages()) {
                image.setItemNo(0);
                image.setImgType(ImageAttributesConstacts.POLICY_IMAGE);
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
