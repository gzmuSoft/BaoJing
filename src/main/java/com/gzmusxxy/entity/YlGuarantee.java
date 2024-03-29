package com.gzmusxxy.entity;

import lombok.Data;

import java.util.Date;

@Data
public class YlGuarantee {
    private Integer id;

    private Integer personId;
    /*没有的字段*/
    private String name;
    /*没有的字段*/
    private String identity;

    private Byte payCost;

    private String dataZip;

    private String card;

    private String remark;
    //1待验证 2待验成功 3验证失败 4审核中 5审核失败 6审核通过（等待线下提交资料）7.已提交材料 8.已转帐
    private Byte status;

    private Date applicationTime;
}