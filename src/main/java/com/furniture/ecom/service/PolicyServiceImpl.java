/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom.dao.PolicyDAO;
import com.furniture.ecom._dto.PolicyDTO;
import com.furniture.ecom._entity.Policy;
import com.furniture.ecom.dao.GeneralDAO;
import com.furniture.ecom._helpers.CommonValidationHelper;
import com.furniture.ecom._model.ResponseMessages;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    PolicyDAO policyDao;

    @Autowired
    GeneralDAO generalDao;

    @Autowired
    ModelMapper mapper;
    @Autowired
    CommonValidationHelper commonValHelper;

    @Override
    @Transactional
    public List<PolicyDTO> getPolicyList() {
        List<Policy> policyList = policyDao.getPolicyList();
        List<PolicyDTO> policyDtoList = new ArrayList<>();

        if (policyList != null) {
            for (Policy policy : policyList) {
                policyDtoList.add(mapper.map(policy, PolicyDTO.class));
            }
        }
        return policyDtoList;

    }

    @Override
    @Transactional
    public PolicyDTO getPolicyByNo(Integer policyNo) {
        Policy policy = policyDao.getPolicyByNo(policyNo);
        if (policy != null) {
            return mapper.map(policy, PolicyDTO.class);
        }
        return null;
    }

    @Override
    @Transactional
    public Integer addPolicy(PolicyDTO policyDto) {
        if (checkIFPolicyIsExists(policyDto.getPolicyNo())) {
            return ResponseMessages.RECORD_IS_EXISTS_CODE;
        }
        if (generalDao.checkUniquePrmtr("Policy", "policyTxtAr", policyDto.getPolicyTxtAr())) {
            return ResponseMessages.DUPLICATE_RECORD_AR_CODE;
        }
        if (commonValHelper.checkStringValueIsValid(policyDto.getPolicyTxtAr())) {
            if (generalDao.checkUniquePrmtr("Policy", "policyTxtEn", policyDto.getPolicyTxtAr())) {
                return ResponseMessages.DUPLICATE_RECORD_EN_CODE;
            }
        }
        if (policyDto.getOrderNo() == null || policyDto.getOrderNo() <= 0) {
            policyDto.setOrderNo(generalDao.getNextOrderNo("Policy"));
        }
        policyDao.addPolicy(mapper.map(policyDto, Policy.class));
        return 1;
    }

    @Override
    @Transactional
    public Integer editPolicy(PolicyDTO policyDto) {
        if (!policyDao.checkIFPolicyIsExists(policyDto.getPolicyNo())) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        if (generalDao.checkRecordIfExists("Policy", "policyTxtAr", policyDto.getPolicyTxtAr(), "policyNo", policyDto.getPolicyNo())) {
            return ResponseMessages.DUPLICATE_RECORD_AR_CODE;
        }
        if (commonValHelper.checkStringValueIsValid(policyDto.getPolicyTxtAr())) {
            if (generalDao.checkUniquePrmtr("Policy", "policyTxtEn", policyDto.getPolicyTxtAr())) {
                return ResponseMessages.DUPLICATE_RECORD_EN_CODE;
            }
        }
        policyDao.editPolicy(mapper.map(policyDto, Policy.class));
        return 1;
    }

    @Override
    @Transactional
    public Integer deletePolicy(Integer policyNo) {
        if (!policyDao.checkIFPolicyIsExists(policyNo)) {
            return ResponseMessages.NOT_FOUND_RECORD_CODE;
        }
        return policyDao.deletePolicy(policyNo);
    }

    @Override
    @Transactional
    public boolean checkIFPolicyIsExists(Integer policyNo) {
        return policyDao.checkIFPolicyIsExists(policyNo);
    }

    @Override
    @Transactional
    public List<PolicyDTO> getEnabledPolicyList() {
        List<Policy> policyList = policyDao.getPolicyList();
        List<PolicyDTO> policyDtoList = new ArrayList<>();
        if (policyList != null) {
            for (Policy policy : policyList) {
                policyDtoList.add(mapper.map(policy, PolicyDTO.class));
            }
        }
        return policyDtoList;
    }

}
