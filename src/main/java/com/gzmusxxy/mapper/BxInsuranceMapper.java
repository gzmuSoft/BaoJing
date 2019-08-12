package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.BxInsurance;
import java.util.List;

public interface BxInsuranceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BxInsurance record);

    BxInsurance selectByPrimaryKey(Integer id);

    List<BxInsurance> selectAll();

    int updateByPrimaryKey(BxInsurance record);
}