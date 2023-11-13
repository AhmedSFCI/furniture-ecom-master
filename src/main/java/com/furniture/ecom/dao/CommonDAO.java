/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import java.util.List;

/**
 *
 * @author HP
 */
public interface CommonDAO<E>{

    public E add(E entity);

    public E update(E entity);

    public void remove(Integer id);

    public E findById(Integer id);

    public List<E> findList();
}
