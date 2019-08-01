package com.gzmusxxy.entity;

import java.util.Date;

public class XjhbPerson {
    private Integer id;

    private String name;

    private String identity;

    private String telphone;

    private String village;

    private Date createTime;

    private String oneCardSolution;

    private String idCardFront;

    private String idCardReverse;

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

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOneCardSolution() {
        return oneCardSolution;
    }

    public void setOneCardSolution(String oneCardSolution) {
        this.oneCardSolution = oneCardSolution;
    }

    public String getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront;
    }

    public String getIdCardReverse() {
        return idCardReverse;
    }

    public void setIdCardReverse(String idCardReverse) {
        this.idCardReverse = idCardReverse;
    }
}