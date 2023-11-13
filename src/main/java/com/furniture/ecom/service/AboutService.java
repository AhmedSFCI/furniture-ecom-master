/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.AboutDTO;
import java.util.List;

/**
 *
 * @author HP
 */
public interface AboutService {
    public List<AboutDTO> getAboutList();

    public AboutDTO getAboutByNo(Integer aboutNo);

    public Integer addAbout(AboutDTO aboutDto);

    public Integer editAbout(AboutDTO aboutDto);

    public Integer deleteAbout(Integer aboutNo);

    public boolean checkIFAboutIsExists(Integer aboutNo);

    public List<AboutDTO> getEnabledAboutList();
}
