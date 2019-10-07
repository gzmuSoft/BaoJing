package com.gzmusxxy.controller;

import com.gzmusxxy.annotation.IsLogin;
import com.gzmusxxy.common.JsonResult;
import com.gzmusxxy.entity.*;
import com.gzmusxxy.service.*;
import com.gzmusxxy.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 住房保障
 * @Author RAINEROSION
 * @Date 2019/10/3 21:45
 */
@Controller
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private BulletinService bulletinService;
    @Autowired
    private ZfTemplateService zfTemplateService;
    @Autowired
    private XjhbPersonService xjhbPersonService;
    @Autowired
    private ZfPhotoService zfPhotoService;
    @Autowired
    private ZfApplyService zfApplyService;

    /**
     * 住房保障首页
     *
     * @return
     */
    @IsLogin
    @RequestMapping(value = {"", "/"})
    public String index(Model model, HttpSession session) {
        String openid = session.getAttribute("openid").toString();
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(openid);
        //判断用户是否存在
        if (person == null) {
            XjhbPerson xjhbPerson = new XjhbPerson();
            //用户不存在创建用户
            xjhbPerson.setOpenid(openid);
            xjhbPerson.setCreateTime(new Date());
            xjhbPersonService.insert(xjhbPerson);
        }
        //查询住房保障公告信息
        List<Bulletin> bulletins = bulletinService.selectBySourceId(5);
        List<Map<String, Object>> list = new ArrayList<>();
        if (bulletins.size() > 0) {
            for (Bulletin bl : bulletins) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", bl.getId());
                map.put("title", bl.getTitle());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                map.put("time", simpleDateFormat.format(bl.getUpdateTime()));
                if (bl.getContent() != null) {
                    try {
                        map.put("content", new String(bl.getContent(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    map.put("content", "");
                }
                list.add(map);
            }
        }
        model.addAttribute("bulletins", list);
        return "house/index";
    }

    /**
     * 公告详细内容查看
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/detail")
    public String bulletinDetails(@RequestParam("id") Integer id, Model model) {
        //查询公告详细内容
        Bulletin bulletin = bulletinService.selectByPrimaryKey(id);
        HashMap<String, String> map = new HashMap<>();
        map.put("id", bulletin.getId().toString());
        map.put("title", bulletin.getTitle());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("time", simpleDateFormat.format(bulletin.getUpdateTime()));
        if (bulletin.getContent() != null) {
            try {
                map.put("content", new String(bulletin.getContent(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            map.put("content", "");
        }
        model.addAttribute("bulletin", map);
        return "house/detail";
    }

    /**
     * 用于下载模板文件的方法
     *
     * @param id
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/download")
    public void downloadTemplate(@RequestParam("id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        ZfTemplate zfTemplate = zfTemplateService.selectByPrimaryKey(id);
        FileUtil.downloadFile(zfTemplate.getTemplatePath(), zfTemplate.getTemplateName(), request, response);
    }

    /**
     * 危房改造申请
     *
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/apply")
    public String apply(HttpSession session, Model model) {
        String openid = session.getAttribute("openid").toString();
        //获取用户信息
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(openid);
        if (person == null || person.getName().trim().equals("") || person.getIdentity().trim().equals("") || person.getTelphone().trim().equals("")) {
            return "redirect:/house/user?supplement=true";
        }
        //获取申请书模板
        List<ZfTemplate> zfTemplates = zfTemplateService.selectAll();
        model.addAttribute("user", person);
        model.addAttribute("template", zfTemplates);
        return "house/apply";
    }

    /**
     * 用户个人信息
     *
     * @param session
     * @param model
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/user")
    public String user(HttpSession session, Model model) {
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(session.getAttribute("openid").toString());
        //给一个属性为空的对象，避免前台thymeleaf获取不到对象属性而报错
        if (person == null) {
            person = new XjhbPerson();
        }
        model.addAttribute("person", person);
        return "house/users";
    }

    /**
     * 更新用戶信息
     * @param session
     * @param name
     * @param identity
     * @param telphone
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/updateUserInfo")
    @ResponseBody
    public JsonResult updateUser(HttpSession session, @RequestParam("name") String name, @RequestParam("identity") String identity, @RequestParam("telphone") String telphone) {
        String openid = session.getAttribute("openid").toString();
        XjhbPerson personByOpenId = xjhbPersonService.findPersonByOpenId(openid);
        //获取当前登录用户的id
        Integer userid = personByOpenId.getId();
        JsonResult jsonResult = new JsonResult();
        XjhbPerson xjhbPerson = new XjhbPerson();
        //获取到的用户的信息
        xjhbPerson.setId(userid);
        xjhbPerson.setName(name);
        xjhbPerson.setIdentity(identity);
        xjhbPerson.setTelphone(telphone);
        //更新用户信息
        int i = xjhbPersonService.updateNameAndIdentifyAndTelphoneById(xjhbPerson);
        if (i > 0) {
            //用户信息更新成功
            jsonResult.setCode(1);
            jsonResult.setResult("ok");
            return jsonResult;
        }
        jsonResult.setCode(0);
        jsonResult.setResult("信息更新失败");
        return jsonResult;
    }

    /**
     * 通用下载
     *
     * @param name
     * @param path
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/downloadFile")
    public String download(String name, String path, HttpServletRequest request, HttpServletResponse response) {
        FileUtil.downloadFile(path, name, request, response);
        return "";
    }

    /**
     * 上传文件（通用）
     *
     * @param file file
     * @param path path
     * @param type 文件类型
     * @return return
     */

    @ResponseBody
    @RequestMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file, String path, String type) {
        if (path == null || path.equals("null") || path.equals("")) {
            return FileUtil.saveFile(file, null, type);
        }
        return FileUtil.saveFile(file, path, type);
    }

    /**
     * 保存用户提交的申请信息
     *
     * @param session
     * @param templateId
     * @param template
     * @param photo
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/postInfo")
    @ResponseBody
    public JsonResult saveApply(HttpSession session, @RequestParam("templateId") Integer templateId, @RequestParam("template") String template, @RequestParam("photo") String photo) {
        //获取登录用户信息
        String openid = session.getAttribute("openid").toString();
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(openid);

        JsonResult jsonResult = new JsonResult();
        ZfApply zfApply = new ZfApply();
        zfApply.setPersonId(person.getId());
        zfApply.setHousePhotosPath(photo);
        zfApply.setTemplatePath(template);
        zfApply.setTemplateId(templateId);
        zfApply.setStatus((byte) 1);
        //保存危改申请
        int insert = zfApplyService.insert(zfApply);
        if (insert > 0) {
            jsonResult.setCode(1);
            jsonResult.setResult("ok");
            return jsonResult;
        }
        jsonResult.setCode(0);
        jsonResult.setResult("申请信息提交失败");
        return jsonResult;
    }

    /**
     * 我的申請
     * @param session
     * @param model
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/myApply")
    public String myApply(HttpSession session, Model model) {
        String openid = session.getAttribute("openid").toString();
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(openid);
        Integer userid = person.getId();
        List<ZfApply> list = zfApplyService.selectByPersonId(userid);
        model.addAttribute("user", person);
        model.addAttribute("list", list);
        return "house/myapply";
    }

    /**
     * 重新申请
     *
     * @param model
     * @param session
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/reapply")
    public String reApply(Model model, HttpSession session, @RequestParam("id") Integer id) {
        String openid = session.getAttribute("openid").toString();
        //获取用户信息
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(openid);
        //获取申请书模板
        List<ZfTemplate> zfTemplates = zfTemplateService.selectAll();

        ZfApply zfApply = zfApplyService.selectByPrimaryKey(id);
        //判断状态
        if (zfApply.getStatus() == (byte)9 || zfApply.getStatus()== (byte) 3) {
            model.addAttribute("info", zfApply);
            model.addAttribute("user", person);
            model.addAttribute("template", zfTemplates);
            return "house/reapply";
        }
        model.addAttribute("msg", "当前状态无法完善信息！");
        return "house/msg";
    }

    /**
     * 更新用户信息
     *
     * @param id
     * @param templateId
     * @param template
     * @param photo
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/postUpdateInfo")
    @ResponseBody
    public JsonResult postUpdateInfo(@RequestParam("id") Integer id, @RequestParam("templateId") Integer templateId, @RequestParam("template") String template, @RequestParam("photo") String photo) {
        JsonResult jsonResult = new JsonResult();
        ZfApply zfApply = new ZfApply();
        zfApply.setId(id);
        zfApply.setHousePhotosPath(photo);
        zfApply.setTemplatePath(template);
        zfApply.setTemplateId(templateId);
        //状态置为1 审核中
        zfApply.setStatus((byte) 1);
        //更新用户数据
        int i = zfApplyService.updateByPrimaryKey(zfApply);
        if (i > 0) {
            jsonResult.setCode(1);
            jsonResult.setResult("ok");
            return jsonResult;
        }
        jsonResult.setCode(0);
        jsonResult.setResult("信息更新失败");
        return jsonResult;
    }

    /**
     * 施工照片上传页面
     *
     * @param applyid 照片的类型id 1：施工前 2：施工中 3：施工后
     * @param model
     * @param typeid
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/constructionPhoto")
    public String constructionPhoto(@RequestParam("applyid") Integer applyid, Model model, @RequestParam("typeid") Integer typeid) {
        String url = null;
        switch (typeid) {
            case 1:
                url = "house/photofront";
                break;
            case 2:
                url = "house/photocenter";
                break;
            case 3:
                url = "house/photoafter";
                break;
        }
        model.addAttribute("applyid", applyid);
        return url;
    }

    /**
     * 保存施工前图片
     *
     * @param applyid
     * @param typeid  照片的类型id 1：施工前 2：施工中 3：施工后
     * @param path
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/postPhotoFunction")
    @ResponseBody
    public JsonResult postPhotoFront(@RequestParam("applyid") Integer applyid, @RequestParam("typeid") Integer typeid, @RequestParam("path") String path) {
        JsonResult jsonResult = new JsonResult();
        ZfPhoto zfPhoto1 = zfPhotoService.selectByApplyId(applyid);
        //判断是否为空
        if (zfPhoto1 == null) {
            ZfPhoto zfPhoto = new ZfPhoto();
            zfPhoto.setApplyId(applyid);
            zfPhoto.setIsUpload(0);
            //创建一条
            zfPhotoService.insert(zfPhoto);
        }
        ZfPhoto zfPhoto = null;
        ZfApply zfApply = new ZfApply();
        zfApply.setId(applyid);

        switch (typeid) {
            case 1:
                zfPhoto = new ZfPhoto();
                zfPhoto.setPhotoPathFront(path);
                zfPhoto.setIsUpload(1);
                zfApply.setStatus((byte) 4);
                break;
            case 2:
                zfPhoto = new ZfPhoto();
                zfPhoto.setPhotoPathCenter(path);
                zfPhoto.setIsUpload(1);
                //zfApply.setStatus((byte) 5);
                break;
            case 3:
                zfPhoto = new ZfPhoto();
                zfPhoto.setPhotoPathAfter(path);
                zfPhoto.setIsUpload(1);
                //zfApply.setStatus((byte) 6);
                break;
        }
        zfPhoto.setApplyId(applyid);
        int insert = zfPhotoService.updateByApplyId(zfPhoto);
        if(typeid == 1){
            zfApplyService.updateByPrimaryKey(zfApply);
        }
        if (insert > 0) {
            jsonResult.setCode(1);
            jsonResult.setResult("ok");
            return jsonResult;
        }
        jsonResult.setCode(1);
        jsonResult.setResult("照片信息保存失败");
        return jsonResult;
    }

    /**
     * 申请验收
     * @param id
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/acceptance")
    @ResponseBody
    public JsonResult acceptance(@RequestParam("id") Integer id){
        JsonResult jsonResult = new JsonResult();
        ZfApply zfApply = zfApplyService.selectByPrimaryKey(id);
        //判断状态
        if(zfApply.getStatus().equals((byte) 7)){
            ZfApply zfApply1 = new ZfApply();
            zfApply1.setStatus((byte) 8);
            zfApply1.setId(id);
            //更新数据
            int i = zfApplyService.updateByPrimaryKey(zfApply1);
            if(i > 0 ){
                jsonResult.setCode(1);
                jsonResult.setResult("ok");
                return jsonResult;
            }
        }
        jsonResult.setCode(0);
        jsonResult.setResult("申请验收失败");
        return jsonResult;
    }
}