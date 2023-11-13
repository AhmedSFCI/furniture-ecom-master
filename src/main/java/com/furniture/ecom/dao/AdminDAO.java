/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._entity.Admin;
import java.util.List;

/**
 *
 * @author HP
 */
public interface AdminDAO {

    public List<Admin> getAdminList();

    public void addAdmin(Admin admin);

    public int deleteAdmin(Integer adminNo);

    public void updateAdmin(Admin admin);

    public void updateAdminPic(Admin admin);

    public Admin getAdminByCode(String adminCode);

    public Admin getAdminByAdmNo(Integer adminNo);

    public void updateAdminPassword(Admin admin);

    public boolean checkAdminISExists(Integer adminNo);
}
