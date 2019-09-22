package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.JyApply;

import java.util.List;

public interface JyApplyService {
    int deleteByPrimaryKey(Integer id);

    int insert(JyApply record);

    JyApply selectByPrimaryKey(Integer id);

    List<JyApply> selectAll();

    int updateByPrimaryKey(JyApply record);

    PageInfo<JyApply> selectByNameLike(String name, Integer pageNumber);
}
