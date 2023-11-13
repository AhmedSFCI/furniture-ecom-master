/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.FinishDTO;
import com.furniture.ecom._entity.Finish;
import java.util.List;

/**
 *
 * @author HP
 */
public interface FinishDAO {

    public List<Finish> getFinishList();

    public Finish getFinishByNo(Integer finishNo);

    public Integer deleteFinish(Integer finishNo);

    public Finish addFinish(Finish finish);

    public void updateFinish(FinishDTO finishDto);

    public boolean checkFinishISExists(Integer finishNo);

}
