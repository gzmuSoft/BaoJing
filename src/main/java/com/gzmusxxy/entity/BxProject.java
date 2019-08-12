package com.gzmusxxy.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class BxProject {
    private Integer id;

    private String name;

    private String claimsTemplateName;

    private String claimsTemplate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private Float cost;


}
