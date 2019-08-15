package com.gzmusxxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.*;
import com.gzmusxxy.service.*;
import com.gzmusxxy.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 后台管理
 */
@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private XjhbProjectService xjhbProjectService;

    @Autowired
    private XjhbInformationService xjhbInformationService;

    @Autowired
    private XjhbPersonService xjhbPersonService;

    @Autowired
    private BxProjectService bxProjectService;

    @Autowired
    private BxInsuranceService bxInsuranceService;

    @RequestMapping(value = "/login")
        public String login(HttpSession session) {
        session.removeAttribute("admin");
        return "admin/login";
    }

    /**
     * 更新admin信息
     * @param admin
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateMsg")
    public String updateMsg(Admin admin){
        Admin admin1 = adminService.selectByPrimaryKey(admin.getId());
        Integer re = adminService.updateByPrimaryKey(admin);
        if (!admin.getPassword().equals(admin1.getPassword())){
            return "reLogin";
        }
        return re.toString();
    }

    /**
     * 验证登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/verify")
    public String verify(String username, String password, HttpSession session) {
        Integer id = adminService.verify(username,password);
        if (id > 0){
            session.setAttribute("admin",adminService.selectByPrimaryKey(id));
            return id.toString();
        }else{
            return "输入的账号或密码错误！";
        }
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "admin/index";
    }

    /**
     * 查询加分页
     * @param model model
     * @param name name
     * @param pageNumber pageNumber
     * @return admin/xjhb_project
     */
    @RequestMapping(value = "/xjhbProject")
    public String xjhbProject(Model model, String name, @RequestParam("pageNumber") Integer pageNumber){
        PageInfo<XjhbProject> pageInfo = xjhbProjectService.selectProjectByNameLike(name, pageNumber);
        //防止搜索栏bug
        if (name == null) {
            name = "";
        }
        model.addAttribute("name",name);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pages", PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/xjhb_project";
    }

    /**
     * 申请书管理
     * @param model model
     * @param name name
     * @param pageNumber pageNumber
     * @return return
     */
    @RequestMapping(value = "/xjhbApply")
    public String xjhbApply(Model model,String name, @RequestParam("pageNumber") Integer pageNumber){
        PageInfo<XjhbInformation> pageInfo = xjhbInformationService.selectApplyByNameLike(name, pageNumber);
        //防止搜索栏bug
        if (name == null) {
            name = "";
        }
        model.addAttribute("name",name);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pages",PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/xjhb_apply";
    }

    /**
     * 详细信息(通用)
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detailed")
    public String detailed(int id) {
        JSONObject json = new JSONObject();
        XjhbInformation xjhbInformation = xjhbInformationService.selectByPrimaryKey(id);
        XjhbProject xjhbProject = xjhbProjectService.selectByPrimaryKey(xjhbInformation.getProjectId());
        XjhbPerson xjhbPerson = xjhbPersonService.selectByPrimaryKey(xjhbInformation.getPersonId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String createTime = null;
        if (xjhbInformation.getCreateTime() != null) {
            createTime = formatter.format(xjhbInformation.getCreateTime());
        }
        json.put("createTime",createTime);
        json.put("project",xjhbProject);
        json.put("person",xjhbPerson);
        return json.toJSONString();
    }

    /**
     * xjhb改变状态（通用）
     * @param id 申请书id
     * @param status 申请书status
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/status")
    public String status(int id, byte status) {
        XjhbInformation xjhbInformation = xjhbInformationService.selectByPrimaryKey(id);
        xjhbInformation.setStatus(status);
        if(xjhbInformationService.updateByPrimaryKey(xjhbInformation) == 1){
            new Thread(){
                public void run(){
                    XjhbPerson xjhbPerson = xjhbPersonService.selectByPrimaryKey(xjhbInformation.getPersonId());
                    XjhbProject xjhbProject = xjhbProjectService.selectByPrimaryKey(xjhbInformation.getProjectId());
                    switch (status) {
                        case 2:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(),xjhbPerson.getName()+" 你好！",
                                    xjhbProject.getProjectName()+"的申请",
                                    false,"审核失败","请完善资料再申请审核","");break;
                        case 3:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(),xjhbPerson.getName()+" 你好！"
                                    ,xjhbProject.getProjectName()+"的申请",
                                    true,"审核通过","","");break;
                        case 5:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(),xjhbPerson.getName()+" 你好！"
                                    ,xjhbProject.getProjectName()+"的验收",
                                    true,"初步验收通过","请准备好接受线下验收","");break;
                        case 7:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(),xjhbPerson.getName()+" 你好！"
                                    ,xjhbProject.getProjectName()+"的验收",
                                    false,"验收失败","请完善资料再申请验收","");break;
                        case 6:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(),xjhbPerson.getName()+" 你好！"
                                    ,xjhbProject.getProjectName()+"的验收",
                                    true,"验收通过","请等待补助发放","");break;
                        case 8:
                            WeChatUtil.sendBusinessNoticeMsg(xjhbPerson.getOpenid(),xjhbPerson.getName()+" 你好！"
                                    ,"转帐业务","补助已发放,请查验","如有问题，请联系电话xxxxxxxxxxxxx","");break;
                    }
                }
            }.start();
        }
        return "";
    }

    /**
     * 待验收管理
     * @param model model
     * @param name name
     * @param pageNumber pageNumber
     * @return return
     */
    @RequestMapping(value = "/xjhbCheck")
    public String xjhbCheck(Model model,String name, @RequestParam("pageNumber") Integer pageNumber){
        PageInfo<XjhbInformation> pageInfo = xjhbInformationService.selectCheckByNameLike(name, pageNumber);
        //防止搜索栏bug
        if (name == null) {
            name = "";
        }
        model.addAttribute("name",name);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pages",PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/xjhb_check";
    }

    /**
     * 验收通过查看
     * @param model model
     * @param name name
     * @param pageNumber pageNumber
     * @return return
     */
    @RequestMapping(value = "/xjhbAdopt")
    public String xjhbAdopt(Model model,String name, @RequestParam("pageNumber") Integer pageNumber){
        PageInfo<XjhbInformation> pageInfo = xjhbInformationService.selectAdoptByNameLike(name, pageNumber);
        //防止搜索栏bug
        if (name == null) {
            name = "";
        }
        model.addAttribute("name",name);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pages",PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/xjhb_adopt";
    }

    /**
     * 标记全部转帐
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/adoptAllTransfer")
    public String adoptAllTransfer(){
        return xjhbInformationService.updateStatus().toString();
    }

    /**
     * 管理员信息管理
     * @param model model
     * @return return
     */
    @RequestMapping(value = "/message")
    public String message(Model model){
        return "admin/admin_message";
    }

    /**
     * 处理前台时间传到后台的格式
     * @param binder binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 添加项目
     * @param xjhbProject xjhbProject
     * @return return
     */
    @ResponseBody
    @RequestMapping(value = "/addProject")
    public String addProject(XjhbProject xjhbProject) {
        Integer re = xjhbProjectService.insert(xjhbProject);
        return re.toString();
    }

    /**
     * 根据id删除项目
     * @param id id
     * @return return
     */
    @ResponseBody
    @PostMapping(value = "/delProject")
    public String delProject(int id) {
        Integer re = xjhbProjectService.deleteByPrimaryKey(id);
        return re.toString();
    }

    /**
     * 获取项目id
     * @param id id
     * @return return
     */
    @ResponseBody
    @RequestMapping(value = "/updateProject")
    public String updateProject(int id){
        JSONObject json = new JSONObject();
        XjhbProject xjhbProject = xjhbProjectService.selectByPrimaryKey(id);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = formatter.format(xjhbProject.getStartTime());
        String endTime = formatter.format(xjhbProject.getEndTime());
        json.put("project",xjhbProject);
        json.put("startTime",startTime);
        json.put("endTime",endTime);
        return json.toJSONString();
    }

    /**
     * 更新项目
     * @param xjhbProject xjhbProject
     * @param filePath filePath
     * @param fileName fileName
     * @return return
     */
    @ResponseBody
    @PostMapping(value = "/update")
    public String update(XjhbProject xjhbProject,String filePath, String fileName){
        //已经上传文件
        if (filePath !=null && !filePath.equals("")) {
            xjhbProject.setApplicationTemplate(filePath);
            xjhbProject.setApplicationTemplateName(fileName);
        }
        Integer re = xjhbProjectService.updateByPrimaryKey(xjhbProject);
        return re.toString();
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
        if (path == null || path.equals("null") || path.equals("")){
            return FileUtil.saveFile(file,null,type);
        }
        return FileUtil.saveFile(file,path,type);
    }

    /**
     * 下载项目模板文件
     * @param id id
     * @param request request
     * @param response response
     * @return return
     */
    @ResponseBody
    @RequestMapping(value= "/downloadProject")
    public String downloadProject(int id, HttpServletRequest request, HttpServletResponse response){
        XjhbProject xjhbProject = xjhbProjectService.selectByPrimaryKey(id);
        FileUtil.downloadFile(xjhbProject.getApplicationTemplate(),xjhbProject.getApplicationTemplateName(),request,response);
        return "";
    }

    /**
     * 下载申请书
     * @param id id
     * @param name name
     * @param request request
     * @param response response
     * @return return
     */
    @ResponseBody
    @RequestMapping(value= "/downloadApply")
    public String downloadApply(int id,String name, HttpServletRequest request, HttpServletResponse response){
        XjhbInformation xjhbInformation = xjhbInformationService.selectByPrimaryKey(id);
        if (name.equals("apply")) {
            FileUtil.downloadFile(xjhbInformation.getProjectApplication(),xjhbInformation.getProjectApplicationName(),request,response);
        }else {
            FileUtil.downloadFile(xjhbInformation.getOtherProof(),xjhbInformation.getOtherProofName(),request,response);
        }
        return "";
    }

    /**
     * 下载现场照片
     * @param id id
     * @param request request
     * @param response response
     * @return return
     */
    @ResponseBody
    @RequestMapping(value= "/downloadScenePhotos")
    public String downloadScenePhotos(int id, HttpServletRequest request, HttpServletResponse response){
        XjhbInformation xjhbInformation = xjhbInformationService.selectByPrimaryKey(id);
        FileUtil.downloadFile(xjhbInformation.getScenePhotos(),xjhbInformation.getScenePhotosName(),request,response);
        return "";
    }

    /**
     * 导出全部未转帐excl
     * @param status id
     * @param request request
     * @param response response
     * @return return
     */
    @ResponseBody
    @RequestMapping(value= "/downloadExcl")
    public String downloadExcl(int status, HttpServletRequest request, HttpServletResponse response){
        List<XjhbInformation> xjhbInformationList = xjhbInformationService.selectAdoptByStatus(status);
        String title[] = new String[]{"编号","申请项目名称","项目申请经费","申请人","农户一卡通号"};
        String items[][] = new String[xjhbInformationList.size()][title.length];
        int j = 0;
        for (XjhbInformation xjhbInformation:
        xjhbInformationList) {
            items[j][0] = xjhbInformation.getId().toString();
            items[j][1] = xjhbInformation.getProjectName();
            items[j][2] = xjhbInformation.getOutlay().toString();
            items[j][3] = xjhbInformation.getProjectName();
            items[j][4] = xjhbInformation.getOneCardSolution();
            j++;
        }
        String path = ExcelUtil.getHSSFWorkbook(
                "先建后补项目转帐名单",title,items,null,
                FileUtil.FILE_PATH,"先建后补项目转帐名单"
        );
        String name = path.substring(path.lastIndexOf("/"));
        FileUtil.downloadFile(path,name,request,response);
        FileUtil.deleteFile(path);
        return "";
    }

    /**
     * 下载身份证
     * @param id id
     * @param request request
     * @param response response
     * @return return
     */
    @ResponseBody
    @RequestMapping(value= "/downloadCard")
    public String downloadCard(int id,String name, HttpServletRequest request, HttpServletResponse response){
        XjhbPerson xjhbPerson = xjhbPersonService.selectByPrimaryKey(id);
        if (name.equals("front")) {
            String type = xjhbPerson.getIdCardFront().substring(xjhbPerson.getIdCardFront().lastIndexOf("."));
            FileUtil.downloadFile(xjhbPerson.getIdCardFront(),"身份证正面"+type,request,response);
        }else {
            String type = xjhbPerson.getIdCardReverse().substring(xjhbPerson.getIdCardReverse().lastIndexOf("."));
            FileUtil.downloadFile(xjhbPerson.getIdCardReverse(),"身份证反面"+type,request,response);
        }
        return "";
    }

    /* -------------------农业保险管理------------------- */

    /**
     * 查询加分页 保险项目管理
     * @param model model
     * @param name name
     * @param pageNumber pageNumber
     * @return admin/bx_project
     */
    @RequestMapping(value = "/bxProject")
    public String bxProject(Model model, String name, @RequestParam("pageNumber") Integer pageNumber){
        PageInfo<BxProject> pageInfo = bxProjectService.selectProjectByNameLike(name, pageNumber);
        //防止搜索栏bug
        if (name == null) {
            name = "";
        }
        model.addAttribute("name",name);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pages",PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/bx_project";
    }

    /**
     * 根据id查询保险项目
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findBxProject")
    public String findBxProject(Integer id){
        JSONObject json = new JSONObject();
        BxProject bxProject = bxProjectService.selectByPrimaryKey(id);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = formatter.format(bxProject.getStartTime());
        String endTime = formatter.format(bxProject.getEndTime());
        json.put("project",bxProject);
        json.put("startTime",startTime);
        json.put("endTime",endTime);
        return json.toJSONString();
    }

    /**
     * 根据传过来的值更新保险项目
     * @param bxProject
     * @param filePath
     * @param fileName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateBxProject")
    public String updateBxProject(BxProject bxProject, String filePath, String fileName) {
        //已经上传文件
        if (filePath !=null && !filePath.equals("")) {
            bxProject.setClaimsTemplate(filePath);
            bxProject.setClaimsTemplateName(fileName);
        }
        Integer re = bxProjectService.updateByPrimaryKey(bxProject);
        return re.toString();
    }

    /**
     * 添加保险项目
     * @param bxProject
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBxProject")
    public String addBxProject(BxProject bxProject) {
        Integer re = bxProjectService.insert(bxProject);
        return re.toString();
    }

    /**
     * 根据id删除保险项目
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delBxProject")
    public String delBxProject(Integer id) {
        Integer re = bxProjectService.deleteByPrimaryKey(id);
        return re.toString();
    }

    /**
     * 查询加分页 购买审核
     * @param model model
     * @param name name
     * @param pageNumber pageNumber
     * @return admin/bx_audit
     */
    @RequestMapping(value = "/bxAudit")
    public String bxAudit(Model model, String name,String poverty, @RequestParam("pageNumber") Integer pageNumber){
        PageInfo<BxInsurance> pageInfo = bxInsuranceService.selectAuditByNameLike(name, poverty, pageNumber);
        //防止搜索栏bug
        if (name == null) {
            name = "";
        }
        model.addAttribute("name",name);
        model.addAttribute("poverty",poverty);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pages",PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/bx_audit";
    }

    /**
     * 查询加分页 理赔审核
     * @param model model
     * @param name name
     * @param pageNumber pageNumber
     * @return admin/bx_claims
     */
    @RequestMapping(value = "/bxClaims")
    public String bxClaims(Model model, String name, @RequestParam("pageNumber") Integer pageNumber){
        PageInfo<BxInsurance> pageInfo = bxInsuranceService.selectClaimsByNameLike(name, pageNumber);
        //防止搜索栏bug
        if (name == null) {
            name = "";
        }
        model.addAttribute("name",name);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pages",PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/bx_claims";
    }

    /**
     * 下载压缩打包文件
     * @param id 理赔书id
     * @param request request
     * @param response response
     * @return ""
     */
    @ResponseBody
    @RequestMapping(value= "/downloadZip")
    public String downloadZip(int id, HttpServletRequest request, HttpServletResponse response) {
        BxInsurance bxInsurance = bxInsuranceService.selectClaimsById(id);
        List<String> filePaths = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        String zipPath = FileUtil.FILE_PATH + UUID.randomUUID() + ".zip";
        filePaths.add(bxInsurance.getClaimsApplication());
        filePaths.add(bxInsurance.getIdCardFront());
        filePaths.add(bxInsurance.getIdCardReverse());
        filePaths.add(bxInsurance.getAffectedPhoto());
        fileNames.add(bxInsurance.getClaimsApplicationName());
        fileNames.add("身份证正面照片");
        fileNames.add("身份证反面照片");
        if (!bxInsurance.getAffectedPhotoName().equals(bxInsurance.getClaimsApplicationName())) {
            fileNames.add(bxInsurance.getAffectedPhotoName());
        } else {
            fileNames.add("受灾照片");
        }
        ZipUtil.toZip(filePaths,fileNames,zipPath);
        FileUtil.downloadFile(zipPath,"理赔资料.zip",request,response);
        FileUtil.deleteFile(zipPath);
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

    /**
     * bx改变状态（通用）
     * @param id insuranceId
     * @param status 需要改变的状态
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/bxStatus")
    public String bxStatus(int id, byte status) {
        BxInsurance bxInsurance = bxInsuranceService.selectByPrimaryKey(id);
        if (status == 2 && xjhbPersonService.selectByPrimaryKey(bxInsurance.getPersonId()).getPoverty() == 1) {
            bxInsurance.setPayCost((byte)1);
        }
        bxInsurance.setStatus(status);
        bxInsuranceService.updateByPrimaryKey(bxInsurance);
        return "";
    }
}
