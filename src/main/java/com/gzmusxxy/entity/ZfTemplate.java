package com.gzmusxxy.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ZfTemplate {
    private Integer id;

    private String name;

    private String templateName;

    private String templatePath;

    private Date createTime;
}