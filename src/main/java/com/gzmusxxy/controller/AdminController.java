package com.gzmusxxy.controller;

import com.gzmusxxy.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public String verify(String username, String password) {
        Integer id = adminService.verify(username,password);
        if (id > 0){
            return id.toString();
        }else
            return "输入的账号或密码错误！";
    }

    @RequestMapping(value = "/index")
    public String index(Integer id, HttpSession session) {
        session.setAttribute("admin",adminService.selectByPrimaryKey(id));
        return "admin/index";
    }

    @RequestMapping(value = "/xjhbProject")
    public String xjhbProject(Model model, Integer id) {
        model.addAttribute("admin",adminService.selectByPrimaryKey(id));
        return "admin/xjhb_project";
    }
}
