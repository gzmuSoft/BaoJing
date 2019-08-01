package com.gzmusxxy.controller;

import com.gzmusxxy.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/information")
    public String information() {
        return "information";
    }

    @RequestMapping(value = "/")
    public String Index() {
        return "index";
    }

    @RequestMapping(value = "/user")
    public String user() {
        return "users";
    }

    @RequestMapping(value = "/userMsg")
    public String userMsg(String name) {
        System.out.println("name="+name);
        return "usershen";
    }

    @ResponseBody
    @RequestMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file, String backPath, String frontPath, String type) {
        if (backPath != null && frontPath != null) {
            return FileUtil.saveFile(file,null, type);
        }else {
            if (backPath != null ) {
                return FileUtil.saveFile(file,backPath,type);
            }else
                return FileUtil.saveFile(file,frontPath,type);
        }
    }

    @ResponseBody
    @RequestMapping(value= "/download")
    public String download(String path,String name,HttpServletRequest request, HttpServletResponse response){
        FileUtil.downloadFile(path,name,request,response);
        return "";
    }
}
