package com.gzmusxxy.entity;

import lombok.Data;

import java.util.Date;
@Data
public class XjhbInformation {
    private Integer id;

    private String name;

    private Double outlay;

    private Date startTime;

    private Date endTime;

    private String oneCardSolution;

    private String proofMaterial;

    private String otherProof;

    private String projectApplication;

    private Integer personId;

    private String createTime;


}