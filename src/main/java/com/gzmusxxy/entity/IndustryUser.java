package com.gzmusxxy.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class IndustryUser {
    private Integer id;

    private String username;

    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registertime;

    private String phone;

    private String lastip;

    private String name;

    private Integer userType;
}