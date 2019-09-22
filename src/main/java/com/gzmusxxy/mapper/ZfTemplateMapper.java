package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.ZfTemplate;
import java.util.List;

public interface ZfTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZfTemplate record);

    ZfTemplate selectByPrimaryKey(Integer id);

    List<ZfTemplate> selectAll();

    int updateByPrimaryKey(ZfTemplate record);
}