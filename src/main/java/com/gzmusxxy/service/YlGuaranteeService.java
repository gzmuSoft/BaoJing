package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.YlGuarantee;

import java.util.List;

public interface YlGuaranteeService {

    PageInfo<YlGuarantee> selectByNameCostLike(String name, Integer pageNumber);

    int deleteByPrimaryKey(Integer id);

    int insert(YlGuarantee record);

    YlGuarantee selectByPrimaryKey(Integer id);

    List<YlGuarantee> selectAll();

    int updateByPrimaryKey(YlGuarantee record);
}
