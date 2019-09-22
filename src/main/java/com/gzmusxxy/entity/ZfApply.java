package com.gzmusxxy.entity;

import lombok.Data;

@Data
public class ZfApply {
    private Integer id;

    private Integer personId;

    private Integer templateId;

    private String templatePath;

    private String housePhotosPath;

    private String constructionPath;

    private Byte status;
}