package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.JyApply;
import java.util.List;

public interface JyApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JyApply record);

    JyApply selectByPrimaryKey(Integer id);

    List<JyApply> selectAll();

    int updateByPrimaryKey(JyApply record);

    List<JyApply> selectByNameLike(String name);
}