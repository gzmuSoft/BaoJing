package com.gzmusxxy.entity;

import lombok.Data;

import java.util.Date;

@Data
public class YlGuarantee {
    private Integer id;

    private Integer personId;

    private Byte payCost;

    private String dataZip;

    private String card;

    private String remark;
    //1待验证 2待验成功 3验证失败 4审核中 5审核失败 6审核通过（待转帐）7.已转帐
    private Byte status;

    private Date applicationTime;
}