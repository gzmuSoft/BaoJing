package com.gzmusxxy.controller;

import com.alibaba.fastjson.JSON;
import com.gzmusxxy.util.WxConfig;
import com.gzmusxxy.entity.WeChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 微信登录控制器
 */
@Controller
@RequestMapping("/wechat")
public class WeChatController {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 微信登录重定向
     * @return
     */
    @RequestMapping(value = {"","/"})
    public String Index(){
        //获得微信用户登录链接,重定向
        return "redirect:"+ WxConfig.getLoginUrl();
    }
    @RequestMapping(value = "/redirect")
    public String WechatRedirect(WeChat weChat, Model model, HttpSession session){
        String code = weChat.getCode();
        //String state = weChat.getState();
        String json = "";
        if(code.trim() != null && !code.trim().equals("")){
            //判断code是否为空，不为空则获取用户信息
            ResponseEntity<String> results = restTemplate.exchange(WxConfig.getAccessTokenUrl(code), HttpMethod.GET, null, String.class);
            json = results.getBody();
            Map<String,Object> parse = JSON.parseObject(json,Map.class);
            //判断是否存在键值，存在则接口返回错误
            if(parse.containsKey("errcode")){
                String errmsg = parse.get("errcode").toString()+parse.get("errmsg").toString();
                model.addAttribute("msg",errmsg);
                return "poverty/msg";
            }
            //保存用户信息
            String openId = parse.get("openid").toString();
            //将用户openid保存到session
            session.setAttribute("openid",openId);
            //回到首页
            return "poverty/index";
        }else{
            model.addAttribute("msg","登录失败，请重试！");
            return "poverty/msg";
        }
    }
}
