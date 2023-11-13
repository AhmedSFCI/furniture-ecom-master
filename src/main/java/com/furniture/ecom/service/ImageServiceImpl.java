/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.ImageDTO;
import com.furniture.ecom._entity.Image;
import com.furniture.ecom.dao.ImageDAO;
import com.furniture.ecom._model.ResponseMessages;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author HP
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageDAO imageDao;
    @Autowired
    ModelMapper mapper;

    @Override
    @Transactional
    public void addImages(List<ImageDTO> imageList) {
        List<Image> images = new ArrayList<>();
        for (ImageDTO imgDto : imageList) {
            images.add(mapper.map(imgDto, Image.class));
        }
        imageDao.addImages(images);
    }

    @Override
    @Transactional
    public Integer deleteImage(Integer imageNo) {
        if (imageDao.checkImageIsExists(imageNo)) {
            return imageDao.deleteImage(imageNo);
        }
        return ResponseMessages.NOT_FOUND_RECORD_CODE;
    }

    @Override
    @Transactional
    public Integer updateImage(ImageDTO image) {
        if (imageDao.checkImageIsExists(image.getImgNo())) {
            return imageDao.updateImage(image);
        }
        return ResponseMessages.NOT_FOUND_RECORD_CODE;
    }

    @Override
    @Transactional
    public List<ImageDTO> getImages(String imgType, Integer itemNo) {
        List<Image> images = imageDao.getImages(imgType, itemNo);
        List<ImageDTO> imagesDto = new ArrayList();
        if (images != null) {
            for (Image img : images) {
                imagesDto.add(mapper.map(img, ImageDTO.class));
            }
        }
        return imagesDto;
    }

    @Override
    @Transactional
    public ImageDTO getImageByType(String imgType, Integer itemNo) {
        List<Image> images = imageDao.getImages(imgType, itemNo);
        if (images != null && images.size() != 0) {
            return mapper.map(images.get(0), ImageDTO.class);
        }
        return null;
    }

    @Override
    @Transactional
    public List<ImageDTO> getSpecificNoOfImage(String imgType, Integer itemNo, Integer numberOfImg) {
        List<Image> images = imageDao.getSpecificNoOfImage(imgType, itemNo,numberOfImg);
        List<ImageDTO> imagesDto = new ArrayList();
        if (images != null) {
            for (Image img : images) {
                imagesDto.add(mapper.map(img, ImageDTO.class));
            }
        }
        return imagesDto;
    }

}
