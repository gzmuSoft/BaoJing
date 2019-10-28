package com.gzmusxxy.controller;

import com.gzmusxxy.annotation.IsLogin;
import com.gzmusxxy.entity.Bulletin;
import com.gzmusxxy.entity.XjhbInformation;
import com.gzmusxxy.entity.XjhbPerson;
import com.gzmusxxy.entity.XjhbProject;
import com.gzmusxxy.service.*;
import com.gzmusxxy.util.FileUtil;
import com.gzmusxxy.util.MailUtil;
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
import java.io.UnsupportedEncodingException;
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

    @Autowired
    private BulletinService bulletinService;

    @Autowired
    private AdminService adminService;

    /**
     * 主页
     * 根据openId 用户不存在则创建用户
     * @param session session
     * @return poverty/apply
     */
    @IsLogin
    @RequestMapping(value = {"","/apply","/"})
    public String apply(HttpSession session,Model model) {
        String openId =  session.getAttribute("openid").toString();
        if(xjhbPersonService.findPersonByOpenId(openId) == null){
            XjhbPerson person = new XjhbPerson();
            person.setOpenid(openId);
            person.setCreateTime(new Date());
            xjhbPersonService.insert(person);
        }
        Bulletin bulletin = null;
        List<Bulletin> bulletins = bulletinService.selectBySourceId(1);
        if (bulletins.size() > 0) {
            bulletin = bulletins.get(0);
        }
        String content = "";
        if(bulletin == null){
            bulletin = new Bulletin();
        }else {
            if (bulletin.getContent() != null) {
                try {
                    content = new String(bulletin.getContent(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        model.addAttribute("content",content);
        model.addAttribute("bulletin",bulletin);
        return "poverty/apply";
    }

    /**
     * 验证是否可以跳转创建申请页面
     * @return
     */
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/verifyInformation")
    public String verifyInformation(HttpSession session){
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(session.getAttribute("openid").toString());
        if (person.getName() == null || person.getName().equals("")){
            return "no";
        }
        if (person.getIdentity() == null || person.getIdentity().equals("")){
            return "no";
        }
        if (person.getTelphone()== null || person.getTelphone().equals("")){
            return "no";
        }
        if (person.getOneCardSolution() == null || person.getOneCardSolution().equals("")){
            return "no";
        }
        if (person.getIdCardFront() == null || person.getIdCardFront().equals("")){
            return "no";
        }
        if (person.getIdCardReverse() == null || person.getIdCardReverse().equals("")){
            return "no";
        }
        if (person.getVillage() == null || person.getVillage().equals("")) {
            return "no";
        }
        return "yes";
    }

    /**
     * 跳转创建申请页面
     * @return poverty/c_information
     */
    @IsLogin
    @RequestMapping(value = "/information")
    public String information(Model model){
        model.addAttribute("projects",xjhbProjectService.selectEffective());
        model.addAttribute("information",new XjhbInformation());
        return "poverty/information";
    }

    /**
     * 跳转修改申请页面
     * @return poverty/c_information
     */
    @IsLogin
    @RequestMapping(value = "/updateInformation")
    public String updateInformation(Integer id, Model model){
        model.addAttribute("projects",xjhbProjectService.selectAll());
        model.addAttribute("information",xjhbInformationService.selectByPrimaryKey(id));
        return "poverty/information";
    }

    /**
     * 跳转至项目管理
     * @param session
     * @param model
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/manage")
    public String manage(HttpSession session,Model model) {
        List<XjhbInformation> xjhbInformation = xjhbInformationService.selectInformationByOpenId(session.getAttribute("openid").toString());
        model.addAttribute("informationList",xjhbInformation);
        return "poverty/manage";
    }

    /**
     * 跳转至资料管理
     * @param session
     * @param model
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/user")
    public String user(HttpSession session,Model model) {
        String openId =  session.getAttribute("openid").toString();
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(openId);
        model.addAttribute("person",person);
        return "poverty/users";
    }

    /**
     * 上传文件（通用）
     * @param file  file
     * @param path  path
     * @param type  文件类型
     * @return return
     */
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file,String path, String type) {
        if (path == null || path.equals("null") || path.equals("")){
            return FileUtil.saveFile(file,null,type);
        }
        return FileUtil.saveFile(file,path,type);
    }

    /**
     * users页面上传身份证正反面照片
     * @param file
     * @param backPath
     * @param frontPath
     * @param type
     * @return
     */
    @IsLogin
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
     * 修改资料
     * 因为一开始登录时自动创建用户
     * @param xjhbPerson
     * @return
     */
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/update")
    public String update(XjhbPerson xjhbPerson) {
        Integer re = xjhbPersonService.updateByPrimaryKey(xjhbPerson);
        return re.toString();
    }

    /**
     * （审核）
     * 保存申请书信息
     * 如果id为空则创建 否则修改
     * @param xjhbInformation
     * @param session
     * @return
     */
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/saveInformation")
    public String saveInformation(XjhbInformation xjhbInformation,HttpSession session){
        xjhbInformation.setCreateTime(new Date());
        xjhbInformation.setStatus((byte)1);
        if (xjhbInformation.getId() == null){
            String openId = session.getAttribute("openid").toString();
            XjhbPerson xjhbPerson = xjhbPersonService.findPersonByOpenId(openId);
            xjhbInformation.setPersonId(xjhbPerson.getId());
            xjhbInformationService.saveInformation(xjhbInformation);
        }else {
            xjhbInformationService.updateByPrimaryKey(xjhbInformation);
        }
        //发送请求审核邮件
        new Thread(){
            @Override
            public void run() {
                //申请 发送邮件
                List<String> emails = adminService.selectEmailByRole(1,2);
                XjhbPerson xjhbPerson = xjhbPersonService.selectByPrimaryKey(xjhbInformation.getPersonId());
                XjhbProject xjhbProject = xjhbProjectService.selectByPrimaryKey(xjhbInformation.getProjectId());
                for (String email:
                        emails) {
                    if (email != null){
                        MailUtil.sendMail("申请审核",
                                xjhbPerson.getName() + "的" +
                                        xjhbProject.getProjectName() + "项目申请审核，请及时处理。",email
                        );
                    }
                }
                super.run();
            }
        }.start();
        return "success";
    }

    /**
     * 申请验收
     * 跳转至上传现场照片
     * @param id
     * @param model
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/check")
    public String check(Integer id,Model model) {
        model.addAttribute("information",xjhbInformationService.selectByPrimaryKey(id));
        return "poverty/check";
    }

    /**
     * 申请验收保存现场照片
     * 成功跳转至项目管理
     * @param information
     * @return
     */
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/saveCheck")
    public String saveCheck(XjhbInformation information) {
        information.setStatus((byte)4);
        information.setCreateTime(new Date());
        xjhbInformationService.updateByPrimaryKey(information);
        new Thread(){
            @Override
            public void run() {
                //申请验收 发送邮件
                List<String> emails = adminService.selectEmailByRole(1,2);
                XjhbPerson xjhbPerson = xjhbPersonService.selectByPrimaryKey(information.getPersonId());
                XjhbProject xjhbProject = xjhbProjectService.selectByPrimaryKey(information.getProjectId());
                for (String email:
                        emails) {
                    if (email != null){
                        MailUtil.sendMail("申请验收",
                                xjhbPerson.getName() + "的" +
                                        xjhbProject.getProjectName() + "项目申请验收，请及时处理。",email
                                );
                    }
                }
                super.run();
            }
        }.start();
        return "success";
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
     * 通用下载
     * @param name
     * @param path
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value= "/download")
    public String download(String name,String path, HttpServletRequest request, HttpServletResponse response){
        FileUtil.downloadFile(path,name,request,response);
        return "";
    }
}
