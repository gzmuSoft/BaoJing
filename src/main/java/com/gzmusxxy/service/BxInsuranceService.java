package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.BxInsurance;

public interface BxInsuranceService {

    int insert(BxInsurance record);

    BxInsurance selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(BxInsurance record);

    PageInfo<BxInsurance> selectAuditByNameLike(String name, Integer pageNumber);
}
