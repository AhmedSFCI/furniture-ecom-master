/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furniture.ecom._model.ResponseMessages;
import com.furniture.ecom._dto.ResultDTO;
import com.furniture.ecom._helpers.APIContainer;
import com.furniture.ecom._helpers.GlobalConstants;
import com.furniture.ecom._helpers.JwtToken;
import com.furniture.ecom._helpers.PasswordEncoder;
import com.furniture.ecom._dto.AdminDTO;
import com.furniture.ecom.service.GeneralService;
import com.furniture.ecom._dto.ErrorDTO;
import com.furniture.ecom._dto.RequestDTO;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.furniture.ecom.service.AdminService;
import com.furniture.ecom._enums.RoleType;
import com.furniture.ecom.service.ImageService;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom._helpers.CommonValidationHelper;
import com.furniture.ecom._model.LoginData;
import com.furniture.ecom._model.LoginUser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RequestHeader;
import com.furniture.ecom._helpers.JsonAPIDocs;
import com.furniture.ecom._helpers.RequestResponseObj;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/Admin/")
public class AdminController {

    @Autowired
    Validator validator;
    @Autowired
    AdminService adminService;
    @Autowired
    GeneralService generalService;
    @Autowired
    ImageService imageService;
    @Autowired
    CommonHelper commonHelper;
    @Autowired
    CommonValidationHelper commonValHelper;
    @Autowired
    JwtToken jwtToken;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("getAdminList")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_ADMIN_LIST, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> getAdminList(@RequestHeader("Authorization") String Auth, @RequestBody(required = false) RequestDTO req) throws Exception {
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
            data.put(RequestResponseObj.ADMIN_LIST, adminService.getAdminList(langNo));
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

    @PostMapping("getAdminByCode")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.GET_ADMIN_BY_CODE, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> getAdminByCode(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
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
            AdminDTO adminDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.ADMIN), AdminDTO.class);
            if (adminDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (adminDto.getAdmNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "admNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            AdminDTO admin = adminService.getAdminByAdmNo(adminDto.getAdmNo());
            if (admin != null) {
                if (admin.getAdmPic() == null || admin.getAdmPic().isBlank()) {
                    admin.setAdmPic(GlobalConstants.USER_IMG);
                }
                admin.setAdmRoleTypeDsc(commonHelper.getFlgValBasedOnLang(GlobalConstants.ADMIN_ROLE_TYPE, admin.getAdmRoleType(), langNo));
                data.put(RequestResponseObj.ADMIN, admin);
                result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            } else {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "admNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }

    }

    @PostMapping("addAdmin")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.ADD_ADMIN, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> addAdmin(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
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
            AdminDTO adminDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.ADMIN), AdminDTO.class);
            if (adminDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(adminDto);
            if (!commonHelper.checkFlgValIsExists(GlobalConstants.ADMIN_ROLE_TYPE, adminDto.getAdmRoleType())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(20, "admRoleType", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (constraintViolations != null && constraintViolations.size() > 0) {
                List<ErrorDTO> objErrors = generalService.getErrorMessages(constraintViolations, langNo);
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, objErrors);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }

            if (adminDto.getAdmNo() != null && adminDto.getAdmNo() <= 0) {
                adminDto.setAdmNo(null);
            }
            adminDto.setAdmPassword(PasswordEncoder.encrypt(adminDto.getAdmPassword()));
            adminDto.setCreateOn(new Date());
            if (adminService.addAdmin(adminDto)) {
                result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(3, "admCode", langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);

        } catch (DataIntegrityViolationException | NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
    }

    @PostMapping("deleteAdmin")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.DELETE_ADMIN, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> deleteAdmin(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
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
            AdminDTO adminDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.ADMIN), AdminDTO.class);
            if (adminDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (adminDto.getAdmNo() == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(1, "admNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            } else if (adminDto.getAdmNo() == 1) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(16, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            } else if (adminDto.getAdmNo() == loginUsr.getUsrNo().intValue()) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(17, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }

            if (!Objects.equals(adminService.deleteAdmin(adminDto.getAdmNo()), ResponseMessages.NOT_FOUND_RECORD_CODE)) {
                result = new ResultDTO(ResponseMessages.SUCCESS_CODE, ResponseMessages.SUCCESS_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            } else {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "admNo", langNo));
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

    @PostMapping("updateAdmin")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.UPDATE_ADMIN, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> updateAdmin(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
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
        AdminDTO adminDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.ADMIN), AdminDTO.class);
        if (adminDto == null) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        if (adminDto.getAdmNo() == null) {
            errorsList.put("admNo", 1);
        } else if (adminDto.getAdmNo() != loginUsr.getUsrNo().intValue()) {
            if (!PasswordEncoder.decrypt(loginUsr.getRoleTyp()).equals(RoleType.ADMIN.toString())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(15, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
            }
        }
        if (adminDto.getAdmCode() == null || adminDto.getAdmCode().isBlank()) {
            errorsList.put("admCode", 1);
        } else if (adminDto.getAdmCode().length() < 5) {
            errorsList.put("admCode", 13);
        }
        if (adminDto.getAdmName() == null || adminDto.getAdmName().isBlank()) {
            errorsList.put("admName", 1);
        } else if (adminDto.getAdmName().length() < 5) {
            errorsList.put("admName", 13);
        }
        if (adminDto.getAdmRoleType() == null) {
            errorsList.put("admRoleType", 1);
        } else if (!PasswordEncoder.decrypt(loginUsr.getRoleTyp()).equals(RoleType.ADMIN.toString())) {
            errorsList.put("admRoleType", 20);
        }
        if (adminDto.getInActive() == null) {
            short inactv = 0;
            adminDto.setInActive(inactv);
            adminDto.setInactivDate(null);
        } else if (adminDto.getInActive() < 0 || adminDto.getInActive() > 1) {
            errorsList.put("inActive", 6);
        }
        if (errorsList.size() > 0) {
            result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        if (adminDto.getInActive() == 1) {
            adminDto.setInactivDate(new Date());
        }

        try {
            Integer effectsRow = adminService.updateAdmin(adminDto);
            if (Objects.equals(effectsRow, ResponseMessages.NOT_FOUND_RECORD_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(3, "admCode", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (Objects.equals(effectsRow, ResponseMessages.NOT_FOUND_RECORD_CODE)) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "admNo", langNo));
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

    @PostMapping("changePassword")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.CHANGE_PASSWORD, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> changeAdminPassword(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
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
            AdminDTO adminDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.ADMIN), AdminDTO.class);
            if (adminDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (adminDto.getAdmNo() == null) {
                errorsList.put("admNo", 1);
            } else if (adminDto.getAdmNo() != loginUsr.getUsrNo().intValue()) {
                if (!PasswordEncoder.decrypt(loginUsr.getRoleTyp()).equals(RoleType.ADMIN.toString())) {
                    result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(15, langNo));
                    return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
                }
            }
            if (adminDto.getOldPassword() == null || adminDto.getOldPassword().isBlank()) {
                errorsList.put("oldPassword", 1);
            }
            if (adminDto.getAdmPassword() == null || adminDto.getAdmPassword().isBlank()) {
                errorsList.put("password", 1);
            } else if (adminDto.getAdmPassword().length() < 8) {
                errorsList.put("password", 13);
            }

            if (adminDto.getConfirmAdmPassword() == null || adminDto.getConfirmAdmPassword().isBlank()) {
                errorsList.put("confirmPassword", 1);
            } else if (adminDto.getConfirmAdmPassword().length() < 8) {
                errorsList.put("confirmPassword", 13);
            }

            if (errorsList.size() > 0) {
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!adminDto.getAdmPassword().equalsIgnoreCase(adminDto.getConfirmAdmPassword())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(7, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }

            AdminDTO updatedAdmin = adminService.getAdminByAdmNo(adminDto.getAdmNo());
            if (updatedAdmin == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(4, "admNo", langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (!updatedAdmin.getAdmPassword().equals(PasswordEncoder.encrypt(adminDto.getAdmPassword()))) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(5, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            adminDto.setAdmPassword(PasswordEncoder.encrypt(adminDto.getAdmPassword()));
            adminService.updateAdminPassword(adminDto);
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

    @PostMapping("login")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.LOGIN, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> login(@RequestBody RequestDTO req) throws Exception {
        ResultDTO result;
        Map<String, Object> data = new HashMap();
        Map<String, Integer> errorsList = new HashMap<>();
        int langNo = commonHelper.getLngNoFromRequest(req);
        try {
            Object obj = req.getData().get(RequestResponseObj.LOGIN_DATA);

            LoginData loginData = objectMapper.convertValue(obj, LoginData.class);
            if (loginData == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }

            if (loginData.getAdmCode() == null || loginData.getAdmCode().isBlank()) {
                errorsList.put("admCode", 1);
            }
            if (loginData.getPassword() == null || loginData.getPassword().isBlank()) {
                errorsList.put("password", 1);
            }
            if (errorsList.size() > 0) {
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            AdminDTO adminDto = adminService.getAdminByCode(loginData.getAdmCode());
            if (adminDto == null) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(10, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (adminDto.getInActive() == 1) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(9, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            if (PasswordEncoder.encrypt(loginData.getPassword()) == null ? adminDto.getAdmPassword() != null : !PasswordEncoder.encrypt(loginData.getPassword()).equals(adminDto.getAdmPassword())) {
                result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, generalService.getErrorMsg(5, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            String token = jwtToken.createToken(adminDto.getAdmNo().longValue(), adminDto.getAdmCode(), PasswordEncoder.encrypt(adminDto.getAdmRoleType()), adminDto.getAdmName(), adminDto.getAdmPic(), "furniture.com");
            //System.out.println("token : " + token);
            data.put(RequestResponseObj.TOAKEN, token);
            adminDto.setAdmPassword(null);
            data.put(RequestResponseObj.ADMIN, adminDto);
            result = new ResultDTO(ResponseMessages.SUCCESS_CODE, generalService.getErrorMsg(12, langNo));
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (DataIntegrityViolationException | NullPointerException ex) {
            result = new ResultDTO(ResponseMessages.CLIENT_WRONG_PARAMETER_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1] + ex.getMessage());
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = new ResultDTO(ResponseMessages.SERVER_ERROR_CODE, ResponseMessages.SERVER_ERROR_MSG[langNo - 1] + ex.getMessage());
            return new ResponseEntity<>(APIContainer.responseWrapper(data, result), HttpStatus.OK);
        }
    }

    @PostMapping("preAddUpd")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.PRE_ADD_UPD, name = "req", paramType = "body")
            }
    )
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
            AdminDTO admin = new AdminDTO();
            admin.setAdmRoleList(commonHelper.getFlgList(GlobalConstants.ADMIN_ROLE_TYPE, langNo));
            data.put(RequestResponseObj.ADMIN, admin);
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

    @PostMapping("chnageAdminImg")
    @ApiImplicitParams(
            value = {
                @ApiImplicitParam(name = "Authorization", paramType = "header"),
                @ApiImplicitParam(example = JsonAPIDocs.UPDATE_ADMIN_IMG, name = "req", paramType = "body")
            }
    )
    public ResponseEntity<Object> chnageAdminImg(@RequestHeader("Authorization") String Auth, @RequestBody RequestDTO req) throws Exception {
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
        AdminDTO adminDto = objectMapper.convertValue(req.getData().get(RequestResponseObj.ADMIN), AdminDTO.class);
        if (adminDto == null) {
            result = new ResultDTO(ResponseMessages.CLIENT_ERROR_CODE, ResponseMessages.CLIENT_ERROR_MSG[langNo - 1]);
            return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
        }
        if (adminDto.getAdmNo() == null) {
            errorsList.put("admNo", 1);
        } else if (adminService.checkAdminISExists(adminDto.getAdmNo())) {
            errorsList.put("admNo", 4);
        }
        if (adminDto.getAdmPic() == null || adminDto.getAdmPic().isBlank()) {
            errorsList.put("imgPath", 1);
        } else if (!adminDto.getAdmPic().matches(GlobalConstants.IMAGE_PATTERN)) {
            errorsList.put("imgPath", 23);
        }
        try {
            if (errorsList.size() > 0) {
                result = new ResultDTO(ResponseMessages.CLIENT_FORM_ERROR_CODE, generalService.getErrorMsg(errorsList, langNo));
                return new ResponseEntity<>(APIContainer.responseWrapper(result), HttpStatus.OK);
            }
            adminService.updateAdminPic(adminDto);
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

}
