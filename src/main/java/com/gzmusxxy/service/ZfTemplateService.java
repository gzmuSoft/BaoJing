package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.ZfTemplate;

import java.util.List;

public interface ZfTemplateService {

    int deleteByPrimaryKey(Integer id);

    int insert(ZfTemplate record);

    ZfTemplate selectByPrimaryKey(Integer id);

    List<ZfTemplate> selectAll();

    int updateByPrimaryKey(ZfTemplate record);

    PageInfo<ZfTemplate> selectByNameLike(String name, Integer pageNumber);
}
