package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.BxInsurance;

import java.util.List;

public interface BxInsuranceService {

    int insert(BxInsurance record);

    BxInsurance selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(BxInsurance record);

    PageInfo<BxInsurance> selectAuditByNameLike(String name, Integer poverty, Integer pageNumber);

    PageInfo<BxInsurance> selectClaimsByNameLike(String name, Integer pageNumber);

    PageInfo<BxInsurance> selectCheckByNameLike(String name, Integer pageNumber);

    BxInsurance selectClaimsById(Integer id);
}
