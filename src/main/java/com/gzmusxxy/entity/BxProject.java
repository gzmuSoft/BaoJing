package com.gzmusxxy.entity;

import lombok.Data;

import java.util.Date;
@Data
public class BxProject {
    private Integer id;

    private String name;

    private Date startTime;

    private Date endTime;

    private Float cost;


}
