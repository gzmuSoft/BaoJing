package com.gzmusxxy.entity;

import java.util.Date;

public class YlGuarantee {
    private Integer id;

    private Integer personId;

    private Byte payCost;

    private String dataZip;

    private String card;

    private Date applicationTime;

    private String remark;

    private Byte status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Byte getPayCost() {
        return payCost;
    }

    public void setPayCost(Byte payCost) {
        this.payCost = payCost;
    }

    public String getDataZip() {
        return dataZip;
    }

    public void setDataZip(String dataZip) {
        this.dataZip = dataZip;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Date getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}