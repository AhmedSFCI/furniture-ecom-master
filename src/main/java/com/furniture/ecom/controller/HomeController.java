/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.controller;

import com.furniture.ecom._helpers.APIContainer;
import com.furniture.ecom._helpers.JwtToken;
import com.furniture.ecom.service.AboutService;
import com.furniture.ecom._dto.AboutDTO;
import com.furniture.ecom._dto.RequestDTO;
import com.furniture.ecom._dto.ResultDTO;
import com.furniture.ecom.service.GeneralService;
import com.furniture.ecom.service.ImageService;
import com.furniture.ecom._helpers.CommonHelper;
import com.furniture.ecom._model.ResponseMessages;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.util.List;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.furniture.ecom._helpers.JsonAPIDocs;
import com.furniture.ecom._helpers.RequestResponseObj;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/Home/")
public class HomeController {

    @Autowired
    Validator validator;
    @Autowired
    ImageService imageService;
    @Autowired
    AboutService aboutService;
    @Autowired
    GeneralService generalService;
    @Autowired
    JwtToken jwtToken;
    @Autowired
    CommonHelper commonHelper;


}
