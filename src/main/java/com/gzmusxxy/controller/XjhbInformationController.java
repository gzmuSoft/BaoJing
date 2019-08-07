package com.gzmusxxy.controller;

import com.gzmusxxy.entity.XjhbInformation;
import com.gzmusxxy.service.XjhbInformationService;
import com.gzmusxxy.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Author: yxf
 * @Date: 2019/8/5 17:50
 * @Version 1.0
 */
@RequestMapping("/information")
@Controller
public class XjhbInformationController {

    @Autowired
    XjhbInformationService xjhbInformationService;

    @RequestMapping(value = "")
    public String information() {
        return "poverty/information";
    }

    @ResponseBody
    @RequestMapping(value = "/informationOk")
    public String informationOk() {
        return "poverty/user";
    }

    /**
     * 保存申请信息
     * */
    @ResponseBody
    @RequestMapping(value = "/saveInformation")
    public String saveInformation(XjhbInformation xjhbInformation){

        Date date = new Date();
        xjhbInformation.setCreateTime(date.toString());
        xjhbInformationService.saveInformation(xjhbInformation);
        System.out.println(xjhbInformation);
        return "";
    }

    /**
    * @File : XjhbInformationController.java
    * @Description : 对于填写申请时对申请书的保存
    * @Param [file, projectPath, type]
    * @Return java.lang.String
    * @Author yxf
    * @Date : 2019/8/6 0:06
    */
    @ResponseBody
    @RequestMapping(value = "/upProjectBook")
    public String upProjectInformation(@RequestParam("file") MultipartFile file, String projectPath, String type) {
       return "";
    }

    /**
    * @File : XjhbInformationController.java
    * @Description : 对项目申请书文件模板的下载
    * @Param [path, name, request, response]
    * @Return java.lang.String
    * @Author yxf
    * @Date : 2019/8/6 0:08
    */

    //从配置文件中获取文件的上传路径
    //@Value("${ws.fileUploadPath}")
    //private String fileUploadPath;


    @ResponseBody
    @RequestMapping(value= "/downProjectBook")
    public String download(String path, String name, HttpServletRequest request, HttpServletResponse response){
        FileUtil.downloadFile(path,name,request,response);
        return "";
    }

}
