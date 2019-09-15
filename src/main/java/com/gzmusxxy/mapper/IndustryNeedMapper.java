package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.IndustryNeed;
import java.util.List;

public interface IndustryNeedMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IndustryNeed record);

    IndustryNeed selectByPrimaryKey(Integer id);

    List<IndustryNeed> selectAll();

    int updateByPrimaryKey(IndustryNeed record);
}