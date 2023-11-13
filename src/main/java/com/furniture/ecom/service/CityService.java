/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.CityDTO;
import java.util.List;

/**
 *
 * @author Mostafa Atta
 */
public interface CityService {

    public List<CityDTO> getCityList();

    public CityDTO getCityByNo(Integer cityNo);

    public Integer addCity(CityDTO cityDto);

    public Integer editCity(CityDTO cityDto);

    public Integer deleteCity(Integer cityNo);

    public boolean checkIFCityIsExists(Integer cityNo);
}
