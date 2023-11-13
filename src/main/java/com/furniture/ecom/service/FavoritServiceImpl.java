/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom.dao.FavoritDAO;
import com.furniture.ecom._dto.FavoritDTO;
import com.furniture.ecom._dto.ProductDTO;
import com.furniture.ecom._entity.Favorit;
import com.furniture.ecom._entity.FavoritPK;
import com.furniture.ecom.dao.GeneralDAO;
import com.furniture.ecom._model.ResponseMessages;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author HP
 */
@Service
public class FavoritServiceImpl implements FavoritService {

    @Autowired
    FavoritDAO favoritDao;
    @Autowired
    GeneralDAO gnrDao;

    @Override
    @Transactional
    public List<ProductDTO> getFavoritProductList(Integer customerNo, Integer langNo) {
        return favoritDao.getFavoritProductList(customerNo, langNo);
    }

    @Override
    @Transactional
    public List<FavoritDTO> getCustomerFavoritList(Integer customerNo) {
        List<Favorit> favorits = favoritDao.getCustomerFavoritList(customerNo);
        List<FavoritDTO> favoritDtoList = new ArrayList();
        if (favorits != null && !favorits.isEmpty()) {
            favorits.stream().map(favorit -> {
                FavoritDTO favoritDto = new FavoritDTO();
                favoritDto.setCustNo(favorit.getFavoritPK().getCustNo());
                favoritDto.setProdNo(favorit.getFavoritPK().getProdNo());
                return favoritDto;
            }).forEachOrdered(favoritDto -> {
                favoritDtoList.add(favoritDto);
            });
        }
        return favoritDtoList;
    }

    @Override
    @Transactional
    public Integer addFavorit(FavoritDTO favorit) {
        Favorit favrt = new Favorit();
        favrt.setFavoritPK(new FavoritPK(favorit.getCustNo(), favorit.getProdNo()));
        favrt.setAddedDate(new Date());
        if (favoritDao.checkFavoritIsExists(favrt)) {
            return ResponseMessages.RECORD_IS_EXISTS_CODE;
        }
        favoritDao.addFavorit(favrt);
        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    @Transactional
    public Integer deleteFavorit(FavoritDTO favorit) {
        Favorit favrt = new Favorit();
        favrt.setFavoritPK(new FavoritPK(favorit.getCustNo(), favorit.getProdNo()));
        if (!favoritDao.checkFavoritIsExists(favrt)) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        favoritDao.deleteFavorit(favrt);
        return ResponseMessages.SUCCESS_OPERATION;
    }

    @Override
    @Transactional
    public Integer deleteAllCustomerFavorit(Integer customerNo) {
        if (!gnrDao.checkIdIsExists("Favorit", " favoritPK.custNo ", customerNo,null)) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        favoritDao.deleteAllCustomerFavorit(customerNo);
        return ResponseMessages.SUCCESS_OPERATION;
    }
}
