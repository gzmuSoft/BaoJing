package com.gzmusxxy.common;

/**
 * 用于显示结果的实体类
 */
public class JsonResult {
    /**
     * 显示结果的代码 1为成功 0 为失败
     */
    private Integer code;
    /**
     * 具体的结果提示
     */
    private String result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        result = result;
    }
}
