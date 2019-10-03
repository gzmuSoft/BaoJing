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
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private BulletinService bulletinService;

    @Autowired
    private YlGuaranteeService ylGuaranteeService;

    @Autowired
    private JyStudentService jyStudentService;

    @Autowired
    private JyApplyService jyApplyService;

    @Autowired
    private ZfTemplateService zfTemplateService;

    @Autowired
    private ZfApplyService zfApplyService;

    @Autowired
    private ZfPhotoService zfPhotoService;


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
     * 跳转设置公告页面
     * @param sourceId 项目id
     * @return
     */
    @RequestMapping(value = "/bulletin")
    public String bulletin(int sourceId, Model model) {
        List<Bulletin> list = bulletinService.selectBySourceId(sourceId);
        if (list.size() > 0){
            Bulletin bulletin = list.get(0);
            if (bulletin.getContent() != null) {
                try {
                    model.addAttribute("content",new String(bulletin.getContent(),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else {
                model.addAttribute("content","");
            }
            model.addAttribute("bulletin",bulletin);
        }else {
            model.addAttribute("bulletin",new Bulletin());
        }
        //判断该回哪个页面
        if (sourceId == 1){
            return "admin/xjhb_bulletin";
        }else {
            return "admin/bx_bulletin";
        }
    }

    /**
     * 保存公告
     * @param bulletin
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveBulletin")
    public String saveBulletin(Bulletin bulletin, String content){
        try {
            byte[] bytes = content.getBytes("UTF-8");
            bulletin.setContent(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (bulletin.getId() != null){
            bulletinService.updateByPrimaryKey(bulletin);
            return "";
        }else {
            bulletin = bulletinService.insert(bulletin);
            if (bulletin != null)
                return bulletin.getId() + "";
            else
                return "";
        }
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
    public String status(int id, byte status, String remark, HttpSession session) {
        XjhbInformation xjhbInformation = xjhbInformationService.selectByPrimaryKey(id);
        xjhbInformation.setStatus(status);
        if(xjhbInformationService.updateByPrimaryKey(xjhbInformation) == 1){
            Admin admin = (Admin) session.getAttribute("admin");
            if (admin == null) {
                admin = new Admin();
            }
            Admin finalAdmin = admin;
            new Thread(){
                @Override
                public void run(){
                    XjhbPerson xjhbPerson = xjhbPersonService.selectByPrimaryKey(xjhbInformation.getPersonId());
                    XjhbProject xjhbProject = xjhbProjectService.selectByPrimaryKey(xjhbInformation.getProjectId());
                    switch (status) {
                        case 2:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(),xjhbPerson.getName()+" 你好！",
                                    xjhbProject.getProjectName()+"的申请",
                                    false,"审核失败",remark,"");break;
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
                                    false,"验收失败",remark,"");break;
                        case 6:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(),xjhbPerson.getName()+" 你好！"
                                    ,xjhbProject.getProjectName()+"的验收",
                                    true,"验收通过","请等待补助发放","");break;
                        case 8:
                            WeChatUtil.sendBusinessNoticeMsg(xjhbPerson.getOpenid(),xjhbPerson.getName()+" 你好！"
                                    ,"转帐业务","补助已发放,请查验","如有问题，请联系电话:"+
                                            finalAdmin.getPhone(),"");break;
                    }
                    super.run();
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
        String title[] = new String[]{"项目类型","项目名称","项目简介","项目申请经费","申请人","农户一卡通号"};
        String items[][] = new String[xjhbInformationList.size()][title.length];
        int j = 0;
        for (XjhbInformation xjhbInformation:
        xjhbInformationList) {
            items[j][0] = xjhbInformation.getProjectName();
            items[j][1] = xjhbInformation.getProjectTitle();
            items[j][2] = xjhbInformation.getProjectContent();
            items[j][3] = xjhbInformation.getOutlay().toString();
            items[j][4] = xjhbInformation.getProjectName();
            items[j][5] = xjhbInformation.getOneCardSolution();
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
     * @param name 项目名称
     * @param poverty 是否贫困
     * @param pageNumber pageNumber
     * @return
     */
    @RequestMapping(value = "/bxAudit")
    public String bxAudit(Model model, String name,Integer poverty, @RequestParam("pageNumber") Integer pageNumber){
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
     * 查询加分页 缴费验收
     * @param model model
     * @param name name
     * @param pageNumber pageNumber
     * @return admin/bx_audit
     */
    @RequestMapping(value = "/bxCheck")
    public String bxCheck(Model model, String name, @RequestParam("pageNumber") Integer pageNumber,String personName, String idCard,Integer cost){
        PageInfo<BxInsurance> pageInfo = bxInsuranceService.selectCheckByNameLike(name, pageNumber, personName, idCard, cost);
        model.addAttribute("personName",personName);
        model.addAttribute("idCard",idCard);
        model.addAttribute("cost",cost);
        model.addAttribute("name",name);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pages",PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/bx_check";
    }

    /**
     * 付款验证
     * @param id insuranceId
     * @param payCost 是否付款
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/bxPayCost")
    public String bxPayCost(int id, byte payCost,HttpSession session) {
        BxInsurance bxInsurance = bxInsuranceService.selectByPrimaryKey(id);
        bxInsurance.setPayCost(payCost);
        if (bxInsuranceService.updateByPrimaryKey(bxInsurance) == 1){
            Admin admin = (Admin) session.getAttribute("admin");
            if (admin == null) {
                admin = new Admin();
            }
            Admin finalAdmin = admin;
            new Thread(){
                @Override
                public void run() {
                    XjhbPerson xjhbPerson = xjhbPersonService.selectByPrimaryKey(bxInsurance.getPersonId());
                    BxProject bxProject = bxProjectService.selectByPrimaryKey(bxInsurance.getProjectId());
                    if (payCost == 1){
                        WeChatUtil.sendBusinessNoticeMsg(xjhbPerson.getOpenid(),xjhbPerson.getName()+" 你好！"
                                ,"缴费业务",bxProject.getName()+"的转帐已收到","接下来您可以在需要理赔的时候申请理赔。如有问题，请联系电话:"+finalAdmin.getPhone(),"");
                    }
                    if (payCost == 0){
                        WeChatUtil.sendBusinessNoticeMsg(xjhbPerson.getOpenid(),xjhbPerson.getName()+" 你好！"
                                ,"缴费业务",bxProject.getName()+"的转帐未收到","如确实转帐，请再次申请验收是否转帐。如有问题，请联系电话:"+finalAdmin.getPhone(),"");
                    }
                    super.run();
                }
            }.start();
        }
        return "";
    }

    /**
     * 查询加分页 理赔审核
     * @param model model
     * @param name name
     * @param pageNumber pageNumber
     * @return admin/bx_claims
     */
    @RequestMapping(value = "/bxClaims")
    public String bxClaims(Model model, String name, @RequestParam("pageNumber") Integer pageNumber, String personName, String startTime, String endTime){
        PageInfo<BxInsurance> pageInfo = bxInsuranceService.selectClaimsByNameLike(name, pageNumber, personName, startTime, endTime);
        model.addAttribute("personName",personName);
        model.addAttribute("startTime",startTime);
        model.addAttribute("endTime",endTime);
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
        if (FileUtil.existFile(bxInsurance.getClaimsApplication())){
            filePaths.add(bxInsurance.getClaimsApplication());
            fileNames.add(bxInsurance.getClaimsApplicationName());
        }
        if (FileUtil.existFile(bxInsurance.getIdCardFront())){
            filePaths.add(bxInsurance.getIdCardFront());
            String type = bxInsurance.getIdCardFront();
            type = type.substring(type.lastIndexOf("."));
            fileNames.add("身份证正面照片"+type);
        }
        if (FileUtil.existFile(bxInsurance.getIdCardReverse())){
            filePaths.add(bxInsurance.getIdCardReverse());
            String type = bxInsurance.getIdCardReverse();
            type = type.substring(type.lastIndexOf("."));
            fileNames.add("身份证反面照片"+type);
        }
        if (FileUtil.existFile(bxInsurance.getAffectedPhoto())){
            filePaths.add(bxInsurance.getAffectedPhoto());
            //防止可能出现的重名问题
            if (!bxInsurance.getAffectedPhotoName().equals(bxInsurance.getClaimsApplicationName())) {
                fileNames.add(bxInsurance.getAffectedPhotoName());
            } else {
                String type = bxInsurance.getAffectedPhoto();
                type = type.substring(type.lastIndexOf("."));
                fileNames.add("受灾照片"+type);
            }
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
    public String bxStatus(int id, byte status, String remark) {
        BxInsurance bxInsurance = bxInsuranceService.selectByPrimaryKey(id);
        //是贫穷则默认付款
        if (status == 2 && xjhbPersonService.selectByPrimaryKey(bxInsurance.getPersonId()).getPoverty() == 1) {
            bxInsurance.setPayCost((byte)1);
        }
        bxInsurance.setStatus(status);
        if(bxInsuranceService.updateByPrimaryKey(bxInsurance) == 1) {
            new Thread() {
                @Override
                public void run() {
                    XjhbPerson xjhbPerson = xjhbPersonService.selectByPrimaryKey(bxInsurance.getPersonId());
                    BxProject bxProject = bxProjectService.selectByPrimaryKey(bxInsurance.getProjectId());
                    switch (status) {
                        case 3:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(), xjhbPerson.getName() + " 你好！",
                                    bxProject.getName() + "的申请",
                                    false, "审核失败", remark, "");
                            break;
                        case 2:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(), xjhbPerson.getName() + " 你好！"
                                    , bxProject.getName()  + "的申请",
                                    true, "审核通过", "请及时缴纳保险费用", "");
                            break;
                        case 5:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(), xjhbPerson.getName() + " 你好！"
                                    , bxProject.getName()  + "的理赔申请",
                                    true, "理赔申请通过", "已将资料发送至保险公司，请耐心等待保险公司前来定损", "");
                            break;
                        case 6:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(), xjhbPerson.getName() + " 你好！"
                                    , bxProject.getName()  + "的验收",
                                    false, "理赔申请失败", remark, "");
                            break;
                    }
                    super.run();
                }
            }.start();
        }
        return "";
    }

//    医疗保障

    /**
     * 医疗保障分页查询通知
     * @param model
     * @param pageNumber
     * @return
     */
    @RequestMapping(value = "/ylNotification")
    public String ylNotification(Model model, Integer pageNumber) {
        PageInfo<Bulletin> pageInfo = bulletinService.selectAllBySourceId(pageNumber, 3);
        List<String> list =  new LinkedList<>();
        for (Bulletin bulletin:
        pageInfo.getList()) {
            if (bulletin.getContent() != null) {
                try {
                    list.add(new String(bulletin.getContent(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("contentList", list);
        model.addAttribute("pages",PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/yl_guarantee";
    }

    /**
     * 医疗保障分页查询验证缴费页面
     * @param model
     * @param pageNumber
     * @param name
     * @return
     */
    @RequestMapping(value = "/ylVerificationPay")
    public String ylVerificationPay(Model model, Integer pageNumber, String name) {
        PageInfo<YlGuarantee> pageInfo = ylGuaranteeService.selectByNameCostLike(name, pageNumber);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("name",name);
        model.addAttribute("pages",PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/yl_verification_pay";
    }

    /**
     * 医疗保障分页查询报销页面
     * @param model
     * @param pageNumber
     * @return
     */
    @RequestMapping(value = "/ylReimbursement")
    public String ylReimbursement(Model model, Integer pageNumber, String name) {
        PageInfo<YlGuarantee> pageInfo = ylGuaranteeService.selectByNameLike(name, pageNumber);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("name",name);
        model.addAttribute("pages",PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/yl_reimbursement";
    }

    /**
     * 医疗保障分页查询转账页面
     * @param model
     * @param pageNumber
     * @return
     */
    @RequestMapping(value = "/ylTransferAccounts")
    public String ylTransferAccounts(Model model, Integer pageNumber, String name) {
        PageInfo<YlGuarantee> pageInfo = ylGuaranteeService.selectAccountByNameLike(name, pageNumber);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("name",name);
        model.addAttribute("pages",PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/yl_transfer_accounts";
    }

    /**
     * 医疗保障全部更新状态
     * @param front 前状态
     * @param after 后状态
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/ylUpdateSign")
    public String ylUpdateSign(Integer front, Integer after) {
        return ylGuaranteeService.updateStatus(front, after).toString();
    }

    /**
     * 医疗导出excl
     * @param status 对应类型
     * @param request request
     * @param response response
     * @return return
     */
    @ResponseBody
    @RequestMapping(value= "/downloadYlExcl")
    public String downloadYlExcl(Integer status, HttpServletRequest request, HttpServletResponse response){
        List<YlGuarantee> ylGuarantees = ylGuaranteeService.selectAllByStatus(status);
        String title[] = new String[]{"申请人","身份证号","一折通卡号","转账备注","申请时间"};
        String items[][] = new String[ylGuarantees.size()][title.length];
        int j = 0;
        for (YlGuarantee ylGuarantee:
                ylGuarantees) {
            items[j][0] = ylGuarantee.getName();
            items[j][1] = ylGuarantee.getIdentity();
            items[j][2] = ylGuarantee.getCard();
            items[j][3] = ylGuarantee.getRemark();
            if (ylGuarantee.getApplicationTime() != null) {
                items[j][4] = ylGuarantee.getApplicationTime().toString();
            }else {
                items[j][4] = "";
            }
            j++;
        }
        String path = "";
        if (status == 6) {
            path = ExcelUtil.getHSSFWorkbook(
                    "医疗保障待收材料名单",title,items,null,
                    FileUtil.FILE_PATH,"医疗保障待收材料名单"
            );
        }else {
            path = ExcelUtil.getHSSFWorkbook(
                    "医疗保障待转账名单",title,items,null,
                    FileUtil.FILE_PATH,"医疗保障待转账名单"
            );
        }
        String name = path.substring(path.lastIndexOf("/"));
        FileUtil.downloadFile(path,name,request,response);
        FileUtil.deleteFile(path);
        return "";
    }

    /**
     * 医疗保障改变状态
     * @param id id
     * @param status status
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/ylStatus")
    public String ylStatus(Integer id, Byte status, String remark, HttpSession session) {
        YlGuarantee ylGuarantee = ylGuaranteeService.selectByPrimaryKey(id);
        ylGuarantee.setStatus(status);
        if (ylGuaranteeService.updateByPrimaryKey(ylGuarantee) > 0) {
            Admin admin = (Admin) session.getAttribute("admin");
            if (admin == null) {
                admin = new Admin();
            }
            Admin finalAdmin = admin;
            new Thread() {
                @Override
                public void run() {
                    XjhbPerson xjhbPerson = xjhbPersonService.selectByPrimaryKey(ylGuarantee.getPersonId());
                    switch (status) {
                        case 2:
                            WeChatUtil.sendBusinessNoticeMsg(xjhbPerson.getOpenid(),xjhbPerson.getName()+" 你好！"
                                    ,"缴费业务","转帐已收到","接下来您可以在需要报销的时候申请报销。如有问题，请联系电话:"+ finalAdmin.getPhone(),"");
                            break;
                        case 3:
                            WeChatUtil.sendBusinessNoticeMsg(xjhbPerson.getOpenid(),xjhbPerson.getName()+" 你好！"
                                    ,"缴费业务","转帐未收到","如确实转帐，请再次申请验证。如有问题，请联系电话:"+ finalAdmin.getPhone(),"");
                            break;
                        case 6:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(), xjhbPerson.getName() + " 你好！"
                                    , "申请报销",
                                    true, "通过", "请及时线下提交材料。如有问题，请联系电话:"+ finalAdmin.getPhone(), "");
                            break;
                        case 5:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(), xjhbPerson.getName() + " 你好！"
                                    , "申请报销",
                                    false, "失败", remark, "");
                            break;
                        case 7:
                            WeChatUtil.sendBusinessNoticeMsg(xjhbPerson.getOpenid(),xjhbPerson.getName()+" 你好！"
                                    ,"业务通知","材料已验收，请等待转账。","如有问题，请联系电话:"+
                                            finalAdmin.getPhone(),"");
                            break;
                        case 8:
                            WeChatUtil.sendBusinessNoticeMsg(xjhbPerson.getOpenid(),xjhbPerson.getName()+" 你好！"
                                    ,"转帐业务","已转账, 请查验","如有问题，请联系电话:"+
                                            finalAdmin.getPhone(),"");
                            break;
                    }
                    super.run();
                }
            }.start();
            return "yes";
        }
        return "no";
    }

//    教育保障
    /**
     * 教育保障公告分页查询通知
     * @param model
     * @param pageNumber
     * @return
     */
    @RequestMapping(value = "/jyNotification")
    public String jyNotification(Model model, Integer pageNumber) {
        PageInfo<Bulletin> pageInfo = bulletinService.selectAllBySourceId(pageNumber, 4);
        List<String> list =  new LinkedList<>();
        for (Bulletin bulletin:
                pageInfo.getList()) {
            if (bulletin.getContent() != null) {
                try {
                    list.add(new String(bulletin.getContent(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("contentList", list);
        model.addAttribute("pages",PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/jy_notification";
    }

    /**
     * 教育保障学生管理分页查询通知
     * @param model
     * @param pageNumber
     * @param name
     * @return
     */
    @RequestMapping(value = "/jyStudent")
    public String jyStudent(Model model, Integer pageNumber, String name) {
        PageInfo<JyStudent> pageInfo = jyStudentService.selectByNameLike(name, pageNumber);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("name", name);
        model.addAttribute("pages",PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/jy_student";
    }

    /**
     * 查询学生信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getStudent")
    public String getStudent(Integer id) {
        JyStudent jyStudent = jyStudentService.selectByPrimaryKey(id);
        JSONObject json = new JSONObject();
        json.put("jyStudent", jyStudent);
        return json.toJSONString();
    }

    /**
     *  插入学生
     * @param jyStudent
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addStudent")
    public String addStudent(JyStudent jyStudent) {
        Integer re = jyStudentService.insert(jyStudent);
        return re.toString();
    }

    /**
     * 教育保障 申请资助
     * @param model
     * @param pageNumber
     * @param name
     * @return
     */
    @RequestMapping(value = "/jyApply")
    public String jyApply(Model model, Integer pageNumber, String name) {
        PageInfo<JyApply> pageInfo = jyApplyService.selectByNameLike(name, pageNumber);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("name", name);
        model.addAttribute("pages",PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/jy_apply";
    }

    /**
     * 教育保障改变状态
     * @param id id
     * @param status status
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/jyStatus")
    public String jyStatus(Integer id, Byte status, String remark, HttpSession session) {
        JyApply jyApply = jyApplyService.selectByPrimaryKey(id);
        jyApply.setStatus(status);
        if (jyApplyService.updateByPrimaryKey(jyApply) > 0) {
            Admin admin = (Admin) session.getAttribute("admin");
            if (admin == null) {
                admin = new Admin();
            }
            Admin finalAdmin = admin;
            new Thread() {
                @Override
                public void run() {
                    JyStudent jyStudent = jyStudentService.selectByPrimaryKey(jyApply.getStudentId());
                    switch (status) {
                        case 2:
                            WeChatUtil.sendReviewNoticeMsg(jyApply.getOpenId(), jyStudent.getName() + " 你好！"
                                    , "申请资助",
                                    true, "通过", "如有问题，请联系电话:"+ finalAdmin.getPhone(), "");
                            break;
                        case 3:
                            WeChatUtil.sendReviewNoticeMsg(jyApply.getOpenId(), jyStudent.getName() + " 你好！"
                                    , "申请资助",
                                    false, "失败", remark, "");
                            break;
                    }
                    super.run();
                }
            }.start();
            return "yes";
        }
        return "no";
    }

    /**
     * 更新学生信息
     * @param jyStudent
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateStudent")
    public String updateStudent(JyStudent jyStudent) {
        Integer re = jyStudentService.updateByPrimaryKey(jyStudent);
        return re.toString();
    }

    /**
     * 根据id删除学生
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delStudent")
    public String delStudent(Integer id) {
        Integer re = jyStudentService.deleteByPrimaryKey(id);
        return re.toString();
    }

    //住房保障
    /**
     * 住房保障分页查询通知
     * @param model
     * @param pageNumber
     * @return
     */
    @RequestMapping(value = "/zfNotification")
    public String zfNotification(Model model, Integer pageNumber) {
        PageInfo<Bulletin> pageInfo = bulletinService.selectAllBySourceId(pageNumber, 5);
        List<String> list =  new LinkedList<>();
        for (Bulletin bulletin:
                pageInfo.getList()) {
            if (bulletin.getContent() != null) {
                try {
                    list.add(new String(bulletin.getContent(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("contentList", list);
        model.addAttribute("pages",PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/zf_notification";
    }

    /**
     * 申请书模版查询加分页
     * @param model model
     * @param name name
     * @param pageNumber pageNumber
     * @return admin/zf_project
     */
    @RequestMapping(value = "/zfProject")
    public String zfProject(Model model, String name, @RequestParam("pageNumber") Integer pageNumber){
        PageInfo<ZfTemplate> pageInfo = zfTemplateService.selectByNameLike(name, pageNumber);
        model.addAttribute("name",name);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pages", PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/zf_project";
    }

    /**
     * 添加模版
     * @param zfTemplate
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/zfAddTemplate")
    public String zfAddTemplate(ZfTemplate zfTemplate) {
        zfTemplate.setCreateTime(new Date());
        Integer re = zfTemplateService.insert(zfTemplate);
        return re.toString();
    }

    /**
     * 更新模版
     * @param zfTemplate
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/zfUpdateTemplate")
    public String zfUpdateTemplate(ZfTemplate zfTemplate) {
        Integer re = zfTemplateService.updateByPrimaryKey(zfTemplate);
        return re.toString();
    }

    /**
     * 获取模版
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/zfGetTemplate")
    public String zfGetTemplate(int id) {
        JSONObject jsonObject = new JSONObject();
        ZfTemplate zfTemplate = zfTemplateService.selectByPrimaryKey(id);
        if (zfTemplate != null) {
            jsonObject.put("project",zfTemplate);
        }else {
            jsonObject.put("project",new ZfTemplate());
        }
        return jsonObject.toJSONString();
    }

    /**
     * 删除模版
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/zfDelTemplate")
    public String zfDelTemplate(int id) {
        Integer re = zfTemplateService.deleteByPrimaryKey(id);
        return re.toString();
    }


    /**
     * 申请书管理
     * @param model
     * @param name
     * @param pageNumber
     * @return
     */
    @RequestMapping(value = "/zfApply")
    public String zfApply(Model model, String name, @RequestParam("pageNumber") Integer pageNumber){
        PageInfo<ZfApply> pageInfo = zfApplyService.selectByNameLike(name, pageNumber);
        //防止搜索栏bug
        if (name == null) {
            name = "";
        }
        model.addAttribute("name",name);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pages", PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/zf_apply";
    }

    /**
     * 住房状态改变
     * @param id
     * @param status
     * @param remark
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/zfStatus")
    public String zfStatus(int id, byte status, String remark, HttpSession session) {
        ZfApply zfApply = zfApplyService.selectByPrimaryKey(id);
        zfApply.setStatus(status);
        if(zfApplyService.updateByPrimaryKey(zfApply) == 1) {
            Admin admin = (Admin) session.getAttribute("admin");
            if (admin == null) {
                admin = new Admin();
            }
            Admin finalAdmin = admin;
            new Thread() {
                @Override
                public void run() {
                    XjhbPerson xjhbPerson = xjhbPersonService.selectByPrimaryKey(zfApply.getPersonId());
                    switch (status) {
                        case 3:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(), xjhbPerson.getName() + " 你好！",
                                    "危房改造的申请",
                                    false, "审核失败", remark, "");
                            break;
                        case 2:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(), xjhbPerson.getName() + " 你好！"
                                    , "危房改造的申请",
                                    true, "审核通过", "请严格遵守施工要求进行危房改造", "");
                            break;
                        case 9:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(), xjhbPerson.getName() + " 你好！"
                                    , "危房改造的验收",
                                    false, "审核失败", remark, "");
                            break;
                        case 10:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(), xjhbPerson.getName() + " 你好！"
                                    , "危房改造的验收",
                                    true, "审核通过", "请准备线下验收", "");
                            break;
                        case 11:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(), xjhbPerson.getName() + " 你好！"
                                    , "危房改造的验收",
                                    true, "审核通过", "", "已将信息提交至县住建中心,请耐心等待补助发放,如有问题请联系电话："+finalAdmin.getPhone());
                            break;
                    }
                    super.run();
                }
            }.start();
        }
        return "";
    }

    /**
     * 保存施工要求
     * @param id
     * @param constructionPath
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/zfSaveConstruction")
    public String zfSaveConstruction(int id, String constructionPath) {
        if (constructionPath != null && !constructionPath.equals("")) {
            ZfApply zfApply = zfApplyService.selectByPrimaryKey(id);
            zfApply.setConstructionPath(constructionPath);
            Integer re = zfApplyService.updateByPrimaryKey(zfApply);
            return re.toString();
        }
        return "0";
    }

    /**
     * 照片管理
     * @param model
     * @param name
     * @param pageNumber
     * @return
     */
    @RequestMapping(value = "/zfPhoto")
    public String zfPhoto(Model model, String name, @RequestParam("pageNumber") Integer pageNumber){
        PageInfo<ZfPhoto> pageInfo = zfPhotoService.selectByNameLike(name, pageNumber);
        //防止搜索栏bug
        if (name == null) {
            name = "";
        }
        model.addAttribute("name",name);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pages", PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/zf_photo";
    }

    /**
     * 改变状态和isUpload
     * @param id
     * @param isUpload
     * @param remark
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/zfStatusUpload")
    public String zfStatusUpload(int id, int isUpload, String remark) {
        ZfPhoto zfPhoto = zfPhotoService.selectByPrimaryKey(id);
        ZfApply zfApply = zfApplyService.selectByPrimaryKey(zfPhoto.getApplyId());
        zfPhoto.setIsUpload(0);
        switch (zfApply.getStatus()) {
            case 4:if (isUpload == 0) {
                zfApply.setStatus((byte)2);
            } else {
                zfApply.setStatus((byte)5);
            }
                break;
            case 5:if (isUpload == 0) {
                zfApply.setStatus((byte)4);
            } else {
                zfApply.setStatus((byte)6);
            }
                break;
            case 6:if (isUpload == 0) {
                zfApply.setStatus((byte)5);
            } else {
                zfApply.setStatus((byte)7);
                zfPhoto.setIsUpload(1);
            }
                break;
        }
        if(zfPhotoService.updateByPrimaryKey(zfPhoto) == 1 && zfApplyService.updateByPrimaryKey(zfApply) == 1) {
            new Thread() {
                @Override
                public void run() {
                    XjhbPerson xjhbPerson = xjhbPersonService.selectByPrimaryKey(zfApply.getPersonId());
                    switch (isUpload) {
                        case 0:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(), xjhbPerson.getName() + " 你好！",
                                    "施工照片的审核",
                                    false, "审核失败", remark, "");
                            break;
                        case 1:
                            WeChatUtil.sendReviewNoticeMsg(xjhbPerson.getOpenid(), xjhbPerson.getName() + " 你好！"
                                    , "施工照片的审核",
                                    true, "审核通过", "请继续严格遵守施工要求进行危房改造", "");
                            break;
                    }
                    super.run();
                }
            }.start();
        }
        return "";
    }

    /**
     *验收管理
     * @param model
     * @param name
     * @param pageNumber
     * @return
     */
    @RequestMapping(value = "/zfComplete")
    public String zfComplete(Model model, String name, @RequestParam("pageNumber") Integer pageNumber){
        PageInfo<ZfApply> pageInfo = zfApplyService.selectCompleteByNameLike(name, pageNumber);
        //防止搜索栏bug
        if (name == null) {
            name = "";
        }
        model.addAttribute("name",name);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pages", PageUtil.getPage(pageInfo.getPages(), pageNumber));
        return "admin/zf_complete";
    }

    /**
     * 导出excl
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value= "/downloadZfExcl")
    public String downloadZfExcl(HttpServletRequest request, HttpServletResponse response){
        List<ZfApply> zfApplies = zfApplyService.selectByStatus(11);
        String title[] = new String[]{"申请人","身份证号","联系电话"};
        String items[][] = new String[zfApplies.size()][title.length];
        int j = 0;
        for (ZfApply ZfApply:
                zfApplies) {
            items[j][0] = ZfApply.getName();
            items[j][1] = ZfApply.getIdentity();
            items[j][2] = ZfApply.getTelphone();
            j++;
        }
        String path = ExcelUtil.getHSSFWorkbook(
                "住房保障验收通过名单",title,items,null,
                FileUtil.FILE_PATH,"住房保障验收通过名单"
        );
        String name = path.substring(path.lastIndexOf("/"));
        FileUtil.downloadFile(path,name,request,response);
        FileUtil.deleteFile(path);
        return "";
    }

    /**
     * 根据id删除公告
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delNotification")
    public String delNotification(Integer id) {
        Integer re = bulletinService.deleteByPrimaryKey(id);
        return re.toString();
    }
}
