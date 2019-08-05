package com.gzmusxxy.controller;

import com.gzmusxxy.entity.XjhbPerson;
import com.gzmusxxy.service.XjhbPersonService;
import com.gzmusxxy.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Author: yxf
 * @Date: 2019/8/5 17:51
 * @Version 1.0
 */
@RequestMapping("/person")
@Controller
public class XjhbPersonController {

    @Autowired
    XjhbPersonService xjhbPersonService;

    /**
    * @File : XjhbPersonController.java
    * @Description : 上传身份证正反面图片
    * @Param [file, backPath, frontPath, type]
    * @Return java.lang.String
    * @Author fx
    * @Date : 2019/8/6 0:03
    */

    @ResponseBody
    @RequestMapping(value = "/upIdCard")
    public String upload(@RequestParam("file") MultipartFile file, String backPath, String frontPath, String type) {
        if (backPath != null && frontPath != null) {
            return FileUtil.saveFile(file,null, type);
        }else {
            if (backPath != null ) {
                return FileUtil.saveFile(file,backPath,type);
            }else{
                return FileUtil.saveFile(file,frontPath,type);}
        }
    }

    /**
    * @File : XjhbPersonController.java
    * @Description :
    * @Param [xjhbPerson, session]
    * @Return java.lang.String
    * @Author yxf
    * @Date : 2019/8/6 0:01
    */

    @ResponseBody
    @RequestMapping(value = "/userMsg")
    public String userMsg(XjhbPerson xjhbPerson, HttpSession session) {
        //从session中获得用户的openid,如果为空则用户未登录
        //暂时未进行登录验证
        String openId = session.getAttribute("openid").toString();
        xjhbPerson.setOpenid(openId);
        Date data = new Date();
        xjhbPerson.setCreateTime(data);
        xjhbPerson.setIdCardFront(xjhbPerson.getIdCardFront());
        xjhbPerson.setIdCardReverse(xjhbPerson.getIdCardReverse());
        xjhbPersonService.insert(xjhbPerson);
        System.out.println("地址="+xjhbPerson.getIdCardFront()+"..."+xjhbPerson.getIdCardReverse());
        System.out.println(xjhbPerson);
        return "poverty/usershen";
    }

}
