package com.gzmusxxy.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 多彩报京：图片展览
 */
@Data
public class QueImages {
    private Integer id;

    private String name;

    private String path;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}