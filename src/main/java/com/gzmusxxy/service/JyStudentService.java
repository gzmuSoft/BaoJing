package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.JyStudent;

import java.util.List;

public interface JyStudentService {
    int deleteByPrimaryKey(Integer id);

    int insert(JyStudent record);

    JyStudent selectByPrimaryKey(Integer id);

    List<JyStudent> selectAll();

    int updateByPrimaryKey(JyStudent record);

    PageInfo<JyStudent> selectByNameLike(String name, Integer pageNumber);

    JyStudent selectByIdentity(String identity);
}
