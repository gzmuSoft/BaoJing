package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.IndustryUser;
import java.util.List;

public interface IndustryUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IndustryUser record);

    IndustryUser selectByPrimaryKey(Integer id);

    List<IndustryUser> selectAll();

    int updateByPrimaryKey(IndustryUser record);
}