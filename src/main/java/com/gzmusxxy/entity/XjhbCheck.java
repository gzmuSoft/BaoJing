package com.gzmusxxy.entity;

import lombok.Data;

import java.util.Date;
@Data
public class XjhbCheck {
    private Integer id;

    private String materal;

    private String status;

    private Integer projectId;

    private Integer infomationId;

    private Date createTime;


}
