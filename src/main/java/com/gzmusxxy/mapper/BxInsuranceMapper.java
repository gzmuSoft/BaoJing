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

    List<BxInsurance> selectClaimsByNameLike(String name, String personName, String startTime, String endTime);

    List<BxInsurance> selectCheckByNameLike(String name, String personName, String idCard,Integer cost);

    BxInsurance selectClaimsById(Integer id);

    List<BxInsurance> selectInsuranceByPersonId(Integer personId);

    int updateByIdAndPersonId(BxInsurance record);

    int updatePayCostByIdAndPersonId(@Param("personId") Integer personId, @Param("id") Integer id,@Param("payCost") Byte payCost);
}