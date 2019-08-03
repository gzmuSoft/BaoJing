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

    @RequestMapping(value = "/login")
    public String login() {
        return "admin/login";
    }

    @ResponseBody
    @RequestMapping(value = "/verify")
    public String verify(String username,String password) {
        System.out.println("um"+username+password);
        Integer id = adminService.verify(username,password);
        if (id > 0){
            return id.toString();
        }else
            return "输入的账号或密码错误！";
    }

    @RequestMapping(value = "/main")
    public String main(Integer id) {
        System.out.println("id="+id);
        return "admin/main";
    }
}
