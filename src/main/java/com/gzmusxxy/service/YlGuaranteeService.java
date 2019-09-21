package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.YlGuarantee;

import java.util.List;

public interface YlGuaranteeService {

    PageInfo<YlGuarantee> selectByNameCostLike(String name, Integer pageNumber);

    PageInfo<YlGuarantee> selectByNameLike(String name, Integer pageNumber);

    PageInfo<YlGuarantee> selectAccountByNameLike(String name, Integer pageNumber);

    List<YlGuarantee> selectAllByStatus(Integer status);

    int deleteByPrimaryKey(Integer id);

    int insert(YlGuarantee record);

    YlGuarantee selectByPrimaryKey(Integer id);

    List<YlGuarantee> selectAll();

    int updateByPrimaryKey(YlGuarantee record);

    Integer updateStatus(Integer front, Integer after);
}
