package com.gzmusxxy.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class XjhbInformation {
    private Integer id;

    private Integer projectId;
    /**额外添加的字段*/
    private String projectName;
    private Integer period;
    private Double outlay;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private String scenePhotosName;

    private String scenePhotos;

    private String otherProofName;

    private String otherProof;

    private String projectApplication;

    private String projectApplicationName;

    private Integer personId;
    /**额外添加的字段*/
    private String personName;
    /**额外添加的字段*/
    private String oneCardSolution;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    /**1.未审核 2.审核失败 3.审核通过 4.待验收 5.线下验收 6.验收通过（待转帐）7.验收失败 8.已转帐*/
    private Byte status;


}
