package com.gzmusxxy.controller;

import com.gzmusxxy.annotation.IsLogin;
import com.gzmusxxy.entity.XjhbInformation;
import com.gzmusxxy.entity.XjhbPerson;
import com.gzmusxxy.entity.XjhbProject;
import com.gzmusxxy.service.XjhbInformationService;
import com.gzmusxxy.service.XjhbPersonService;
import com.gzmusxxy.service.XjhbProjectService;
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
    private XjhbInformationService xjhbInformationService;

    @Autowired
    private XjhbProjectService xjhbProjectService;

    @Autowired
    private XjhbPersonService xjhbPersonService;

    @IsLogin
    @RequestMapping(value = {"","/"})
    public String information(HttpSession session) {
        //获取session中的用户openid
        System.out.println(session.getAttribute("openid"));
        return "poverty/information";
    }
    @RequestMapping(value = "/index")
    public String index() {
        return "poverty/index";
    }

    @RequestMapping(value = "/user")
    public String user() {
        return "poverty/users";
    }

    @RequestMapping(value = "/usershen")
    public String usershen() {
        return "poverty/usershen";
    }

    @RequestMapping(value = "/information")
    public String information() {
        return "poverty/information";
    }

    /**
     * @File : PovertyController.java
     * @Description : 查询项目信息
     * @Param [model]
     * @Return java.lang.String
     * @Author yxf
     * @Date : 2019/8/6 0:15
     */
    @RequestMapping(value = "/findProjectName")
    public String findProjectName(Model model){
        List<XjhbProject> list = xjhbProjectService.selectAll();
        model.addAttribute("projectList",list);
        return "poverty/usershen";
    }


    /**
     * @File : PovertyController.java
     * @Description : 上传身份证正反面图片
     * @Param [file, backPath, frontPath, type]
     * @Return java.lang.String
     * @Author fx
     * @Date : 2019/8/6 0:03
     */

    @ResponseBody
    @RequestMapping(value = "/upIdCard")
    public String upIdCard(@RequestParam("file") MultipartFile file, String backPath, String frontPath, String type) {
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
     * @File : PovertyController.java
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
     * @File : PovertyController.java
     * @Description : 对于填写申请时对申请书的保存
     * @Param [file, projectPath, type]
     * @Return java.lang.String
     * @Author yxf
     * @Date : 2019/8/6 0:06
     */
    @ResponseBody
    @RequestMapping(value = "/upProjectBook")
    public String upProjectBook(@RequestParam("file") MultipartFile file, String projectPath, String type) {
        return "";
    }

    /**
     * @File : PovertyController.java
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
    public String downProjectBook(String path, String name, HttpServletRequest request, HttpServletResponse response){
        FileUtil.downloadFile(path,name,request,response);
        return "";
    }
}
