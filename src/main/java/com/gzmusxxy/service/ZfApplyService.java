package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.ZfApply;

import java.util.List;

public interface ZfApplyService {
    int deleteByPrimaryKey(Integer id);

    int insert(ZfApply record);

    ZfApply selectByPrimaryKey(Integer id);

    List<ZfApply> selectAll();

    int updateByPrimaryKey(ZfApply record);

    PageInfo<ZfApply> selectByNameLike(String name, Integer pageNumber);

    PageInfo<ZfApply> selectCompleteByNameLike(String name, Integer pageNumber);
}
