package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.XjhbProject;
import java.util.List;

public interface XjhbProjectMapper {
    int insert(XjhbProject record);

    List<XjhbProject> selectAll();
}