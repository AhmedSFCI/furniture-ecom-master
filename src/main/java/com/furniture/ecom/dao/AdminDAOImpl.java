/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._entity.Admin;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HP
 */
@Repository
public class AdminDAOImpl implements AdminDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Admin> getAdminList() {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "FROM Admin";
        Query query = session.createQuery(HQL);
        List<Admin> adminList = query.getResultList();
        return adminList;
    }

    @Override
    public void addAdmin(Admin admin) {
        Session session = sessionFactory.getCurrentSession();
        session.save(admin);
    }

    @Override
    public int deleteAdmin(Integer adminNo) {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "DELETE FROM Admin WHERE admNo = :adminNo";
        Query query = session.createQuery(HQL);
        query.setParameter("adminNo", adminNo);
        return query.executeUpdate();
    }

    @Override
    public void updateAdmin(Admin admin) {
        Session session = sessionFactory.getCurrentSession();
        Admin PersistEntity = session.get(Admin.class, admin.getAdmNo());
        if (PersistEntity != null) {
            PersistEntity.setAdmName(admin.getAdmName());
            PersistEntity.setAdmRoleType(admin.getAdmRoleType());
            PersistEntity.setInActive(admin.getInActive());
            PersistEntity.setAdmCode(admin.getAdmCode());
            if (admin.getInActive() == 1 && PersistEntity.getInActive() == 0) {
                PersistEntity.setInactivDate(new Date());
            }
        }
        session.merge(PersistEntity);

    }

    @Override
    public Admin getAdminByCode(String adminCode) {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "FROM Admin WHERE admCode = :adminCode";
        Query query = session.createQuery(HQL);
        query.setParameter("adminCode", adminCode);
        try {
            Admin admin = (Admin) query.getSingleResult();
            return admin;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void updateAdminPassword(Admin admin) {
        Session session = sessionFactory.getCurrentSession();
        Admin PersistEntity = session.get(Admin.class, admin.getAdmNo());
        if (PersistEntity != null) {
            PersistEntity.setAdmPassword(admin.getAdmPassword());
        }
        session.merge(PersistEntity);
    }

    @Override
    public boolean checkAdminISExists(Integer adminNo) {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "FROM Admin WHERE admNo = :adminNo";
        Query query = session.createQuery(HQL);
        query.setParameter("adminNo", adminNo);
        try {
            List<Admin> adminList = query.getResultList();
            if (adminList == null || adminList.size() == 0) {
                return false;
            }
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public Admin getAdminByAdmNo(Integer admNo) {
        Session session = sessionFactory.getCurrentSession();
        String HQL = "FROM Admin WHERE admNo = :admNo";
        Query query = session.createQuery(HQL);
        query.setParameter("admNo", admNo);
        try {
            Admin admin = (Admin) query.getSingleResult();
            if (admin != null) {
                return admin;
            }
            return null;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void updateAdminPic(Admin admin) {
        Session session = sessionFactory.getCurrentSession();
        Admin PersistEntity = session.get(Admin.class, admin.getAdmNo());
        if (PersistEntity != null) {
            PersistEntity.setAdmPic(admin.getAdmPic());
        }
        session.merge(PersistEntity);
    }

}
