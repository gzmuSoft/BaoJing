package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.BxProject;

import java.util.List;

public interface BxProjectService {

    int deleteByPrimaryKey(Integer id);

    int insert(BxProject record);

    BxProject selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(BxProject record);

    PageInfo<BxProject> selectProjectByNameLike(String name,Integer pageNumber);

    /**
     * 查询所有的项目信息
     * @return
     */
    List<BxProject> selectAll();
}
