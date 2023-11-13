/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._dto.ProductDTO;
import com.furniture.ecom._model.Pagging;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author HP
 */
public interface GeneralDAO {

    /*
    get the message map based on list on numbers
     */
    public Map<Integer, String> getMessage(Set<Integer> msgNoList, Integer langNo);

    /*
    used to get the field name based on lang no
     */
    public Map<String, String> getFieldText(Integer langNo, Set<String> fieldNameList);

    /*
    check that the specified id has this attribute value
     */
    public boolean checkUniquePrmtrBasedOnId(String tblNm, String colNm, String attrValue, String idNm, String idValue);

    /*
    check string prmtr is exists 
     */
    public boolean checkUniquePrmtr(String tblNm, String colNm, String attrValue);

    public String getErrorMsg(Integer msgNo, Integer langNo);

    public String getFieldName(String field, Integer langNo);

    /*
    check if the value is exists where id values != entered value
     */
    public boolean checkRecordIfExists(String tblNm, String colNm, String colVal, String idNm, Integer id);

    public Integer getNextOrderNo(String tblNm);

    public Long getCountOfRecords(String tableName, String whrQry);

    public String getTextFromId(String tableNm, String colTxtNm, String colIdNm, Integer id, Integer langNo);

    public <T> Map<T, String> getTextListFromId(String tableNm, String colTxtNm, String colIdNm, Set<T> idList, Integer langNo);

    public boolean checkIdIsExists(String tblNm, String colNm, Integer idValue, String whrQry);

    public <T> T getColumnValue(String tableName, String columnName, String PKName, T PKValue, String qryWhere);

    public List<ProductDTO> getMaterialProductsInfo(Integer fldVal, String whrQry, Integer langNo);

    public List<ProductDTO> getProductsInfo(String fldNm, Integer fldVal, String whrQry, Integer numberOFReconds, Integer langNo);

    public List<ProductDTO> getProductsPagingList(Pagging paging, Integer langNo);

    public Object getAttributeValue(String tableName, String colNm, String pkNm, Integer pkVal);

    public <T> List<T> checkIdListAreExist(String tableName, String columnName, Set<T> list, String whrQry);

    public Map<Integer, String> getCustomersNm(Set<Integer> list);
    
    public Object getMaxValue(String tblNm , String colNm);
}
