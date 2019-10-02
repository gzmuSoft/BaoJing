package com.gzmusxxy.entity;

public class ZfPhoto {
    private Integer id;

    private String photoPathFront;

    private String photoPathCenter;

    private String photoPathAfter;

    private Integer applyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhotoPathFront() {
        return photoPathFront;
    }

    public void setPhotoPathFront(String photoPathFront) {
        this.photoPathFront = photoPathFront;
    }

    public String getPhotoPathCenter() {
        return photoPathCenter;
    }

    public void setPhotoPathCenter(String photoPathCenter) {
        this.photoPathCenter = photoPathCenter;
    }

    public String getPhotoPathAfter() {
        return photoPathAfter;
    }

    public void setPhotoPathAfter(String photoPathAfter) {
        this.photoPathAfter = photoPathAfter;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }
}