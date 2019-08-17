package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.BxProject;
import java.util.List;

public interface BxProjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BxProject record);

    BxProject selectByPrimaryKey(Integer id);

    List<BxProject> selectAll();

    List<BxProject> selectEffective();

    int updateByPrimaryKey(BxProject record);

    List<BxProject> selectProjectByNameLike(String name);
}