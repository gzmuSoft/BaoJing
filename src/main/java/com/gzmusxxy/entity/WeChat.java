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
}
