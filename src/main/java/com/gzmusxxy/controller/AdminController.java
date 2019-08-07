package com.gzmusxxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.Admin;
import com.gzmusxxy.entity.XjhbProject;
import com.gzmusxxy.mapper.XjhbProjectMapper;
import com.gzmusxxy.service.AdminService;
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

    @RequestMapping(value = "/login")
        public String login(HttpSession session) {
        session.removeAttribute("admin");
        return "admin/login";
    }

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

    @ResponseBody
    @RequestMapping(value = "/verify")
    public String verify(String username, String password, HttpSession session) {
        Integer id = adminService.verify(username,password);
        if (id > 0){
            session.setAttribute("admin",adminService.selectByPrimaryKey(id));
            return id.toString();
        }else
            return "输入的账号或密码错误！";
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "admin/index";
    }

    /**
     * 项目管理
     * @param model
     * @return
     */
    @RequestMapping(value = "/xjhbProject")
    public String xjhbProject(Model model, String name, @RequestParam("pageNumber") Integer pageNumber){
        PageInfo<XjhbProject> pageInfo = xjhbProjectService.selectProjectByNameLike(name, pageNumber);
        model.addAttribute("projectList",pageInfo.getList());
        model.addAttribute("pageNumber",pageNumber);
        return "admin/xjhb_project";
    }

    /**
     * 申请书管理
     * @param model
     * @return
     */
    @RequestMapping(value = "/xjhbApply")
    public String xjhbApply(Model model){
        return "admin/xjhb_apply";
    }

    /**
     * 待验收管理
     * @param model
     * @return
     */
    @RequestMapping(value = "/xjhbCheck")
    public String xjhbCheck(Model model){
        return "admin/xjhb_check";
    }

    /**
     * 验收通过查看
     * @param model
     * @return
     */
    @RequestMapping(value = "/xjhbAdopt")
    public String xjhbAdopt(Model model){
        return "admin/xjhb_adopt";
    }

    /**
     * 管理员信息管理
     * @param model
     * @return
     */
    @RequestMapping(value = "/message")
    public String message(Model model){
        return "admin/admin_message";
    }

    /**
     * 处理前台时间传到后台的格式
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 添加项目
     * @param xjhbProject
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addProject")
    public String addProject(XjhbProject xjhbProject) {
        Integer re = xjhbProjectService.insert(xjhbProject);
        return re.toString();
    }

    /**
     * 删除项目
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/delProject")
    public String delProject(int id) {
        Integer re = xjhbProjectService.deleteByPrimaryKey(id);
        return re.toString();
    }

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
     * 上传文件
     * @param file  file
     * @param path  path
     * @param type  文件类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file,String path, String type) {
        if (path.trim() == null || path.equals("null") || path.equals("")){
            return FileUtil.saveFile(file,null,type);
        }
        return FileUtil.saveFile(file,path,type);
    }

    @ResponseBody
    @RequestMapping(value= "/download")
    public String download(int id, HttpServletRequest request, HttpServletResponse response){
        XjhbProject xjhbProject = xjhbProjectService.selectByPrimaryKey(id);
        FileUtil.downloadFile(xjhbProject.getApplicationTemplate(),xjhbProject.getApplicationTemplateName(),request,response);
        return "";
    }
}
