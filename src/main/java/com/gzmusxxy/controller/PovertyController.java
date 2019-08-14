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
import java.text.SimpleDateFormat;
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

    /**
     * 主页
     * 根据openId 用户不存在则创建用户
     * @param session session
     * @return poverty/apply
     */
    @IsLogin
    @RequestMapping(value = "/apply")
    public String apply(HttpSession session) {
        String openId =  session.getAttribute("openid").toString();
        if(xjhbPersonService.findPersonByOpenId(openId) == null){
            XjhbPerson person = new XjhbPerson();
            person.setOpenid(openId);
            xjhbPersonService.insert(person);
        }
        return "poverty/apply";
    }

    /**
     * 跳转创建申请页面
     * @return poverty/c_information
     */
    @IsLogin
    @RequestMapping(value = "/cInformation")
    public String cInformation(Model model){
        model.addAttribute("projects",xjhbProjectService.selectAll());
        return "poverty/c_information";
    }

    /**
     * 上传文件（通用）
     * @param file  file
     * @param path  path
     * @param type  文件类型
     * @return return
     */
    @ResponseBody
    @RequestMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file,String path, String type) {
        if (path.trim() == null || path.equals("null") || path.equals("")){
            return FileUtil.saveFile(file,null,type);
        }
        return FileUtil.saveFile(file,path,type);
    }

    @IsLogin
    @RequestMapping(value = "/manage")
    public String manage(HttpSession session,Model model) {
        String openId = session.getAttribute("openid").toString();
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(openId);
        List<XjhbInformation> xjhbInformationList= xjhbInformationService.findInfobyPersonId(person.getId());
        model.addAttribute("person",person);
        model.addAttribute("personName",person.getName());
        model.addAttribute("xjhbInformationList",xjhbInformationList);
        return "poverty/manage";
    }


    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/index")
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
    * @File : PovertyController.java
    * @Description : 转发页面连openid
    * @Param
    * @Return
    * @Author yxf
    * @Date : 2019/8/9 0:33
    */
    @IsLogin
    @RequestMapping(value = {"/user"})
    public String user(HttpSession session,Model model) {
        String openId =  session.getAttribute("openid").toString();

        if(xjhbPersonService.findPersonByOpenId(openId) == null){
            XjhbPerson person = new XjhbPerson();
            person.setOpenid(openId);
            xjhbPersonService.insert(person);
            model.addAttribute("person",person);
        }
        else{
            XjhbPerson person = xjhbPersonService.findPersonByOpenId(openId);
            model.addAttribute("person",person);
        }
        model.addAttribute("openId",openId);
        return "poverty/users";
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


    /**
     * 保存申请书信息
     * @param xjhbInformation
     * @param session
     * @return
     */
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/saveInformation")
    public String saveInformation(XjhbInformation xjhbInformation,HttpSession session){
        String openId = session.getAttribute("openid").toString();
        XjhbPerson xjhbPerson = xjhbPersonService.findPersonByOpenId(openId);
        xjhbInformation.setPersonId(xjhbPerson.getId());
        xjhbInformation.setCreateTime(new Date());
        xjhbInformation.setStatus((byte)1);
        System.out.println("创建的申请书"+xjhbInformation);
        xjhbInformationService.saveInformation(xjhbInformation);
        return "success";
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
     * 对项目申请书文件模板的下载
     * @param id 项目id
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value= "/downloadTemplate")
    public String downloadTemplate(int id, HttpServletRequest request, HttpServletResponse response){
        XjhbProject xjhbProject = xjhbProjectService.selectByPrimaryKey(id);
        FileUtil.downloadFile(xjhbProject.getApplicationTemplate(),xjhbProject.getApplicationTemplateName(),request,response);
        return "";
    }

    /**
     * @File : PovertyController.java
     * @Description : 对已经上传的项目申请书文件下载
     * @Param [path, name, request, response]
     * @Return java.lang.String
     * @Author yxf
     * @Date : 2019/8/6 0:08
     */
    @ResponseBody
    @RequestMapping(value= "/downOtherFile")
    public String downOtherFile(Integer id, HttpServletRequest request, HttpServletResponse response){

        XjhbInformation xjhbInformation = xjhbInformationService.selectByPrimaryKey(id);
        String path = xjhbInformation.getOtherProof();
        int start =path.lastIndexOf("\\");
        String name = path.substring(start+1,path.length());
        FileUtil.downloadFile(path,name,request,response);
        return "";
    }
    @ResponseBody
    @RequestMapping(value= "/downXcxpFile")
    public String downXcxpFile(Integer id, HttpServletRequest request, HttpServletResponse response){

        XjhbInformation xjhbInformation = xjhbInformationService.selectByPrimaryKey(id);
        String path = xjhbInformation.getScenePhotos();
        int start = path.lastIndexOf("\\");
        String name = path.substring(start+1,path.length());
        FileUtil.downloadFile(path,name,request,response);
        return "";
    }
    @ResponseBody
    @RequestMapping(value= "/downSqsFile")
    public String downSqsFile(Integer id, HttpServletRequest request, HttpServletResponse response){

        XjhbInformation xjhbInformation = xjhbInformationService.selectByPrimaryKey(id);
        String path = xjhbInformation.getProjectApplication();
        int start = path.lastIndexOf("\\");
        String name = path.substring(start+1,path.length());
        FileUtil.downloadFile(path,name,request,response);
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
        XjhbInformation xjhbInformation =xjhbInformationService.selectByPrimaryKey(findId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = sdf.format(xjhbInformationService.selectByPrimaryKey(findId).getStartTime());
        String endTime = sdf.format(xjhbInformationService.selectByPrimaryKey(findId).getEndTime());
        return xjhbInformation;
    }

    @ResponseBody
    @RequestMapping(value = "/findProject")
    public XjhbProject findProject(String findId){
        return xjhbProjectService.selectByPrimaryKey(Integer.parseInt(findId));
    }
}
