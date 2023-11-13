/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.AdminDTO;
import com.furniture.ecom._entity.Admin;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author HP
 */
public interface AdminService {

    public List<AdminDTO> getAdminList(Integer LangNo) throws IOException;

    public boolean addAdmin(AdminDTO adminDto);

    public Integer deleteAdmin(Integer adminNo);

    public Integer updateAdmin(AdminDTO adminDto);

    public void updateAdminPic(AdminDTO adminDto);

    public AdminDTO getAdminByCode(String adminCode);

    public AdminDTO getAdminByAdmNo(Integer adminNo);

    public void updateAdminPassword(AdminDTO adminDto);

    public boolean checkAdminISExists(Integer adminNo);
    
}
