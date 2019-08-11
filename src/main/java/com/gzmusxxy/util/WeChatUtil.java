package com.gzmusxxy.util;

import com.gzmusxxy.entity.WeChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * 微信接口工具类(单例模式)
 * @author RLDS
 */
public class WeChatUtil {
    /**
     * 单例模式
     */
    private static WeChatUtil instance = null;
    /**
     * 微信授权凭证
     */
    private String access_token;
    /**
     * 到期时间戳
     */
    private Long express_in;

    private RestTemplate restTemplate = new RestTemplate();

    private WeChatUtil(){

    }
    public static WeChatUtil getInstance(){
        //检查实例是否存在
        if(instance == null){
            synchronized (WeChatUtil.class){
                if(instance == null){
                    instance = new WeChatUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 使用接口获取微信公众号的全局唯一接口调用凭据
     * @return
     */
    public boolean setAccessToken(){
        WeChat wechat = this.restTemplate.getForObject(WxConfig.getApiAccessToken(), WeChat.class);
        if(wechat.getAccess_token() != null && !wechat.getAccess_token().trim().equals("")){
            //将凭证设置到已经实例化的实例中
            setAccess_token(wechat.getAccess_token());
            //设置到期时间（时间戳）
            setExpress_in(System.currentTimeMillis() + wechat.getExpires_in());
            return true;
        }else{
            return false;
        }
    }

    public String getAccess_token(){
        //判断access_token是否在有效期内
        if(System.currentTimeMillis() >= getExpress_in()){
            //access_token过期，重新设置access_token
            setAccessToken();
        }
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setExpress_in(Long express_in) {
        this.express_in = express_in;
    }

    public Long getExpress_in() {
        return express_in;
    }
}
