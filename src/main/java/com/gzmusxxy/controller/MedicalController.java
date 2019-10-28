package com.gzmusxxy.controller;

import com.gzmusxxy.annotation.IsLogin;
import com.gzmusxxy.common.JsonResult;
import com.gzmusxxy.entity.Admin;
import com.gzmusxxy.entity.Bulletin;
import com.gzmusxxy.entity.XjhbPerson;
import com.gzmusxxy.entity.YlGuarantee;
import com.gzmusxxy.service.AdminService;
import com.gzmusxxy.service.BulletinService;
import com.gzmusxxy.service.XjhbPersonService;
import com.gzmusxxy.service.YlGuaranteeService;
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
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 医疗保障
 * @Author RAINEROSION
 * @Date 2019/9/21 18:29
 */
@Controller
@RequestMapping("/medical")
public class MedicalController {

    @Autowired
    private YlGuaranteeService ylGuaranteeService;

    @Autowired
    private BulletinService bulletinService;

    @Autowired
    private XjhbPersonService personService;

    @Autowired
    private AdminService adminService;

    /**
     * 医疗保障前台首页
     *
     * @return
     */
    @RequestMapping(value = {"", "/"})
    public String index(Model model) {
        //查询公告数据
        List<Bulletin> bulletins = bulletinService.selectBySourceId(3);
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
        return "medical/index";
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
        return "medical/detail";
    }

    /**
     * 我申请的
     *
     * @param session
     * @param model
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/myApply")
    public String myApply(HttpSession session, Model model) {
        //获取用户session
        String openid = session.getAttribute("openid").toString();
        //通过openId获取用户信息
        XjhbPerson personByOpenId = personService.findPersonByOpenId(openid);
        //获取用户id
        Integer userId = personByOpenId.getId();
        List<YlGuarantee> ylGuarantees = ylGuaranteeService.selectByPersonId(userId);
        model.addAttribute("user", personByOpenId);
        model.addAttribute("list", ylGuarantees);
        return "medical/myapply";
    }

    /**
     * 项目申请页面
     *
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/apply")
    public String apply(HttpSession session, Model model) {
        //获取用户session
        String openid = session.getAttribute("openid").toString();
        //通过openId获取用户信息
        XjhbPerson personByOpenId = personService.findPersonByOpenId(openid);
        if (personByOpenId == null) {
            //用户不存在创建用户
            XjhbPerson person = new XjhbPerson();
            person.setOpenid(openid);
            person.setCreateTime(new Date());
            personService.insert(person);
            //创建一个对象
            personByOpenId = new XjhbPerson();
        }
        model.addAttribute("user", personByOpenId);
        return "medical/apply";
    }

    /**
     * 提交数据
     *
     * @param ylGuarantee
     * @param session
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/postInfo")
    @ResponseBody
    public JsonResult postInfo(YlGuarantee ylGuarantee, HttpSession session) {
        //判断用户是否登录
        String openid = session.getAttribute("openid").toString();
        //通过openId获取用户信息
        XjhbPerson personByOpenId = personService.findPersonByOpenId(openid);
        JsonResult jsonResult = new JsonResult();
        //获取提交的信息
        ylGuarantee.setStatus((byte) 0);
        ylGuarantee.setPersonId(personByOpenId.getId());
        ylGuarantee.setApplicationTime(new Date());

        //更新用户信息
        XjhbPerson xjhbPerson = new XjhbPerson();
        xjhbPerson.setId(personByOpenId.getId());
        xjhbPerson.setName(ylGuarantee.getName());
        xjhbPerson.setIdentity(ylGuarantee.getIdentity());
        int i = personService.updateNameAndIdentityById(xjhbPerson);
        if (i >= 1) {
            int insert = ylGuaranteeService.insert(ylGuarantee);
            if (insert >= 1) {
                jsonResult.setResult("ok");
                jsonResult.setCode(1);
                return jsonResult;
            }
        }
        jsonResult.setResult("信息提交失败！");
        jsonResult.setCode(0);
        return jsonResult;
    }

    /**
     * 获取账户银行卡信息
     *
     * @return
     */
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/getBankInfo")
    public Admin getBankInfo() {
        return adminService.selectByRole(4);
    }

    /**
     * 确认缴费
     *
     * @param id
     * @param remark
     * @param session
     * @return
     */
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/payment")
    public JsonResult payment(@RequestParam("id") Integer id, @RequestParam("remark") String remark, HttpSession session) {
        String openid = session.getAttribute("openid").toString();
        //获得用户信息
        XjhbPerson person = personService.findPersonByOpenId(openid);
        //用于保存结果
        JsonResult jsonResult = new JsonResult();
        if (person != null && person.getId() != 0) {
            //更新当前状态为缴费待验证
            int i = ylGuaranteeService.updateRemarkAndSatusByIdAndPersonId(id, remark, 1, person.getId());
            if (i > 0) {
                //更新成功
                jsonResult.setCode(1);
                jsonResult.setResult("ok");
                return jsonResult;
            } else {
                jsonResult.setResult("状态不正常，无法缴费！");
                jsonResult.setCode(0);
                return jsonResult;
            }
        }
        jsonResult.setCode(0);
        jsonResult.setResult("用户信息不完整！");
        return jsonResult;
    }

    /**
     * 显示申请补助页面
     *
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/applySubsidy")
    public String appluSubsudy(@RequestParam("id") Integer id, Model model, HttpSession session) {
        String openid = session.getAttribute("openid").toString();
        //获得用户信息
        XjhbPerson person = personService.findPersonByOpenId(openid);
        model.addAttribute("user", person);
        YlGuarantee ylGuarantee = ylGuaranteeService.selectByPrimaryKey(id);
        if (ylGuarantee == null) {
            ylGuarantee = new YlGuarantee();
        }
        model.addAttribute("ylGuarantee", ylGuarantee);
        model.addAttribute("user", person);
        return "medical/subsidy";
    }

    /**
     * 补充用户信息页面
     *
     * @param id
     * @param model
     * @param session
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/reApply")
    public String reApply(@RequestParam("id") Integer id, Model model, HttpSession session) {
        String openid = session.getAttribute("openid").toString();
        //获得用户信息
        XjhbPerson person = personService.findPersonByOpenId(openid);
        model.addAttribute("user", person);
        model.addAttribute("yid", id);
        return "medical/reapply";
    }

    /**
     * 提交更新信息
     *
     * @param name
     * @param identity
     * @param session
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/postUpdateInfo")
    @ResponseBody
    public JsonResult postUpdateInfo(@RequestParam("id") Integer id, @RequestParam("name") String name, @RequestParam("identity") String identity, HttpSession session) {
        String openid = session.getAttribute("openid").toString();
        //获得用户信息
        XjhbPerson person = personService.findPersonByOpenId(openid);
        JsonResult jsonResult = new JsonResult();
        XjhbPerson xjhbPerson = new XjhbPerson();
        xjhbPerson.setName(name);
        xjhbPerson.setIdentity(identity);
        xjhbPerson.setId(person.getId());
        int i = personService.updateNameAndIdentityById(xjhbPerson);
        if (i > 0) {
            int i1 = ylGuaranteeService.updateStatusById((byte) 1, id);
            jsonResult.setCode(1);
            jsonResult.setResult("ok");
            return jsonResult;
        }
        jsonResult.setResult("更新失败");
        jsonResult.setCode(0);
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
     * 上传身份证正反面照片
     *
     * @param file
     * @param backPath
     * @param frontPath
     * @param type
     * @return
     */
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/upIdCard")
    public String upIdCard(@RequestParam("file") MultipartFile file, String backPath, String frontPath, String fileZip, String type) {
        if (backPath != null && frontPath != null && fileZip != null) {
            return FileUtil.saveFile(file, null, type);
        } else {
            if (backPath != null) {
                return FileUtil.saveFile(file, backPath, type);
            } else if (frontPath != null) {
                return FileUtil.saveFile(file, frontPath, type);
            } else {
                return FileUtil.saveFile(file, fileZip, type);
            }
        }
    }

    /**
     * 补助申请
     *
     * @param session
     * @param id
     * @param dataZip
     * @param frontPath
     * @param backPath
     * @param card
     * @return
     */
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/postApplyForSubsidy")
    public JsonResult postApplyForSubsidy(@RequestParam("id") Integer id, @RequestParam("dataZip") String dataZip, @RequestParam("frontPath") String frontPath, @RequestParam("backPath") String backPath, @RequestParam("card") String card, HttpSession session) {
        String openid = session.getAttribute("openid").toString();
        //获得用户信息
        XjhbPerson person = personService.findPersonByOpenId(openid);
        JsonResult jsonResult = new JsonResult();
        //用户身份证信息
        XjhbPerson xjhbPerson = new XjhbPerson();
        xjhbPerson.setIdCardFront(frontPath);
        xjhbPerson.setIdCardReverse(backPath);
        xjhbPerson.setId(person.getId());
        //等待更新的信息
        YlGuarantee ylGuarantee = new YlGuarantee();
        ylGuarantee.setId(id);
        ylGuarantee.setCard(card);
        ylGuarantee.setStatus((byte) 4);
        ylGuarantee.setDataZip(dataZip);
        int i1 = ylGuaranteeService.updateByPrimaryKey(ylGuarantee);
        if (i1 > 0) {
            //更新身份证信息
            int i = personService.updateIdCardFrontAndIdCardReverseById(xjhbPerson);
            if (i > 0) {
                jsonResult.setCode(1);
                jsonResult.setResult("ok");
                return jsonResult;
            }
        }
        jsonResult.setCode(0);
        jsonResult.setResult("更新失败");
        return jsonResult;
    }
}