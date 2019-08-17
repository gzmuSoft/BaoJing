package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.BxInsurance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BxInsuranceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BxInsurance record);

    BxInsurance selectByPrimaryKey(Integer id);

    List<BxInsurance> selectAll();

    int updateByPrimaryKey(BxInsurance record);

    List<BxInsurance> selectAuditByNameLike(String name, Integer povertyA, Integer povertyB);

    List<BxInsurance> selectClaimsByNameLike(String name);

    List<BxInsurance> selectCheckByNameLike(String name);

    BxInsurance selectClaimsById(Integer id);

    List<BxInsurance> selectInsuranceByPersonId(Integer personId);

    int updateStatusByPersonIdAndId(@Param("status") Integer status, @Param("personId") Integer personId, @Param("id") Integer id);

    int updateByIdAndPersonId(BxInsurance record);
}