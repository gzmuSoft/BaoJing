package com.gzmusxxy.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 多彩报京：报京文苑
 */
@Data
public class QueArticle {
    private Integer id;

    private String name;

    private String html;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}