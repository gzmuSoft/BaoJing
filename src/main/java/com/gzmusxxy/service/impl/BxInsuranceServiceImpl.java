package com.gzmusxxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.BxInsurance;
import com.gzmusxxy.mapper.BxInsuranceMapper;
import com.gzmusxxy.service.BxInsuranceService;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class BxInsuranceServiceImpl implements BxInsuranceService {

    @Autowired
    private BxInsuranceMapper bxInsuranceMapper;

    @Override
    public int insert(BxInsurance record) {
        return bxInsuranceMapper.insert(record);
    }

    @Override
    public BxInsurance selectByPrimaryKey(Integer id) {
        return bxInsuranceMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(BxInsurance record) {
        return bxInsuranceMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo<BxInsurance> selectAuditByNameLike(String name, Integer poverty, Integer pageNumber) {
        if (name != null && name != "") {
            name = "%" + name + "%";
        } else {
            name = "%%";
        }
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<BxInsurance> list;
        if (poverty == 1) {
            list = bxInsuranceMapper.selectAuditByNameLike(name, 1, 1);
        } else if (poverty == 0) {
            list = bxInsuranceMapper.selectAuditByNameLike(name, 0, 0);
        } else {
            list = bxInsuranceMapper.selectAuditByNameLike(name, 1, 0);
        }
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<BxInsurance> selectClaimsByNameLike(String name, Integer pageNumber, String personName, String startTime, String endTime) {
        if (name != null && name != "") {
            name = "%" + name + "%";
        } else {
            name = "%%";
        }
        if (personName != null && personName != "") {
            personName = "%" + personName + "%";
        } else {
            personName = "%%";
        }
        if (endTime != null && !endTime.equals("")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(endTime, pos);
            //获取最大时间
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(strtodate.getTime()), ZoneId.systemDefault());;
            LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
            Date date = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            endTime = formatter1.format(date);
        }
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<BxInsurance> list = bxInsuranceMapper.selectClaimsByNameLike(name, personName,startTime, endTime);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<BxInsurance> selectCheckByNameLike(String name, Integer pageNumber, String personName, String idCard,Integer cost) {
        if (name != null && name != "") {
            name = "%" + name + "%";
        } else {
            name = "%%";
        }
        if (personName != null && personName != "") {
            personName = "%" + personName + "%";
        } else {
            personName = "%%";
        }
        if (idCard != null && idCard != "") {
            idCard = "%" + idCard + "%";
        } else {
            idCard = "%%";
        }
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<BxInsurance> list = bxInsuranceMapper.selectCheckByNameLike(name,personName,idCard,cost);
        return new PageInfo<>(list);
    }

    @Override
    public BxInsurance selectClaimsById(Integer id) {
        return bxInsuranceMapper.selectClaimsById(id);
    }

    @Override
    public List<BxInsurance> selectInsuranceByPersonId(Integer personId) {
        List<BxInsurance> list = bxInsuranceMapper.selectInsuranceByPersonId(personId);
        return list;
    }

    @Override
    public int updateByIdAndPersonId(BxInsurance record) {
        return bxInsuranceMapper.updateByIdAndPersonId(record);
    }

    @Override
    public int updatePayCostByIdAndPersonId(Integer personId, Integer id, Byte payCost) {
        return bxInsuranceMapper.updatePayCostByIdAndPersonId(personId, id, payCost);
    }
}
