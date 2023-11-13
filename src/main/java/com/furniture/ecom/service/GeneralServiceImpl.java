/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.CustomerDTO;
import com.furniture.ecom.dao.GeneralDAO;
import com.furniture.ecom._dto.ErrorDTO;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class GeneralServiceImpl implements GeneralService {

    @Autowired
    GeneralDAO generalDao;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Transactional
    public List<ErrorDTO> getErrorMessages(Set<ConstraintViolation<Object>> errors, Integer LangNo) {
        Set<String> fieldSet = new HashSet<>();
        Set<Integer> msgSet = new HashSet<>();
        for (ConstraintViolation<Object> err : errors) {
            fieldSet.add(err.getMessageTemplate().split("-")[1]);
            msgSet.add(Integer.parseInt(err.getMessageTemplate().split("-")[0]));
        }

        Map<String, String> fieldNameMap = generalDao.getFieldText(LangNo, fieldSet);
        Map<Integer, String> msgMap = generalDao.getMessage(msgSet, LangNo);
        List<ErrorDTO> errorsList = new ArrayList<>();
        for (ConstraintViolation<Object> err : errors) {
            String message = msgMap.get(Integer.parseInt(err.getMessageTemplate().split("-")[0]));
            String field = fieldNameMap.get(err.getMessageTemplate().split("-")[1]);
            errorsList.add(new ErrorDTO(err.getPropertyPath().toString(), message.replace("#p", field)));
        }
        return errorsList;
    }

    @Override
    @Transactional
    public String getErrorMsg(Integer msgNo, Integer LangNo) {
        String msgText = generalDao.getErrorMsg(msgNo, LangNo);
        return msgText;
    }

    @Override
    @Transactional
    public List<ErrorDTO> getErrorMsg(Map<String, Integer> errorsList, Integer LangNo) {
        Set<String> fieldNmList = new HashSet<>();
        Map<String, String> errsFldNms = new HashMap();
        if (errorsList != null) {
            errorsList.forEach((t, u) -> {
                if (t.contains("#")) {
                    fieldNmList.add(t.split("#")[0]);
                    errsFldNms.put(t, t.split("#")[0]);
                } else {
                    fieldNmList.add(t);
                    errsFldNms.put(t, t);
                }
            });
        }
        Map<String, String> fieldNameMap = generalDao.getFieldText(LangNo, fieldNmList);
        Map<Integer, String> msgMap = generalDao.getMessage(new HashSet<>(errorsList.values()), LangNo);
        List<ErrorDTO> errors = new ArrayList<>();
        errorsList.forEach((t, u) -> {
            String message = msgMap.get(u);
            String field = fieldNameMap.get(errsFldNms.get(t));
            String dbFldNm = t;
            if (dbFldNm.contains("#")) {
                String[] values = dbFldNm.split("#");
                field += " = " + values[1];
            }
            errors.add(new ErrorDTO(errsFldNms.get(t), message.replace("#p", field)));
        });
        return errors;
    }

    @Override
    @Transactional
    public String getErrorMsg(Integer msgNo, String fieldName, Integer LangNo) {
        String message = generalDao.getErrorMsg(msgNo, LangNo);
        String field = generalDao.getFieldName(fieldName, LangNo);
        if (field == null) {
            field = fieldName;
        }
        message = message.replace("#p", field);
        return message;
    }

    @Override
    @Transactional
    public Long getCountOfRecords(String tableName, String whrQry) {
        return generalDao.getCountOfRecords(tableName, whrQry);
    }

    @Override
    public void sendVerificationEmail(CustomerDTO customer) {
        String subject = "Verify Your Email";
        String senderName = "Furniture";
        String mainContent = "<p> Dear " + customer.getCustName() + "</p>"
                + "<h3>Please Verify Your email with this code : </h3>"
                + "<h4>" + customer.getVerifyCode() + "</h4>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom("furniture.net@gmail.com", senderName);
            helper.setSubject(subject);
            helper.setText(mainContent, true);
            helper.setTo(customer.getCustEmail());
            mailSender.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(GeneralServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GeneralServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    @Transactional
    public String getTextFromId(String tableNm, String colTxtNm, String colIdNm, Integer id, Integer langNo) {
        String txt = generalDao.getTextFromId(tableNm, colTxtNm, colIdNm, id, langNo);
        if (txt != null) {
            return txt;
        }
        return null;
    }

    @Override
    @Transactional
    public Map<Integer, String> getTextListFromId(String tableNm, String colTxtNm, String colIdNm, Set<Integer> idList, Integer langNo) {
        Map<Integer, String> list = generalDao.getTextListFromId(tableNm, colTxtNm, colIdNm, idList, langNo);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    @Transactional
    public boolean checkIdIsExists(String tblNm, String colNm, Integer idValue) {
        return generalDao.checkIdIsExists(tblNm, colNm, idValue, null);
    }

    @Override
    @Transactional
    public <T> List<T> checkIdListAreExist(String tableName, String columnName, Set<T> list, String whrQry) {
        return generalDao.checkIdListAreExist(tableName, columnName, list, whrQry);
    }
    @Override
    @Transactional
    public Object getMaxValue(String tblNm , String colNm){
        return generalDao.getMaxValue(tblNm, colNm);
    }
}
