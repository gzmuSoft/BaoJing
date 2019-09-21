package com.gzmusxxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.IndustryNeed;
import com.gzmusxxy.mapper.IndustryNeedMapper;
import com.gzmusxxy.service.IndustryNeedService;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 供销系统：需求
 * @Author RAINEROSION
 * @Date 2019/9/19 22:35
 */
@Service
public class IndustryNeedServiceImpl implements IndustryNeedService {

    @Autowired
    private IndustryNeedMapper industryNeedMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return industryNeedMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(IndustryNeed record) {
        return industryNeedMapper.insert(record);
    }

    @Override
    public IndustryNeed selectByPrimaryKey(Integer id) {
        return industryNeedMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<IndustryNeed> selectAll() {
        return industryNeedMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(IndustryNeed record) {
        return industryNeedMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo<IndustryNeed> selectByType(Integer type, Integer pageNumber) {
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<IndustryNeed> industryNeeds = industryNeedMapper.selectByType(type);
        return new PageInfo<>(industryNeeds);
    }

    @Override
    public int selectCountByUserIdAndType(Integer userId, Integer type) {
        return industryNeedMapper.selectCountByUserIdAndType(userId,type);
    }

    @Override
    public int updateReadNumber(Integer id) {
        return industryNeedMapper.updateReadNumber(id);
    }
}
