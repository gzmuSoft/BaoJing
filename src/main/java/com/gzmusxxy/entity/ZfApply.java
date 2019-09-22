package com.gzmusxxy.entity;

public class ZfApply {
    private Integer id;

    private Integer personId;

    private Integer templateId;

    private String templatePath;

    private String housePhotosPath;

    private String constructionPath;

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

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getHousePhotosPath() {
        return housePhotosPath;
    }

    public void setHousePhotosPath(String housePhotosPath) {
        this.housePhotosPath = housePhotosPath;
    }

    public String getConstructionPath() {
        return constructionPath;
    }

    public void setConstructionPath(String constructionPath) {
        this.constructionPath = constructionPath;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}