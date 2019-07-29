package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.XjhbApply;
import java.util.List;

public interface XjhbApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(XjhbApply record);

    XjhbApply selectByPrimaryKey(Integer id);

    List<XjhbApply> selectAll();

    int updateByPrimaryKey(XjhbApply record);
}