package com.gzmusxxy.controller;

import com.gzmusxxy.annotation.IsLogin;
import com.gzmusxxy.common.JsonResult;
import com.gzmusxxy.entity.*;
import com.gzmusxxy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 教育保障
 * @Author RAINEROSION
 * @Date 2019/10/3 21:36
 */
@Controller
@RequestMapping(value = "/education")
public class EducationController {
    @Autowired
    private JyApplyService jyApplyService;
    @Autowired
    private BulletinService bulletinService;
    @Autowired
    private JyStudentService jyStudentService;
    /**
     * 教育保障首页
     *
     * @return
     */
    @RequestMapping(value = {"", "/"})
    public String index(Model model) {
        //查询公告数据
        List<Bulletin> bulletins = bulletinService.selectBySourceId(4);
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
        return "education/index";
    }

    /**
     * 教育保障详情
     *
     * @param id    id
     * @param model model
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
        return "education/detail";
    }

    /**
     * 我的申请
     *
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/apply")
    public String apply() {
        return "education/apply";
    }

    /**
     * 根据身份证号查询学生信息
     *
     * @param identity 身份证号
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/findIdentity")
    @ResponseBody
    public JyStudent findIdentity(@RequestParam(value = "identity", required = false) String identity) {
        return this.jyStudentService.selectByIdentity(identity);
    }

    /**
     * 提交申请
     * @param jyApply 申请信息
     * @param session session
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/insertApply")
    @ResponseBody
    public JsonResult insertApply(JyApply jyApply,HttpSession session){
        JsonResult jsonResult = new JsonResult();
        try {
            Byte status = 1;
            // 设置状态
            jyApply.setStatus(status);
            //获取用户session
            String openid = session.getAttribute("openid").toString();
            // 设置openid
            jyApply.setOpenId(openid);
            int insert = this.jyApplyService.insert(jyApply);
            if(insert > 0){
                jsonResult.setCode(1);
                jsonResult.setResult("提交申请成功");
                return jsonResult;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        jsonResult.setCode(0);
        jsonResult.setResult("提交申请失败");
        return jsonResult;
    }
    @IsLogin
    @RequestMapping(value = "/myApply")
    public String myApply(HttpSession session, Model model) {
        //获取用户session
        String openid = session.getAttribute("openid").toString();
        //通过openId获取用户信息
        List<JyApply> applyList = this.jyApplyService.findStudentByOpenId(openid);
        model.addAttribute("list", applyList);
         // 返回到我的申请页面
        return "education/myapply";
    }

    /**
     * 重新申请
     * @param id id
     * @param model model
     * @param session session
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/reApply")
    public String reApply(@RequestParam("id") Integer id,@RequestParam("student_id") Integer student_id, Model model, HttpSession session){
        String openid = session.getAttribute("openid").toString();
        //通过openId获取用户信息
        JyStudent jyStudent = jyStudentService.selectByPrimaryKey(student_id);
        model.addAttribute("user", jyStudent);
        model.addAttribute("yid", id);
        return "education/reapply";
    }

    /**
     * 更新申请信息
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/updateApply")
    @ResponseBody
    public JsonResult updateApply(JyApply jyApply,HttpSession session){
        JsonResult jsonResult = new JsonResult();
        try {
            Byte status = 1;
            // 设置状态
            jyApply.setStatus(status);
            //获取用户session
            String openid = session.getAttribute("openid").toString();
            // 设置openid
            jyApply.setOpenId(openid);
            int updateByPrimaryKey = this.jyApplyService.updateByPrimaryKey(jyApply);
            if(updateByPrimaryKey > 0){
                jsonResult.setCode(1);
                jsonResult.setResult("提交申请成功");
                return jsonResult;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        jsonResult.setCode(0);
        jsonResult.setResult("提交申请失败");
        return jsonResult;
    }
}
