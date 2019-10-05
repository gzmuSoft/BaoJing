package com.gzmusxxy.entity;

import lombok.Data;

@Data
public class JyStudent {
    private Integer id;

    private String name;
    //1.男 2 女
    private Integer sex;

    private String identity;

    private String familyId;

    private String poorId;

    private String village;

    private String group;
}