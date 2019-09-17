package com.gzmusxxy.controller;

import com.gzmusxxy.annotation.MarketLogin;
import com.gzmusxxy.entity.IndustryUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @Description 供销交流平台
 * @Author RAINEROSION
 * @Date 2019/9/17 21:38
 */
@Controller
@RequestMapping("/supply")
public class MarketController {

    /**
     * 供销平台首页
     * @return
     */
    @MarketLogin
    @RequestMapping(value = {"","/"})
    public String index(){
        return "market/index";
    }
    /**
     * 登录方法
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(HttpSession session){
        IndustryUser industryUser = new IndustryUser();
        industryUser.setName("xyj");
        industryUser.setPassword("dslfhgouiklsyhgkoldfjgklsd");
        session.setAttribute("user",industryUser);
        return "market/login";
    }
}
