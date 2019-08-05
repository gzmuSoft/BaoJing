package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.XjhbProject;
import java.util.List;

public interface XjhbProjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(XjhbProject record);

    XjhbProject selectByPrimaryKey(Integer id);

    List<XjhbProject> selectAll();

    int updateByPrimaryKey(XjhbProject record);

    List<XjhbProject> selectProjectByNameLike(String name);
}