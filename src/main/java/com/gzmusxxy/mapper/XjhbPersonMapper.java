package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.XjhbPerson;
import java.util.List;

public interface XjhbPersonMapper {
    int insert(XjhbPerson record);

    List<XjhbPerson> selectAll();
}