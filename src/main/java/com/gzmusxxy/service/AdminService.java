package com.gzmusxxy.service;

import com.gzmusxxy.entity.Admin;

import java.util.List;

public interface AdminService {
    int verify(String username,String password);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Admin record);

    Admin selectByRole(Integer role);

    List<String> selectEmailByRole(Integer roleA, Integer roleB);
}
