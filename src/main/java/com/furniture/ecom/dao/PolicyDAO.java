/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom.dao;

import com.furniture.ecom._entity.Policy;
import java.util.List;
import com.furniture.ecom._entity.Policy;

/**
 *
 * @author mosta
 */
public interface PolicyDAO {

    public List<Policy> getPolicyList();

    public Policy getPolicyByNo(Integer policyNo);

    public void addPolicy(Policy policy);

    public void editPolicy(Policy policy);

    public int deletePolicy(Integer policyNo);

    public boolean checkIFPolicyIsExists(Integer policyNo);

    public List<Policy> getEnabledPolicyList();
}
