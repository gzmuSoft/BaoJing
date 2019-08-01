package com.gzmusxxy.entity;

import java.util.Date;

public class XjhbInformation {
    private Integer id;

    private String name;

    private Double outlay;

    private Date startTime;

    private Date endTime;

    private String oneCardSolution;

    private String otherProof;

    private String projectApplication;

    private Integer personId;

    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getOutlay() {
        return outlay;
    }

    public void setOutlay(Double outlay) {
        this.outlay = outlay;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getOneCardSolution() {
        return oneCardSolution;
    }

    public void setOneCardSolution(String oneCardSolution) {
        this.oneCardSolution = oneCardSolution;
    }

    public String getOtherProof() {
        return otherProof;
    }

    public void setOtherProof(String otherProof) {
        this.otherProof = otherProof;
    }

    public String getProjectApplication() {
        return projectApplication;
    }

    public void setProjectApplication(String projectApplication) {
        this.projectApplication = projectApplication;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}