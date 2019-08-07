package com.gzmusxxy.service;

import com.gzmusxxy.entity.Admin;

public interface AdminService {
    int verify(String username,String password);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Admin record);
}
