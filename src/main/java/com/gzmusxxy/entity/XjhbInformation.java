package com.gzmusxxy.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class XjhbInformation {
    private Integer id;

    private Integer projectId;

    private Double outlay;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private String oneCardSolution;

    private String otherProofName;

    private String otherProof;

    private String projectApplication;

    private String projectApplicationName;

    private Integer personId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private Byte status;


}
