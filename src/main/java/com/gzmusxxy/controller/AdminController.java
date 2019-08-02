package com.gzmusxxy.controller;

import com.gzmusxxy.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台管理
 */
@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "login")
    public String login() {
        return "admin/login";
    }

    @ResponseBody
    @RequestMapping(value = "verify")
    public String verify(String username,String password) {
        System.out.println("zm"+username+password);
        int flag = adminService.verify(username,password);
        if (flag == 1){
            return "登录成功！";
        }else
            return "登录失败";
    }
}
