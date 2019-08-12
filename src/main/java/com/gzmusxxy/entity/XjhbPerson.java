package com.gzmusxxy.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class XjhbPerson {
    private Integer id;

    private String name;

    private String identity;

    private String telphone;

    private String village;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private String oneCardSolution;

    private String idCardFront;

    private String idCardReverse;

    private String openid;

    private Byte poverty;

    private String povertyProve;


}
