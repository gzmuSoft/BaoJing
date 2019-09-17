package com.gzmusxxy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 供销交流平台
 * @Author RAINEROSION
 * @Date 2019/9/17 21:38
 */
@Controller
@RequestMapping("/market")
public class MarketController {

    /**
     * 供销平台首页
     * @return
     */
    @RequestMapping(value = {"","/"})
    public String index(){
        return "market/index";
    }
    /**
     * 登录方法
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(){
        return "market/login";
    }
}
