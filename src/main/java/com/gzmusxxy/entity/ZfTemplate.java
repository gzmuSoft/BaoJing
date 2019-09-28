package com.gzmusxxy.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ZfTemplate {
    private Integer id;
    //暂时不用
    private String name;
    //申请表名字
    private String templateName;
    //申请表路径
    private String templatePath;

    private Date createTime;
}