package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.XjhbProject;
import java.util.List;

public interface XjhbProjectService {

    PageInfo<XjhbProject> selectProjectByNameLike(String name, Integer pageNumber);

    int insert(XjhbProject record);

    int updateByPrimaryKey(XjhbProject record);

    int deleteByPrimaryKey(Integer id);

    XjhbProject selectByPrimaryKey(Integer id);

    List<XjhbProject> selectAll();

    List<XjhbProject> selectEffective();
}
