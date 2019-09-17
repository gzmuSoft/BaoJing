package com.gzmusxxy.config;

import com.gzmusxxy.annotation.IsLogin;
import com.gzmusxxy.annotation.MarketLogin;
import com.gzmusxxy.entity.IndustryUser;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器：扫描是否有注解
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            String[] split = hm.getBeanType().toString().split("\\.");
            String controller = split[split.length - 1];
            //System.out.println("当前执行的控制器是：" + controller);
            //判断方法上是否有注解
            IsLogin isLogin = hm.getMethodAnnotation(IsLogin.class);
            MarketLogin marketLogin = hm.getMethodAnnotation(MarketLogin.class);
            //若有注解判断是否微信登录
            if (isLogin != null) {
                //存在注解则判断登录章台
                String openid = (String) request.getSession().getAttribute("openid");
                if (openid != null && !openid.trim().equals("")) {
                    return true;
                } else {
                    //若未登录重定向到登陆页面
                    //判断当前执行的控制器是那个
                    if(controller.equals("InsuranceController")){
                        //保险
                        response.sendRedirect("/wechat/redirect?state=insurance");
                    }else if(controller.equals("PovertyController")){
                        //先建后补
                        response.sendRedirect("/wechat/redirect?state=poverty");
                    }
                    return false;
                }
            }else if(marketLogin != null){
                //获取session
                IndustryUser user = (IndustryUser) request.getSession().getAttribute("user");
                //判断是否登录
                if(user != null){
                    return true;
                }else{
                    //如果没有登录则返回登录界面
                    response.sendRedirect("/market/login");
                    return false;
                }
            }else {
                return true;
            }
        } else {
            return false;
        }
    }
}
