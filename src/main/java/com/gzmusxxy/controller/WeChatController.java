package com.gzmusxxy.controller;

import com.alibaba.fastjson.JSON;
import com.gzmusxxy.util.WxConfig;
import com.gzmusxxy.entity.WeChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 微信登录控制器
 */
@Controller
@RequestMapping("/wechat")
public class WeChatController {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 微信登录重定向，跳转到微信登录页面
     *
     * @return
     */
    @RequestMapping(value = "/redirect")
    public String Index(@RequestParam("state") String state) {
        //获得微信用户登录链接,重定向
        return "redirect:" + WxConfig.getLoginUrl(state);
    }

    /**
     * 微信登录解析
     *
     * @param weChat
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = {"", "/"})
    public String WechatRedirect(WeChat weChat, Model model, HttpSession session) {
        //获取授权代码
        String code = weChat.getCode();
        //String state = weChat.getState();
        String json = "";
        if (code.trim() != null && !code.trim().equals("")) {
            //判断code是否为空，不为空则获取用户信息
            ResponseEntity<String> results = restTemplate.exchange(WxConfig.getAccessTokenUrl(code), HttpMethod.GET, null, String.class);
            json = results.getBody();
            //将获得的json数据转换为map
            Map<String, Object> parse = JSON.parseObject(json, Map.class);
            //判断是否存在键值，存在则接口返回错误
            if (parse.containsKey("errcode")) {
                String errmsg = parse.get("errcode").toString() + parse.get("errmsg").toString();
                model.addAttribute("msg", errmsg);
                return "wechat/msg";
            }
            //成功获取用户openid
            String openId = parse.get("openid").toString();
            //将用户openid保存到session
            session.setAttribute("openid", openId);
            //回到首页
            String uri = null;
            if (weChat.getState().equals("insurance")) {
                uri = "/insurance";
            }
            if (weChat.getState().equals("poverty")) {
                uri = "/poverty/apply";
            }
            return "redirect:" + uri;
        } else {
            model.addAttribute("msg", "登录失败，请重试！");
            return "wechat/msg";
        }
    }

    /**
     * 微信接口验证方法
     *
     * @return
     */
    @RequestMapping(value = "MP_verify_spcLemLRmu9e3Md7.txt")
    public String verify() {
        return "wechat/verify";
    }

    /**
     * 设置用于测试的openid
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/demo")
    public String setTestSession(Model model, HttpSession session) {
        //获得access token
        //WeChatAccessTokenUtil instance = WeChatAccessTokenUtil.getInstance();
        //System.out.println(instance.getAccess_token());
        //WeChatUtil.sendBusinessNoticeMsg("oP1se1IxKwG6Su0y6K4Q-_3pkN_Y", "RLDS你好！！", "信息提交", "你的审核信息通过", "如有问题请联系我们！联系电话xxxxxxxxxxx","");
        //设置用于测试的session
        session.setAttribute("openid", "oP1se1IxKwG6Su0y6K4Q-_3pkN_Y");
        model.addAttribute("msg", "设置的openid为：" + session.getAttribute("openid"));
        return "wechat/msg";
    }

}
