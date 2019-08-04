package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.XjhbProject;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public interface XjhbProjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(XjhbProject record);

    XjhbProject selectByPrimaryKey(Integer id);

    List<XjhbProject> selectAll();

    int updateByPrimaryKey(XjhbProject record);
}
