package com.gzmusxxy.controller;

import com.gzmusxxy.entity.XjhbInformation;
import com.gzmusxxy.entity.XjhbProject;
import com.gzmusxxy.service.XjhbProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @Author: yxf
 * @Date: 2019/8/5 17:51
 * @Version 1.0
 */
@RequestMapping("/project")
@Controller
public class XjhbProjectController      {
    @Autowired
    XjhbProjectService xjhbProjectService;

  /**
  * @File : XjhbProjectController.java
  * @Description : 查询项目信息
  * @Param [model]
  * @Return java.lang.String
  * @Author yxf
  * @Date : 2019/8/6 0:15
  */
    @RequestMapping(value = "/usershen")
    public String findProjectName(Model model){

        List<XjhbProject> list = xjhbProjectService.selectAll();
        model.addAttribute("projectList",list);
        return "poverty/usershen";
    }

}
