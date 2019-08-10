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

    @IsLogin
    @RequestMapping(value = "/audit")
    public String audit(HttpSession session,Model model) {
        String openId = session.getAttribute("openid").toString();
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(openId);
        List<XjhbInformation> xjhbInformationList= xjhbInformationService.findInfobyPersonId(person.getId());
        model.addAttribute("person",person);
        model.addAttribute("personName",person.getName());
        model.addAttribute("xjhbInformationList",xjhbInformationList);
        return "poverty/audit";
    }


    /**
    * @File : PovertyController.java
    * @Description : 转发页面连openid
    * @Param
    * @Return
    * @Author yxf
    * @Date : 2019/8/9 0:33
    */

    @IsLogin
    @RequestMapping(value = "/user")
    public String user(HttpSession session,Model model) {
        String openId =  session.getAttribute("openid").toString();
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(openId);
        model.addAttribute("person",person);
        model.addAttribute("openId",openId);
        return "poverty/users";
    }


    /**
     * @File : PovertyController.java
     * @Description : 查询项目信息
     * @Param [model]
     * @Return java.lang.String
     * @Author yxf
     * @Date : 2019/8/6 0:15
     */
    @IsLogin
    @RequestMapping(value = "/usershen")
    public String usershen(Model model,HttpSession session) {
        String openId =  session.getAttribute("openid").toString();
        model.addAttribute("openId",openId);
        List<XjhbProject> list = xjhbProjectService.selectAll();
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(openId);
        model.addAttribute("person",person);
        model.addAttribute("projectList",list);
        return "poverty/usershen";
    }

    @RequestMapping(value = "/information")
    public String information() {
        return "poverty/information";
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

    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/userMsg")
    public String userMsg(XjhbPerson xjhbPerson, HttpSession session) {
        //从session中获得用户的openid,如果为空则用户未登录
        //暂时未进行登录验证
        String openId = session.getAttribute("openid").toString();
        XjhbPerson findXjhbPerson=xjhbPersonService.findPersonByOpenId(openId);
        if(findXjhbPerson.getCreateTime() !=  null){
            xjhbPerson.setId(findXjhbPerson.getId());
            xjhbPerson.setCreateTime(findXjhbPerson.getCreateTime());
            xjhbPersonService.updateByPrimaryKey(xjhbPerson);

        }
        else{
            xjhbPerson.setId(findXjhbPerson.getId());
            xjhbPerson.setOpenid(openId);
            Date data = new Date();
            xjhbPerson.setCreateTime(data);
            xjhbPerson.setIdCardFront(xjhbPerson.getIdCardFront());
            xjhbPerson.setIdCardReverse(xjhbPerson.getIdCardReverse());
            xjhbPersonService.updateByPrimaryKey(xjhbPerson);

        }

        return "poverty/usershen";
    }


    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/informationOk")
    public String informationOk(HttpSession session) {
        String openId =  session.getAttribute("openid").toString();
        XjhbPerson person = new XjhbPerson();
        if(xjhbPersonService.findPersonByOpenId(openId) == null){
            person.setOpenid(openId);
            xjhbPersonService.insert(person);
        }
        return "poverty/user";
    }

    /**
     * 保存申请信息
     * */
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/saveInformation")
    public String saveInformation(XjhbInformation xjhbInformation ){
        System.out.println(xjhbInformation);
        Date date = new Date();
        if (xjhbInformation.getId() == null){

            xjhbInformation.setCreateTime(date);
            xjhbInformation.setStatus((byte)1);
            xjhbInformationService.saveInformation(xjhbInformation);

        }else{
            xjhbInformation.setCreateTime(date);
            xjhbInformation.setStatus((byte)1);
            xjhbInformationService.updateByPrimaryKey(xjhbInformation);
        }


        return "poverty/audit";
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
    public String upProjectBook(@RequestParam("file") MultipartFile file,String projectPath, String type) {

        if (projectPath != null ) {
            return FileUtil.saveFile(file,projectPath, type);
        }else {
            return FileUtil.saveFile(file,null, type);
        }

    }

    /**
     * @File : PovertyController.java
     * @Description : 上传其他材料
     * @Param [file, projectPath, type]
     * @Return java.lang.String
     * @Author yxf
     * @Date : 2019/8/6 0:06
     */
    @ResponseBody
    @RequestMapping(value = "/upOther")
    public String upOther(@RequestParam("file") MultipartFile file,String otherPath, String type) {

        if (otherPath != null ) {
            return FileUtil.saveFile(file,otherPath, type);
        }else {
            return FileUtil.saveFile(file,null, type);
        }

    }

    /**
     * @File : PovertyController.java
     * @Description : 对项目申请书文件模板的下载
     * @Param [path, name, request, response]
     * @Return java.lang.String
     * @Author yxf
     * @Date : 2019/8/6 0:08
     */
    @ResponseBody
    @RequestMapping(value= "/downloadProject")
    public String downloadProject(int id, HttpServletRequest request, HttpServletResponse response){
        XjhbProject xjhbProject = xjhbProjectService.selectByPrimaryKey(id);
        System.out.println(xjhbProject.getApplicationTemplate()+"........"+xjhbProject.getApplicationTemplateName());
        FileUtil.downloadFile(xjhbProject.getApplicationTemplate(),xjhbProject.getApplicationTemplateName(),request,response);
        return "";
    }



    @ResponseBody
    @RequestMapping(value = "/restartInformation")
    public String restartInformation(){
        return "poverty/usershen";
    }

    @ResponseBody
    @RequestMapping(value = "/findInformationById")
    public XjhbInformation findInformationById(int findId){
        return xjhbInformationService.selectByPrimaryKey(findId);
    }

    @ResponseBody
    @RequestMapping(value = "/findProject")
    public XjhbProject findProject(String findId){
        return xjhbProjectService.selectByPrimaryKey(Integer.parseInt(findId));
    }
}
