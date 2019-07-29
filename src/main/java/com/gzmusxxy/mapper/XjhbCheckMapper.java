package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.XjhbCheck;
import java.util.List;

public interface XjhbCheckMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(XjhbCheck record);

    XjhbCheck selectByPrimaryKey(Integer id);

    List<XjhbCheck> selectAll();

    int updateByPrimaryKey(XjhbCheck record);
}