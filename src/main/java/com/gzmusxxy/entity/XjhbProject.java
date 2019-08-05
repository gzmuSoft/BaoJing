package com.gzmusxxy.entity;

import lombok.Data;
import java.util.Date;

@Data
public class XjhbProject {
    private Integer id;

    private String projectName;

    private String applicationTemplateName;

    private String applicationTemplate;

    private Date startTime;

    private Date endTime;

    private Integer period;


}
