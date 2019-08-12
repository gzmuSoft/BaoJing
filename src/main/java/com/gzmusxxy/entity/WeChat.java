package com.gzmusxxy.entity;

import lombok.Data;

@Data
public class WeChat {
    /**
     * 微信回调地址中的code
     * code作为换取access_token的票据
     * 每次用户授权带上的code将不一样
     * code只能使用一次，5分钟未被使用自动过期。
     */
    private String code;
    /**
     * 回调地址中的state参数
     * 重定向后会带上state参数
     * 开发者可以填写a-zA-Z0-9的参数值，最多128字节
     */
    private String state;
    /**
     * 获取到的微信accessToken凭证
     */
    private String access_token;
    /**
     * 授权令牌有效期 单位：秒
     */
    private Long expires_in;
    /**
     * 接口返回的错误代码
     */
    private Integer errcode;
    /**
     * 接口返回的错误信息
     */
    private String errmsg;
    /**
     * 消息推送结果id
     */
    private String msgid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    @Override
    public String toString() {
        return "WeChat{" +
                "code='" + code + '\'' +
                ", state='" + state + '\'' +
                ", access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                ", errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                ", msgid='" + msgid + '\'' +
                '}';
    }
}
