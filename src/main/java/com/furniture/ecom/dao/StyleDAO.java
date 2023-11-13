/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.StyleDTO;
import com.furniture.ecom._entity.Style;
import java.util.List;

/**
 *
 * @author HP
 */
public interface StyleDAO {

    public List<Style> getStyleList();

    public Style getStyleByNo(Integer styleNo);

    public Integer deleteStyle(Integer styleNo);

    public Style addStyle(Style style);

    public void updateStyle(StyleDTO styleDto);

    public boolean checkStyleISExists(Integer styleNo);
}
