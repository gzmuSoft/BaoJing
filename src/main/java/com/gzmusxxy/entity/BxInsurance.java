package com.gzmusxxy.entity;

import lombok.Data;

@Data
public class BxInsurance {
    private Integer id;

    private Integer projectId;

    private Integer personId;

    private Integer buyNumber;

    private Byte payCost;

    private String affectedPhoto;

    private String affectedPhotoName;

    private String claimsApplication;

    private String claimsApplicationName;

    private Byte status;


}
