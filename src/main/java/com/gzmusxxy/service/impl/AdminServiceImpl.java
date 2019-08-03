package com.gzmusxxy.service.impl;

import com.gzmusxxy.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public int verify(String useranme, String password) {
        if (useranme.equals("admin") && password.equals("123123")){
            return 1;
        }else{
            return 0;}
    }
}
