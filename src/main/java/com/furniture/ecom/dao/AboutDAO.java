/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._entity.About;
import java.util.List;

/**
 *
 * @author HP
 */
public interface AboutDAO {
    
    public List<About> getAboutList();

    public About getAboutByNo(Integer aboutNo);

    public void addAbout(About about);

    public void editAbout(About about);

    public int deleteAbout(Integer aboutNo);

    public boolean checkIFAboutIsExists(Integer aboutNo);

    public List<About> getEnabledAboutList();
}
