/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.CustomerDTO;
import com.furniture.ecom._dto.ErrorDTO;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;

/**
 *
 * @author HP
 */
public interface GeneralService {

//    public String getMessage(Set<Integer> msgNoList, Integer langNo);
//
//    public String getFieldText(Integer langNo, Set<String> fieldNameList);
    public List<ErrorDTO> getErrorMessages(Set<ConstraintViolation<Object>> errors, Integer LangNo);

    public String getErrorMsg(Integer msgNo, Integer LangNo);

    public String getErrorMsg(Integer msgNo, String fieldName, Integer LangNo);

    public List<ErrorDTO> getErrorMsg(Map<String, Integer> errorsList, Integer LangNo);

    public Long getCountOfRecords(String tableName, String whrQry);

    public void sendVerificationEmail(CustomerDTO customer);

    public String getTextFromId(String tableNm, String colTxtNm, String colIdNm, Integer id, Integer langNo);

    public Map<Integer, String> getTextListFromId(String tableNm, String colTxtNm, String colIdNm, Set<Integer> idList, Integer langNo);

    public boolean checkIdIsExists(String tblNm, String colNm, Integer idValue);
    
    public <T> List<T> checkIdListAreExist(String tableName, String columnName, Set<T> list, String whrQry);

    public Object getMaxValue(String tblNm , String colNm);
    
}
