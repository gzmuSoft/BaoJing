package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.BxInsurance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BxInsuranceService {

    int insert(BxInsurance record);

    BxInsurance selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(BxInsurance record);

    PageInfo<BxInsurance> selectAuditByNameLike(String name, Integer poverty, Integer pageNumber);

    PageInfo<BxInsurance> selectClaimsByNameLike(String name, Integer pageNumber);

    PageInfo<BxInsurance> selectCheckByNameLike(String name, Integer pageNumber);

    BxInsurance selectClaimsById(Integer id);

    /**
     * 查询指定人购买的保险信息
     *
     * @param personId
     * @return
     */
    PageInfo<BxInsurance> selectInsuranceByPersonId(Integer personId, Integer pageNumber);

    /**
     * 根据用户的id以及保险的id号，更新保险信息
     *
     * @param record
     * @return
     */
    int updateByIdAndPersonId(BxInsurance record);

    /**
     * 通过用户id和保险id更新保险缴费状态
     * @param personId
     * @param id
     * @return
     */
    int updatePayCostByIdAndPersonId(Integer personId, Integer id,Byte payCost);
}
