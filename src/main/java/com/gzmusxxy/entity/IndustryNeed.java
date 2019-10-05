package com.gzmusxxy.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class IndustryNeed {
    private Integer id;

    private Integer type;

    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    private Integer userid;

    private String username;

    private String file;

    private Integer commentNum;
}