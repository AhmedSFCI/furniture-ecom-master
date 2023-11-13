/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.StyleDTO;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author HP
 */
public interface StyleService {

    public Integer addStyle(StyleDTO prodStyleDto);

    public Integer updateStyle(StyleDTO prodStyleDto);

    public List<StyleDTO> getStyleList(Integer langNo) throws IOException;

    public StyleDTO getStyleByNo(Integer styleNo, Integer langNo) throws IOException;

    public Integer deleteStyle(Integer styleNo);

    public boolean checkStyleISExists(Integer styleNo);
}
