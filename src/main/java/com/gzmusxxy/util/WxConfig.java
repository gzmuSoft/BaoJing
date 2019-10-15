package com.gzmusxxy.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 微信接口配置文件
 */
public class WxConfig {

    /**
     * 微信公众号开发者ID
     */
    private static final String APPID = "wx71c419649472ae20";
    /**
     * 回调地址
     */
    private static final String REDIRECT_URL = "http://bjglpt.cn/wechat";
    /**
     * 微信登录授权地址
     */
    private static final String HOST = "https://open.weixin.qq.com/connect/oauth2/authorize?";
    /**
     * 开发者密码
     */
    private static final String APPSECRET = "049013d010a4854e3c1997c93efe6c0e";

    /**
     * 微信发送模板消息的url
     */
    private static final String SEND_WECHAT_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    /**
     * 业务通知消息模板id
     */
    public static final String BUSINESS_TEMPLATE_ID = "-EjtHN-aeHw7bnjzoSPagr_cUnbinza8RatN_YYpOrA";

    /**
     * 审核结果通知模板id
     */
    public static final String REVIEW_TEMPLATE_ID = "NCMf1rrKmyrQ318JfPXvgHQt1h3RlZ3bW6Gx233Go_A";


    /**
     * 构建微信登录访问地址
     *
     * @return 返回登录授权地址
     */
    public static String getLoginUrl(String state) {
        String newAppId = "";
        try {
            newAppId = URLEncoder.encode(REDIRECT_URL, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = HOST + "appid=" + APPID + "&redirect_uri=" + newAppId + "&response_type=code&scope=snsapi_userinfo&state="+state;
        return url;
    }

    /**
     * 构建微信获取用户accesstoken和openid的链接
     *
     * @param code
     * @return
     */
    public static String getAccessTokenUrl(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID + "&secret=" + APPSECRET + "&code=" + code + "&grant_type=authorization_code";
        return url;
    }

    /**
     * 构建公众号的全局唯一接口调用凭据地址，与上面的accesstoken不一样
     *
     * @return
     */
    public static String getApiAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET;
        return url;
    }

    /**
     * 拼接发送消息通知用的url链接
     * @return
     */
    public static String getSendWechatMsgUrl() {
        WeChatAccessTokenUtil wechat = WeChatAccessTokenUtil.getInstance();
        String url = SEND_WECHAT_MSG_URL + wechat.getAccess_token();
        return url;
    }
}
