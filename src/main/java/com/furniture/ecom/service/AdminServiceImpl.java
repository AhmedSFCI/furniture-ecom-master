/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._helpers.CommonHelperImpl;
import com.furniture.ecom._helpers.GlobalConstants;
import com.furniture.ecom._dto.AdminDTO;
import com.furniture.ecom._entity.Admin;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.furniture.ecom.dao.AdminDAO;
import com.furniture.ecom._model.ResponseMessages;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author HP
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDAO adminDao;
    @Autowired
    ModelMapper mapper;

    @Override
    @Transactional
    public List<AdminDTO> getAdminList(Integer LangNo) throws IOException {

        List<Admin> adminList = adminDao.getAdminList();
        List<AdminDTO> adminDtoList = new ArrayList<>();
        CommonHelperImpl utils = new CommonHelperImpl();
        if (adminList != null) {
            for (Admin admin : adminList) {
                AdminDTO adminDto = mapper.map(admin, AdminDTO.class);
                adminDto.setAdmRoleTypeDsc(utils.getFlgValBasedOnLang(GlobalConstants.ADMIN_ROLE_TYPE, adminDto.getAdmRoleType(), LangNo));
                adminDtoList.add(adminDto);
            }

        }
        return adminDtoList;
    }

    @Override
    @Transactional
    public boolean addAdmin(AdminDTO adminDto) {
        Admin admin = adminDao.getAdminByCode(adminDto.getAdmCode());
        if (admin != null) {
            return false;
        }
        adminDao.addAdmin(mapper.map(adminDto, Admin.class));
        return true;
    }

    @Override
    @Transactional
    public Integer deleteAdmin(Integer adminNo) {
         Admin admin = adminDao.getAdminByAdmNo(adminNo);
        if (admin == null) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        return adminDao.deleteAdmin(adminNo);
    }

    @Override
    @Transactional
    public Integer updateAdmin(AdminDTO adminDto) {

        Admin admin2 = adminDao.getAdminByAdmNo(adminDto.getAdmNo());
        if (admin2 == null) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        Admin admin = adminDao.getAdminByCode(adminDto.getAdmCode());
        if (admin != null && !Objects.equals(admin.getAdmNo(), adminDto.getAdmNo())) {
            return ResponseMessages.RECORD_IS_EXISTS_CODE;
        }
        adminDao.updateAdmin(mapper.map(adminDto, Admin.class));
        return 1;
    }

    @Override
    @Transactional
    public void updateAdminPic(AdminDTO adminDto) {
        adminDao.updateAdminPic(mapper.map(adminDto, Admin.class));
    }

    @Override
    @Transactional
    public AdminDTO getAdminByCode(String adminCode) {
        Admin admin = adminDao.getAdminByCode(adminCode);
        if (admin == null) {
            return null;
        }
        return mapper.map(admin, AdminDTO.class);
    }

    @Override
    @Transactional
    public AdminDTO getAdminByAdmNo(Integer adminNo) {
        Admin admin = adminDao.getAdminByAdmNo(adminNo);
        if (admin == null) {
            return null;
        }
        return mapper.map(admin, AdminDTO.class);
    }

    @Override
    @Transactional
    public void updateAdminPassword(AdminDTO adminDto) {
        adminDao.updateAdminPic(mapper.map(adminDto, Admin.class));
    }

    @Override
    @Transactional
    public boolean checkAdminISExists(Integer adminNo) {
        return adminDao.checkAdminISExists(adminNo);
    }

}
