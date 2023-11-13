/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._helpers;

import com.furniture.ecom._dto.EnumFlgListDTO;
import com.furniture.ecom._dto.ImageDTO;
import com.furniture.ecom._dto.ProductFilterDTO;
import com.furniture.ecom._dto.RequestDTO;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 */
public interface CommonHelper {

    public String getNameBasedOnLangNo(String nameAr, String nameEn, Integer langNo);

    public List<EnumFlgListDTO> getFlgList(String listName, Integer langNo) throws IOException;

    public boolean checkFlgValIsExists(String listName, String flgVal);

    public String getFlgValBasedOnLang(String listName, String flgVal, Integer langNo) throws IOException;

    public List<String> getEnumValues(String enumName);

    public String buildEmail(String userName, String verifiedCode, String flag);

    public void sendEmail(String usrMail, String email);

    public Date addHoursToDate(Date date, int hours);

    public Integer getLngNoFromRequest(RequestDTO req);

    public String getProductPagingColumnName(String pagingType);

  //  public String getProductListQryFromFilter(ProductFilterDTO filter);

    public String uploadFile(ImageDTO imageDto) throws IOException, Exception;

}
