package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.JyStudent;
import java.util.List;

public interface JyStudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JyStudent record);

    JyStudent selectByPrimaryKey(Integer id);

    List<JyStudent> selectAll();

    int updateByPrimaryKey(JyStudent record);

    List<JyStudent> selectByNameLike(String name);

    JyStudent selectByIdentity(String identity);
}