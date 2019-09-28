package com.gzmusxxy.entity;

import lombok.Data;

@Data
public class ZfApply {
    private Integer id;

    private Integer personId;
    //选择的模版(选的时候存ID即可)
    private Integer templateId;
    //（用户上传的）申请表
    private String templatePath;
    //压缩危房照片
    private String housePhotosPath;
    //施工要求
    private String constructionPath;

    private Byte status;
}