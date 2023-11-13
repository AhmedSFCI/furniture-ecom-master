/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.FinishDTO;
import com.furniture.ecom._entity.Finish;
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
public class FinishDAOImpl implements FinishDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Finish> getFinishList() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Finish ORDER BY finishNo";
        Query query = session.createQuery(hql);
        return query.getResultList();
    }

    @Override
    public Finish getFinishByNo(Integer finishNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Finish WHERE finishNo = :finishNo";
        Query query = session.createQuery(hql);
        query.setParameter("finishNo", finishNo);
        try {
            Finish finishuct = (Finish) query.getSingleResult();
            return finishuct;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Integer deleteFinish(Integer finishNo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM Finish WHERE finishNo = :finishNo";
        Query query = session.createQuery(hql);
        query.setParameter("finishNo", finishNo);
        return query.executeUpdate();
    }

    @Override
    public Finish addFinish(Finish finishuct) {
        Session session = sessionFactory.getCurrentSession();
        session.save(finishuct);
        return finishuct;
    }

    @Override
    public void updateFinish(FinishDTO finishuctDto) {
        Session session = sessionFactory.getCurrentSession();
        Finish persistEntity = session.get(Finish.class, finishuctDto.getFinishNo());
        if (persistEntity != null) {
            persistEntity.setFinishNmAr(finishuctDto.getFinishNmAr());
            if (finishuctDto.getFinishNmEn() != null && !finishuctDto.getFinishNmEn().isBlank()) {
                persistEntity.setFinishNmEn(finishuctDto.getFinishNmEn());
            }
            session.merge(persistEntity);
        }
    }

    @Override
    public boolean checkFinishISExists(Integer finishNo) {
        boolean flag = false;
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COALESCE(COUNT (*),0) FROM Finish WHERE finishNo = :finishNo";
        Query query = session.createQuery(hql);
        query.setParameter("finishNo", finishNo);
        try {
            Long count = (Long) query.getSingleResult();

            if (count != null && count > 0) {
                return true;
            }
        } catch (NoResultException ex) {
            return flag;
        }
        return flag;
    }

}
