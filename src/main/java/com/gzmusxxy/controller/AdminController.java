package com.gzmusxxy.controller;

import com.gzmusxxy.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public String verify(String username, String password, HttpSession session) {
        Integer id = adminService.verify(username,password);
        if (id > 0){
            session.setAttribute("admin",adminService.selectByPrimaryKey(id));
            return id.toString();
        }else
            return "输入的账号或密码错误！";
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "admin/index";
    }

    /**
     * 项目管理
     * @param model
     * @return
     */
    @RequestMapping(value = "/xjhbProject")
    public String xjhbProject(Model model){
        return "admin/xjhb_project";
    }

    /**
     * 申请书管理
     * @param model
     * @return
     */
    @RequestMapping(value = "/xjhbApply")
    public String xjhbApply(Model model){
        return "admin/xjhb_apply";
    }

    /**
     * 待验收管理
     * @param model
     * @return
     */
    @RequestMapping(value = "/xjhbCheck")
    public String xjhbCheck(Model model){
        return "admin/xjhb_check";
    }

    /**
     * 验收通过查看
     * @param model
     * @return
     */
    @RequestMapping(value = "/xjhbAdopt")
    public String xjhbAdopt(Model model){
        return "admin/xjhb_adopt";
    }

    /**
     * 管理员信息管理
     * @param model
     * @return
     */
    @RequestMapping(value = "/message")
    public String message(Model model){
        return "admin/admin_message";
    }
}
