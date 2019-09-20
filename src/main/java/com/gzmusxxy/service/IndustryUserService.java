package com.gzmusxxy.service;

import com.gzmusxxy.entity.IndustryUser;

import java.util.List;

/**
 * @Description 供销平台 用户service
 * @Author RAINEROSION
 * @Date 2019/9/17 22:54
 */
public interface IndustryUserService {
    int deleteByPrimaryKey(Integer id);

    int insert(IndustryUser record);

    IndustryUser selectByPrimaryKey(Integer id);

    List<IndustryUser> selectAll();

    int updateByPrimaryKey(IndustryUser record);

    IndustryUser login(String username, String password);

    IndustryUser selectUserByUserName(String username);

    int updateNameAndPhoneById(IndustryUser record);
}
