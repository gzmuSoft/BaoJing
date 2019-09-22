package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.IndustryUser;
import org.springframework.stereotype.Component;

import java.util.List;
public interface IndustryUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IndustryUser record);

    IndustryUser selectByPrimaryKey(Integer id);

    List<IndustryUser> selectAll();

    int updateByPrimaryKey(IndustryUser record);

    IndustryUser selectUserByUserName(String username);

    int updateNameAndPhoneById(IndustryUser record);

    int updateById(IndustryUser record);
    List<IndustryUser> getUserByName(IndustryUser industryUser);
}