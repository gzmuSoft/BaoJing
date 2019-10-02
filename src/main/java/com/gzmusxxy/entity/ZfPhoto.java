package com.gzmusxxy.entity;

import lombok.Data;

@Data
public class ZfPhoto {
    private Integer id;
    //不存在字段
    private String name;
    //不存在字段
    private String identity;
    //不存在字段
    private String telphone;

    private String photoPathFront;

    private String photoPathCenter;

    private String photoPathAfter;

    private Integer applyId;

    private Integer isUpload;

    private Integer personId;
    //选择的模版(选的时候存ID即可)
    private Integer templateId;
    //（用户上传的）申请表
    private String templatePath;
    //压缩危房照片
    private String housePhotosPath;
    //施工要求
    private String constructionPath;
    //1.审核中 2.审核通过 3.审核失败 4.施工前 5施工中 6施工完成 7.照片通过 8申请验收 9验收失败（重写回到2）10线下 11验收通过(待发补助）12.已发钱
    private Byte status;
}