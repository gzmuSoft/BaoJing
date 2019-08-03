package com.gzmusxxy.service.impl;

import com.gzmusxxy.entity.Admin;
import com.gzmusxxy.mapper.AdminMapper;
import com.gzmusxxy.service.AdminService;
import com.gzmusxxy.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper admimMapper;

    @Override
    public int verify(String username, String password) {
        Admin admin = admimMapper.selectByUsername(username);
        if (username != null && !username.equals("")){
            if (password != null && !password.equals("")){
                if (EncryptUtil.me.Base64Decode(admin.getPassword()).equals(password)){
                    return admin.getId();
                }
            }
        }
        return 0;
    }
}
