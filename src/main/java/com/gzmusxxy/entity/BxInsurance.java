package com.gzmusxxy.entity;

import lombok.Data;

@Data
public class BxInsurance {
    private Integer id;

    private Integer projectId;

    private Integer personId;

    private Integer buyNumber;
    /**没有的字段*/
    private String identity;
    /**没有的字段*/
    private String telphone;
    /**没有的字段*/
    private String personName;
    /**没有的字段*/
    private String projectName;
    /**没有的字段*/
    private Byte poverty;
    /**没有的字段*/
    private String povertyProve;
    /**1已经缴费 0未缴费*/
    private Byte payCost;
    /**受灾照片*/
    private String affectedPhoto;

    private String affectedPhotoName;
    /**理赔申请书*/
    private String claimsApplication;

    private String claimsApplicationName;
    /**1待审核 2审核成功 3审核失败 4理赔申请 5理赔申请通过（待理赔） 6理赔申请失败*/
    private Byte status;


}
