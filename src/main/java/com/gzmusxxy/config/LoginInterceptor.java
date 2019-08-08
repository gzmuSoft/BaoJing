package com.gzmusxxy.config;

import com.gzmusxxy.annotation.IsLogin;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器扫描是否有注解
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
            //判断方法上是否有注解
            IsLogin isLogin = hm.getMethodAnnotation(IsLogin.class);
            //System.out.println("isLogin is" + isLogin);
            //若有注解判断是否登录
            if(isLogin != null){
                //存在注解则判断登录章台
                String openid = (String)request.getSession().getAttribute("openid");
                System.out.println("openId="+openid);
                if(openid != null && !openid.trim().equals("")){
                    return true;
                }else{
                    //若未登录重定向到登陆页面
                    response.sendRedirect("/wechat/redirect");
                    return false;
                }
            }else{
                return true;
            }
        }else {
            return false;
        }
    }
}
