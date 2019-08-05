package com.gzmusxxy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 扶贫先建后补
 */
@RequestMapping("/poverty")
@Controller
public class PovertyController {



    @RequestMapping(value = {"","/"})
    public String information(HttpSession session) {
        //获取session中的用户openid
        System.out.println(session.getAttribute("openid"));
        return "poverty/information";
    }
    @RequestMapping(value = "/index")
    public String Index() {
        return "poverty/index";
    }

    @RequestMapping(value = "/user")
    public String user() {
        return "poverty/users";
    }

    @RequestMapping(value = "/usershen")
    public String usershen() {
        return "poverty/usershen";
    }

    @RequestMapping(value = "/information")
    public String information() {
        return "poverty/information";
    }










}
