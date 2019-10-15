package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.IndustryNeed;

import java.util.List;

/**
 * @Description 供销系统：需求
 * @Author RAINEROSION
 * @Date 2019/9/19 22:33
 */
public interface IndustryNeedService {
    int deleteByPrimaryKey(Integer id);

    int insert(IndustryNeed record);

    IndustryNeed selectByPrimaryKey(Integer id);

    List<IndustryNeed> selectAll();

    int updateByPrimaryKey(IndustryNeed record);

    PageInfo<IndustryNeed> selectByType(Integer type,Integer pageNumber);

    int selectCountByUserIdAndType(Integer userId,Integer type);

    int updateReadNumber(Integer id);

    int updateById(IndustryNeed record);
}
