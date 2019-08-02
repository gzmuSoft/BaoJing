package com.gzmusxxy.controller;

import com.gzmusxxy.entity.XjhbPerson;
import com.gzmusxxy.service.PovertyService;
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
 * 扶贫先建后补
 */
@RequestMapping("/poverty")
@Controller
public class PovertyController {

    @Autowired
    private PovertyService povertyService;

    @RequestMapping(value = "")
    public String information() {
        return "poverty/information";
    }

    @ResponseBody
    @RequestMapping(value = "/informationOk")
    public String informationOk() {
        return "poverty/user";
    }

//    @RequestMapping(value = "/index")
//    public String Index() {
//        return "poverty/index";
//    }

    @RequestMapping(value = "/user")
    public String user() {
        return "poverty/users";
    }

    @RequestMapping(value = "/userMsg")
    public String userMsg(XjhbPerson xjhbPerson) {

        Date data = new Date();
        xjhbPerson.setCreateTime(data);
        xjhbPerson.setIdCardFront(xjhbPerson.getIdCardFront());
        xjhbPerson.setIdCardReverse(xjhbPerson.getIdCardReverse());
        povertyService.insert(xjhbPerson);
        System.out.println("地址="+xjhbPerson.getIdCardFront()+"..."+xjhbPerson.getIdCardReverse());
        System.out.println(xjhbPerson);
        return "poverty/usershen";
    }

    @ResponseBody
    @RequestMapping(value = "/upload")
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

    @ResponseBody
    @RequestMapping(value= "/download")
    public String download(String path,String name,HttpServletRequest request, HttpServletResponse response){
        FileUtil.downloadFile(path,name,request,response);
        return "";
    }
}
