package com.gzmusxxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.Admin;
import com.gzmusxxy.entity.XjhbInformation;
import com.gzmusxxy.entity.XjhbPerson;
import com.gzmusxxy.entity.XjhbProject;
import com.gzmusxxy.service.AdminService;
import com.gzmusxxy.service.XjhbInformationService;
import com.gzmusxxy.service.XjhbPersonService;
import com.gzmusxxy.service.XjhbProjectService;
import com.gzmusxxy.util.FileUtil;
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
import java.util.Date;

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
        model.addAttribute("pages",getPage(pageInfo.getPages(), pageNumber));
        return "admin/xjhb_project";
    }

    /**
     * 分页按钮
     * @param pages 总页数
     * @param pageNumber 当前页
     * @return 分页按钮
     */
    private int[] getPage(int pages,int pageNumber) {
        //固定5页
        final int page = 5;
        int numPage[];
        //总共页数
        int pageCount = pages;
        //判断是否需要省略
        if (pageCount > page) {
            numPage = new int[page];
            //两边都可以显示
            if (pageNumber - 2 > 0 && pageCount - 2 >= pageNumber) {
                for (int i = 0; i < numPage.length; i++) {
                    numPage[i] = pageNumber - 2 + i;
                }
            }else if (pageNumber - 2 > 0 && pageCount - 2 < pageNumber){
                //前面可以显示 后面不够
                for (int i = numPage.length-1; i >= 0; i--) {
                    numPage[i] = pageCount--;
                }
            }else {
                //后面可以显示 前面不够
                for (int i = 0; i < numPage.length; i++) {
                    numPage[i] = i + 1;
                }
            }
        }else {
            numPage = new int[pageCount];
            for (int i = 0; i < numPage.length; i++) {
                numPage[i] = i + 1;
            }
        }
        return numPage;
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
        model.addAttribute("pages",getPage(pageInfo.getPages(), pageNumber));
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
        //json.put("card",xjhbInformation.getOneCardSolution());
        json.put("createTime",createTime);
        json.put("project",xjhbProject);
        json.put("person",xjhbPerson);
        return json.toJSONString();
    }

    /**
     * 改变状态（通用）
     * @param id 申请书id
     * @param status 申请书status
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/status")
    public String status(int id, byte status) {
        XjhbInformation xjhbInformation = xjhbInformationService.selectByPrimaryKey(id);
        xjhbInformation.setStatus(status);
        xjhbInformationService.updateByPrimaryKey(xjhbInformation);
        return "";
    }

    /**
     * 待验收管理
     * @param model
     * @return
     */
    @RequestMapping(value = "/xjhbCheck")
    public String xjhbCheck(Model model,String name, @RequestParam("pageNumber") Integer pageNumber){
        PageInfo<XjhbInformation> pageInfo = xjhbInformationService.selectCheckByNameLike(name, pageNumber);
        System.out.println(pageInfo);
        //防止搜索栏bug
        if (name == null) {
            name = "";
        }
        model.addAttribute("name",name);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pages",getPage(pageInfo.getPages(), pageNumber));
        return "admin/xjhb_check";
    }

    /**
     * 验收通过查看
     * @param model model
     * @return return
     */
    @RequestMapping(value = "/xjhbAdopt")
    public String xjhbAdopt(Model model,String name, @RequestParam("pageNumber") Integer pageNumber){
        return "admin/xjhb_adopt";
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
        if (path.trim() == null || path.equals("null") || path.equals("")){
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
}
