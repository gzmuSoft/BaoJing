package com.gzmusxxy.controller;

import com.gzmusxxy.entity.XjhbInformation;
import com.gzmusxxy.entity.XjhbPerson;
import com.gzmusxxy.entity.XjhbProject;
import com.gzmusxxy.service.PovertyService;
import com.gzmusxxy.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 扶贫先建后补
 */
@RequestMapping("/poverty")
@Controller
public class PovertyController {



    @Autowired
    private PovertyService povertyService;
    //从配置文件中获取文件的上传路径
    //@Value("${ws.fileUploadPath}")
    //private String fileUploadPath;

    @RequestMapping(value = {"","/"})
    public String information(HttpSession session) {
        //获取session中的用户openid
        System.out.println(session.getAttribute("openid"));
        return "poverty/information";
    }

    @ResponseBody
    @RequestMapping(value = "/informationOk")
    public String informationOk() {
        return "poverty/user";
    }

    @RequestMapping(value = "/index")
    public String Index() {
        return "poverty/index";
    }

    @RequestMapping(value = "/user")
    public String user() {
        return "poverty/users";
    }

    @ResponseBody
    @RequestMapping(value = "/userMsg")
    public String userMsg(XjhbPerson xjhbPerson,HttpSession session) {
        //从session中获得用户的openid,如果为空则用户未登录
        //暂时未进行登录验证
        String openId = session.getAttribute("openid").toString();
        xjhbPerson.setOpenid(openId);
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
    @RequestMapping(value = "/upProjectInformation")
    public String upProjectInformation(@RequestParam("file") MultipartFile file, String backPath, String frontPath, String type) {
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

    @RequestMapping(value = "/usershen")
    public String findProjectName(Model model){

        List<XjhbProject> list = povertyService.findXjhbProject();
        model.addAttribute("projectList",list);
        return "poverty/usershen";
    }


    /**
     * 保存申请信息
     * */
    @RequestMapping(value = "/saveInformation")
    public String saveInformation(XjhbInformation xjhbInformation){

        Date date = new Date();
        xjhbInformation.setCreateTime(date.toString());
        povertyService.saveInformation(xjhbInformation);
        return "";
    }
}
