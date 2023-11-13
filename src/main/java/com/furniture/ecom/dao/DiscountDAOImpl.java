/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._entity.Discount;
import com.furniture.ecom._util.ObjectChecker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * @author HP
 */
@Repository
public class DiscountDAOImpl implements DiscountDAO
{

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public Discount getDiscountList()
	{
		Session session = sessionFactory.getCurrentSession();
		String hql = "From Discount ORDER BY discountNo";
		Query query = session.createQuery(hql);
		List<Discount> discount = query.getResultList();
		if (discount != null && !discount.isEmpty())
		{
			return discount.get(0);
		}
		return null;
	}

	@Override
	public Discount getDiscountByNo(Integer discountNo)
	{
		Session session = sessionFactory.getCurrentSession();
		String hql = "From Discount WHERE discountNo = :discountNo";
		javax.persistence.Query query = session.createQuery(hql);
		query.setParameter("discountNo", discountNo);
		try
		{
			return (Discount) query.getSingleResult();
		}
		catch (NoResultException ex)
		{
			ex.getMessage();
			return null;
		}
	}

	@Override
	public void addDiscount(Discount discount)
	{
		Session session = sessionFactory.getCurrentSession();
		session.save(discount);
	}

	@Override
	public Integer deleteDiscount(Integer discountNo)
	{
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM Discount WHERE discountNo = :discountNo";
		javax.persistence.Query query = session.createQuery(hql);
		query.setParameter("discountNo", discountNo);
		return query.executeUpdate();
	}

	@Override
	public void updateDiscount(Discount discount)
	{
		Session session = sessionFactory.getCurrentSession();
		Discount persistEntity = session.get(Discount.class, discount.getDiscountNo());
		if (persistEntity != null)
		{
			persistEntity.setDiscountRate(discount.getDiscountRate());
			persistEntity.setStartDate(discount.getStartDate());
			persistEntity.setExpirDate(discount.getExpirDate());
			persistEntity.setActive(discount.getActive());
			persistEntity.setDiscountMaxValue(discount.getDiscountMaxValue());
			session.merge(persistEntity);
		}
	}

	@Override
	public boolean checkDiscountIsActive(Integer discountNo)
	{
		Session session = sessionFactory.getCurrentSession();
		String hql = "From Discount WHERE discountNo = :no AND active = 1 AND expirDate <= :date AND startDate >= :date";
		Query query = session.createQuery(hql);
		query.setParameter("date", new Date());
		query.setParameter("no", discountNo);
		List<Discount> discount = query.getResultList();
		if (discount != null && !discount.isEmpty())
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean checkDiscountIsExists(Integer discountNo)
	{
		Session session = sessionFactory.getCurrentSession();
		String hql = "From Discount  WHERE discountNo = :no";
		Query query = session.createQuery(hql);
		query.setParameter("no", discountNo);
		List<Discount> discount = query.getResultList();
		if (ObjectChecker.isEmptyOrNull(discount))
			return false;
		return true;
	}

	@Override
	public Discount getActiveDiscount()
	{
		Session session = sessionFactory.getCurrentSession();
		String hql = "From Discount WHERE active = 1 AND expirDate <= :date AND startDate >= :date";
		Query query = session.createQuery(hql);
		query.setParameter("date", new Date());
		List<Discount> discount = query.getResultList();
		if (discount != null && !discount.isEmpty())
		{
			return discount.get(0);
		}
		return null;
	}

	@Override
	public Double getDiscountValue(Integer discountNo)
	{
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT percentage FROM Discount WHERE discountNo = :no";
		Query query = session.createQuery(hql);
		query.setParameter("no", discountNo);
		try
		{
			Object value = query.getSingleResult();
			if (value == null)
			{
				return null;
			}
			return (Double) value;
		}
		catch (NoResultException ex)
		{
			return null;
		}
	}

}
