package com.gzmusxxy.entity;

import lombok.Data;

@Data
public class JyApply {
    private Integer id;

    private String openId;

    private Integer studentId;
    //1待审核 2审核成功 3审核失败
    private Byte status;
//--------没有的字段---------
    private String name;
    //1.男 2 女
    private Integer sex;

    private String identity;

    private String familyId;

    private String poorId;

    private String village;

    private String group;
}