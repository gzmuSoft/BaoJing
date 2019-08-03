package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.XjhbPerson;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public interface XjhbPersonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(XjhbPerson record);

    XjhbPerson selectByPrimaryKey(Integer id);

    List<XjhbPerson> selectAll();

    int updateByPrimaryKey(XjhbPerson record);
}
