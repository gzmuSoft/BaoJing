package com.gzmusxxy.common;

/**
 * 用于显示结果的实体类
 */
public class JsonResult {
    /**
     * 显示结果的代码 1为成功 0 为失败
     */
    private Integer Code;
    /**
     * 具体的结果提示
     */
    private String Result;

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }
}
