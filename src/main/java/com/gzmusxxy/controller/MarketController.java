package com.gzmusxxy.controller;

import com.gzmusxxy.annotation.MarketLogin;
import com.gzmusxxy.entity.IndustryUser;
import com.gzmusxxy.service.IndustryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Random;

/**
 * @Description 供销交流平台
 * @Author RAINEROSION
 * @Date 2019/9/17 21:38
 */
@Controller
@RequestMapping("/supply")
public class MarketController {

    @Autowired
    private IndustryUserService industryUserService;

    /**
     * 供销平台首页
     *
     * @return
     */
    @RequestMapping(value = {"", "/"})
    public String index() {
        return "market/index";
    }

    /**
     * 登录方法
     *
     * @return
     */
    @RequestMapping(value = "/login")
    public String login() {
        return "market/login";
    }

    /**
     * 接收用户输入的数据
     * @param session
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/postLogin")
    @ResponseBody
    public String postLogin(HttpSession session, @RequestParam("username") String username,@RequestParam("password") String password) {
        IndustryUser login = industryUserService.login(username.trim(), password.trim());
        if(login == null){
            return "0";
        }else {
            session.setAttribute("user",login);
            return "1";
        }
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/supply/";
    }

    /**
     * 登录表单
     * @return
     */
    @RequestMapping(value = "/register")
    public String register(){
        return "market/register";
    }

    @RequestMapping(value = "/postRegister")
    @ResponseBody
    public String postRegister(@RequestParam("username") String username,@RequestParam("password") String password){
        IndustryUser industryUser = new IndustryUser();
        Random random = new Random();
        industryUser.setName("用户"+String.format("%04d",random.nextInt(9999)));
        industryUser.setUsername(username.trim());
        industryUser.setPassword((DigestUtils.md5DigestAsHex(password.trim().getBytes())));
        industryUser.setRegistertime(new Date());
        IndustryUser industryUser1 = industryUserService.selectUserByUserName(username.trim());
        if(industryUser1 != null){
            return "2";
        }
        int insert = industryUserService.insert(industryUser);
        if(insert >= 1){
            return "1";
        }
        return "0";
    }
    @RequestMapping(value = "/checkName")
    @ResponseBody
    public String checkName(@RequestParam("username") String username){
        IndustryUser industryUser = industryUserService.selectUserByUserName(username.trim());
        if(industryUser != null){
            return "1";
        }else{
            return "0";
        }
    }
}
