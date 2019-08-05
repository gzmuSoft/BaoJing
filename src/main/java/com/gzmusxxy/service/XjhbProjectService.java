package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.XjhbProject;

public interface XjhbProjectService {

    PageInfo<XjhbProject> selectProjectByNameLike(String name, Integer pageNumber);

    int insert(XjhbProject record);

    int updateByPrimaryKey(XjhbProject record);

    int deleteByPrimaryKey(Integer id);
}
