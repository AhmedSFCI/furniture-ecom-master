/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.FinishDTO;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author HP
 */
public interface FinishService {

    public Integer addFinish(FinishDTO prodFinishDto);

    public Integer updateFinish(FinishDTO prodFinishDto);

    public List<FinishDTO> getFinishList(Integer langNo) throws IOException;

    public FinishDTO getFinishByNo(Integer finishNo, Integer langNo) throws IOException;

    public Integer deleteFinish(Integer finishNo);

    public boolean checkFinishISExists(Integer finishNo);

}
