/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.service;

import com.furniture.ecom._dto.PolicyDTO;
import java.util.List;

/**
 *
 * @author HP
 */
public interface PolicyService {
    public List<PolicyDTO> getPolicyList();

    public PolicyDTO getPolicyByNo(Integer policyNo);

    public Integer addPolicy(PolicyDTO policyDto);

    public Integer editPolicy(PolicyDTO policyDto);

    public Integer deletePolicy(Integer policyNo);

    public boolean checkIFPolicyIsExists(Integer policyNo);

    public List<PolicyDTO> getEnabledPolicyList();
}
