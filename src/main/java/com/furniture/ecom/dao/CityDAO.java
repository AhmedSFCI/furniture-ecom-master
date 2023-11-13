/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._entity.City;
import java.util.List;

/**
 *
 * @author Mostafa Atta
 */
public interface CityDAO {

    public List<City> getCityList();

    public City getCityByNo(Integer cityNo);

    public void addCity(City city);

    public void editCity(City city);

    public int deleteCity(Integer cityNo);

    public boolean checkIFCityIsExists(Integer cityNo);

}
