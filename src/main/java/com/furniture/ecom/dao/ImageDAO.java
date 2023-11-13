/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.ImageDTO;
import com.furniture.ecom._entity.Image;
import java.util.List;

/**
 *
 * @author HP
 */
public interface ImageDAO {

    public List<Image> getImages(String imgType, Integer itemNo);

    public void addImages(List<Image> imageList);

    public Integer deleteImage(Integer imageNo);
    
    public boolean checkImageIsExists(Integer imageNo);
    
     public Integer updateImage(ImageDTO image);
     
     public List<Image> getSpecificNoOfImage(String imgType,Integer itemNo , Integer numberOfImg);
    
}
