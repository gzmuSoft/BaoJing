package com.gzmusxxy.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Bulletin {
    private Integer id;

    private String title;

    private byte[] content;

//    1.先建后补 2.农业保险 3.医疗保障 4 教育 5住房
    private Integer sourceId;

    private Integer createAdminId;

    private Date updateTime;
}