/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.ImageDTO;
import com.furniture.ecom._entity.Image;
import java.util.List;

/**
 *
 * @author HP
 */
public interface ImageService {

    public void addImages(List<ImageDTO> imageList);

    public Integer updateImage(ImageDTO image);

    public Integer deleteImage(Integer imageNo);

    public List<ImageDTO> getImages(String imgType, Integer itemNo);

    public ImageDTO getImageByType(String imgType, Integer itemNo);
    
    public List<ImageDTO> getSpecificNoOfImage(String imgType, Integer itemNo, Integer numberOfImg);
}
