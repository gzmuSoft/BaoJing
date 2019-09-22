package com.gzmusxxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.IndustryNeed;
import com.gzmusxxy.entity.IndustryUser;
import com.gzmusxxy.mapper.IndustryNeedMapper;
import com.gzmusxxy.mapper.IndustryUserMapper;
import com.gzmusxxy.service.MarketAdminService;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author QFMX
 * @Date 2019/9/21 19:18
 * @Description Service层
 */
@Service
public class MarketAdminServiceImpl implements MarketAdminService {
    @Autowired
    private IndustryUserMapper industryUserMapper;
    @Autowired
    private IndustryNeedMapper industryNeedMapper;

    @Override
    public PageInfo<IndustryUser> getUser(int pageNumber) {
        //PageHelper插件的分页信息：默认8条数据
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        List<IndustryUser> industryUsers = this.industryUserMapper.selectAll();
        return new PageInfo<>(industryUsers);
    }

    @Override
    public PageInfo<IndustryNeed> getNeed(int pageNumber) {
        //PageHelper插件的分页信息：默认8条数据
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        List<IndustryNeed> industryNeeds = this.industryNeedMapper.selectAll();
        return new PageInfo<>(industryNeeds);
    }
    @Override
    public PageInfo<IndustryNeed> getNeedByNameType(int pageNumber, IndustryNeed record) {
        PageHelper.startPage(pageNumber,PageUtil.PAGE_ROW_COUNT);
        List<IndustryNeed> industryNeeds = this.industryNeedMapper.selectByNameType(record);
        return new PageInfo<>(industryNeeds);
    }

    @Override
    public PageInfo<IndustryUser> getUserByName(int pageNumber, IndustryUser user) {
        PageHelper.startPage(pageNumber,PageUtil.PAGE_ROW_COUNT);
        List<IndustryUser> userByName = this.industryUserMapper.getUserByName(user);
        return new PageInfo<>(userByName);
    }
}
