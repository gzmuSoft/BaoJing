package com.gzmusxxy.controller;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.annotation.IsLogin;
import com.gzmusxxy.common.JsonResult;
import com.gzmusxxy.entity.BxInsurance;
import com.gzmusxxy.entity.BxProject;
import com.gzmusxxy.entity.XjhbPerson;
import com.gzmusxxy.service.BxInsuranceService;
import com.gzmusxxy.service.BxProjectService;
import com.gzmusxxy.service.XjhbPersonService;
import com.gzmusxxy.util.FileUtil;
import com.gzmusxxy.util.PageUtil;
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
 * 保险业务控制器
 */
@Controller
@RequestMapping("/insurance")
public class InsuranceController {

    @Autowired
    private XjhbPersonService xjhbPersonService;
    @Autowired
    private BxProjectService bxProjectService;
    @Autowired
    private BxInsuranceService bxInsuranceService;

    /**
     * 首页
     *
     * @return
     */
    @IsLogin
    @RequestMapping(value = {"", "/"})
    public String index(Model model, HttpSession session) {
        //判断用户是否登录
        String openid = session.getAttribute("openid").toString();
        //通过openId获取用户信息
        XjhbPerson personByOpenId = xjhbPersonService.findPersonByOpenId(openid);
        //判断用户信息是否为空
        if (personByOpenId != null) {
            //再次判断用户的信息收否填写完全
            if (personByOpenId.getIdentity() == null || personByOpenId.getName() == null || personByOpenId.getTelphone() == null || personByOpenId.getOneCardSolution() == null || personByOpenId.getPoverty() == null) {
                //所需要的资料未完全填写，跳转到资料填写界面
                return "redirect:/insurance/user?supplement=true";
            }
        }else{
            //用户不存在创建用户
            XjhbPerson person = new XjhbPerson();
            person.setOpenid(openid);
            person.setCreateTime(new Date());
            xjhbPersonService.insert(person);
        }
        //用户资料填写完整，查询项目资料
        List<BxProject> bxProjects = bxProjectService.selectAll();
        model.addAttribute("list", bxProjects);
        return "insurance/index";
    }

    /**
     * 用于下载项目文件的方法
     *
     * @param projectId
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/download")
    public void downloadTemplate(@RequestParam("id") Integer projectId, HttpServletRequest request, HttpServletResponse response) {
        BxProject bxProject = bxProjectService.selectByPrimaryKey(projectId);
        FileUtil.downloadFile(bxProject.getClaimsTemplate(), bxProject.getClaimsTemplateName(), request, response);
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
    @RequestMapping(value= "/downloadFile")
    public String download(String name,String path, HttpServletRequest request, HttpServletResponse response){
        FileUtil.downloadFile(path,name,request,response);
        return "";
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
    public String upload(@RequestParam("file") MultipartFile file, String path, String type) {
        if (path == null || path.equals("null") || path.equals("")){
            return FileUtil.saveFile(file,null,type);
        }
        return FileUtil.saveFile(file,path,type);
    }

    /**
     * 保存保险资料
     *
     * @return
     */
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/saveInsurance")
    public JsonResult saveInsurance(@RequestParam("projectId") Integer projectId, @RequestParam("number") Integer number, HttpSession session) {
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(session.getAttribute("openid").toString());
        BxInsurance bxInsurance = new BxInsurance();
        //设置默认的状态为待审核
        bxInsurance.setStatus((byte) 1);
        bxInsurance.setProjectId(projectId);
        bxInsurance.setBuyNumber(number);
        bxInsurance.setPersonId(person.getId());
        bxInsurance.setPayCost((byte) 0);
        int insert = bxInsuranceService.insert(bxInsurance);
        JsonResult jsonResult = new JsonResult();
        if (insert <= 0) {
            jsonResult.setCode(0);
            jsonResult.setResult("数据提交失败，请重试！");
            return jsonResult;
        }
        jsonResult.setCode(1);
        jsonResult.setResult("ok");
        return jsonResult;
    }

    /**
     * 显示用户已经提交的保险的信息
     *
     * @param session
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/myInsurance")
    public String myInsurance(@RequestParam("pageNumber") Integer pageNumber, HttpSession session, Model model) {
        String openid = session.getAttribute("openid").toString();
        //通过openid获得用户id并获取购买的保险信息
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(openid);
        //调用方法查询该用户购买的保险
        PageInfo<BxInsurance> bxInsurancePageInfo = bxInsuranceService.selectInsuranceByPersonId(person.getId(), pageNumber);
        model.addAttribute("pageInfo", bxInsurancePageInfo);
        model.addAttribute("pages", PageUtil.getPage(bxInsurancePageInfo.getPages(), pageNumber));
        return "insurance/myInsurance";
    }

    /**
     * 进入保险理赔申请页面
     * @param id
     * @param model
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/applyForClaim")
    public String applyForClaim(@RequestParam("id") Integer id, Model model) {
        BxInsurance bxInsurance = bxInsuranceService.selectByPrimaryKey(id);
        //判断当前状态是否支持保险申请
        if (bxInsurance.getStatus().equals(6) || bxInsurance.getStatus().equals(2)) {
            model.addAttribute("msg", "当前状态不支持理赔申请!");
            return "insurance/msg";
        }
        //判断是否缴费 1：已缴费 0 ：未缴费
        if (bxInsurance.getPayCost() == 1) {
            if (bxInsurance != null) {
                //获得保险的信息
                BxProject bxProject = bxProjectService.selectByPrimaryKey(bxInsurance.getProjectId());
                model.addAttribute("insurance", bxInsurance);
                model.addAttribute("project", bxProject);
            }
            return "insurance/applyForClaim";
        } else {
            //未缴费
            model.addAttribute("msg", "未缴费!");
            return "insurance/msg";
        }
    }

    /**
     * 提交保险理赔申请
     * @param bxInsurance
     * @param session
     * @return
     */
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/postApplyForClaim")
    public JsonResult postApplyForClaim(BxInsurance bxInsurance, HttpSession session) {
        JsonResult jsonResult = new JsonResult();
        String openid = session.getAttribute("openid").toString();
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(openid);
        if (person != null) {
            //设置更新后的状态信息
            bxInsurance.setStatus((byte) 4);
            bxInsurance.setPersonId(person.getId());
            //更新数据
            int i = bxInsuranceService.updateByIdAndPersonId(bxInsurance);
            //数据更新成功
            if (i >= 0) {
                jsonResult.setCode(1);
                jsonResult.setResult("ok");
                return jsonResult;
            }
        }
        jsonResult.setCode(0);
        jsonResult.setResult("失败");
        return jsonResult;
    }

    /**
     * 用户资料管理
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
        return "insurance/users";
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
    @RequestMapping(value = "/updateUser")
    public String update(XjhbPerson xjhbPerson) {
        Integer re = xjhbPersonService.updateByPrimaryKey(xjhbPerson);
        return re.toString();
    }

}
