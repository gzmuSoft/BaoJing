package com.gzmusxxy.entity;

import lombok.Data;

import java.util.Date;
@Data
public class XjhbInformation {
    private Integer id;

    private Integer projectId;

    private Double outlay;

    private Date startTime;

    private Date endTime;

    private String oneCardSolution;

    private String otherProofName;

    private String otherProof;

    private String projectApplication;

    private Integer personId;

    private Date createTime;

    private Byte status;


}
