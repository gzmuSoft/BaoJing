package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.BxProject;
import java.util.List;

public interface BxProjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BxProject record);

    BxProject selectByPrimaryKey(Integer id);

    List<BxProject> selectAll();

    int updateByPrimaryKey(BxProject record);
}