package com.gzmusxxy.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WxConfig {
    //微信公众号开发者ID
    public static final String APPID = "wx71c419649472ae20";
    //回调地址
    public static final String REDIRECT_URL = "http://bjglpt.cn/phpapp";

    public static final String HOST = "https://open.weixin.qq.com/connect/oauth2/authorize?";

    public static final String APPSECRET = "";

    //构建微信登录访问地址
    public static String getLoginUrl() {
        String newAppId = "";
        try {
            newAppId = URLEncoder.encode(REDIRECT_URL, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = HOST + "appid=" + APPID + "&redirect_uri=" + newAppId + "&response_type=code&scope=snsapi_userinfo&state=login#wechat_redirect";
        return url;
    }
    //构建微信获取用户accesstoken和openid的链接
    public static String getAccessTokenUrl(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID + "&secret=" + APPSECRET + "&code=" + code + "&grant_type=authorization_code";
        return url;
    }

}
