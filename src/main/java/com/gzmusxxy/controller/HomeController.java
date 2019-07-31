package com.gzmusxxy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/information")
    public String information() {
        return "information";
    }

    @RequestMapping(value = "/")
    public String Index() {
        return "index";
    }

    @RequestMapping(value = "/user")
    public String User() {
        return "users";
    }
}
