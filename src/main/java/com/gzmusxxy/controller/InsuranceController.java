package com.gzmusxxy.controller;

import com.gzmusxxy.annotation.IsLogin;
import com.gzmusxxy.common.JsonResult;
import com.gzmusxxy.entity.*;
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

    @Autowired
    private BulletinService bulletinService;

    @Autowired
    private AdminService adminService;

    @IsLogin
    @RequestMapping(value = {"", "/"})
    public String index(Model model) {
        Bulletin bulletin = bulletinService.selectBySourceId(2);
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
        return "insurance/index";
    }

    /**
     * 申请项目
     *
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/apply")
    public String apply(Model model, HttpSession session) {
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
        } else {
            //用户不存在创建用户
            XjhbPerson person = new XjhbPerson();
            person.setOpenid(openid);
            person.setCreateTime(new Date());
            xjhbPersonService.insert(person);
            //创建用户以后跳转到补充信息页面
            return "redirect:/insurance/user?supplement=true";
        }
        //用户资料填写完整，查询项目资料
        List<BxProject> bxProjects = bxProjectService.selectEffective();
        model.addAttribute("list", bxProjects);
        model.addAttribute("bulletin",bulletinService.selectBySourceId(2));
        return "insurance/apply";
    }

    /**
     * 验证用户信息是否完整
     *
     * @param session
     * @return
     */
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/verifyUserInfo")
    public JsonResult verifyUserInfo(HttpSession session) {
        JsonResult jsonResult = new JsonResult();
        //判断用户是否登录
        String openid = session.getAttribute("openid").toString();
        //通过openId获取用户信息
        XjhbPerson personByOpenId = xjhbPersonService.findPersonByOpenId(openid);
        //判断用户信息是否为空
        if (personByOpenId != null) {
            //再次判断用户的信息收否填写完全
            if (personByOpenId.getIdentity() == null || personByOpenId.getName() == null || personByOpenId.getTelphone() == null || personByOpenId.getOneCardSolution() == null || personByOpenId.getPoverty() == null) {
                //所需要的资料未完全填写，返回数据
                jsonResult.setCode(0);
                jsonResult.setResult("请先补充资料！！");
                return jsonResult;
            }
        } else {
            //创建用户
            XjhbPerson person = new XjhbPerson();
            person.setOpenid(openid);
            person.setCreateTime(new Date());
            xjhbPersonService.insert(person);
            //补充资料
            jsonResult.setCode(0);
            jsonResult.setResult("请先补充资料！！");
            return jsonResult;
        }
        jsonResult.setCode(1);
        jsonResult.setResult("ok");
        return jsonResult;
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
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file, String path, String type) {
        if (path == null || path.equals("null") || path.equals("")) {
            return FileUtil.saveFile(file, null, type);
        }
        return FileUtil.saveFile(file, path, type);
    }

    /**
     * 保存保险资料
     *
     * @return
     */
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/saveInsurance")
    public JsonResult saveInsurance(@RequestParam("projectId") Integer projectId, @RequestParam("number") Integer number,@RequestParam("totalPrice") Double total, HttpSession session) {
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(session.getAttribute("openid").toString());
        BxInsurance bxInsurance = new BxInsurance();
        //设置默认的状态为待审核
        bxInsurance.setStatus((byte) 1);
        bxInsurance.setProjectId(projectId);
        bxInsurance.setBuyNumber(number);
        bxInsurance.setPersonId(person.getId());
        bxInsurance.setPayCost((byte) 0);
        bxInsurance.setTotalPrice(total);
        bxInsurance.setCreateTime(new Date());
        int insert = bxInsuranceService.insert(bxInsurance);
        JsonResult jsonResult = new JsonResult();
        if (insert <= 0) {
            jsonResult.setCode(0);
            jsonResult.setResult("数据提交失败，请重试！");
            return jsonResult;
        }
        jsonResult.setCode(1);
        jsonResult.setResult("ok");
        //发送请求审核邮件
        new Thread(){
            @Override
            public void run() {
                List<String> emails = adminService.selectEmailByRole(1,3);
                BxProject bxProject = bxProjectService.selectByPrimaryKey(projectId);
                for (String email:
                        emails) {
                    if (email != null){
                        MailUtil.sendMail("申请审核",
                                person.getName() + "的" +
                                        bxProject.getName() + "项目申请审核，请及时处理。",email
                        );
                    }
                }
                super.run();
            }
        }.start();
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
        List<BxInsurance> bxInsurancePageInfo = bxInsuranceService.selectInsuranceByPersonId(person.getId());
        //查询银行账户信息
        Admin admin = adminService.selectByRole(3);
        if(admin == null){
            admin = new Admin();
        }
        model.addAttribute("bank",admin.getBank());
        model.addAttribute("payee",admin.getPayee());
        model.addAttribute("cardNumber",admin.getCardNumber());
        model.addAttribute("dataList", bxInsurancePageInfo);
        return "insurance/myInsurance";
    }

    /**
     * 进入保险理赔申请页面
     *
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
     *
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
                //发送请求理赔申请
                new Thread(){
                    @Override
                    public void run() {
                        //理赔申请 发送邮件
                        List<String> emails = adminService.selectEmailByRole(1,3);
                        BxInsurance bxInsurance1 = bxInsuranceService.selectByPrimaryKey(bxInsurance.getId());
                        BxProject bxProject = bxProjectService.selectByPrimaryKey(bxInsurance1.getProjectId());
                        for (String email:
                                emails) {
                            if (email != null){
                                MailUtil.sendMail("申请理赔",
                                        person.getName() + "的" +
                                                bxProject.getName() + "项目申请理赔，请及时处理。",email
                                );
                            }
                        }
                        super.run();
                    }
                }.start();
                return jsonResult;
            }
        }
        jsonResult.setCode(0);
        jsonResult.setResult("失败");
        return jsonResult;
    }

    /**
     * 用户资料管理
     *
     * @param session
     * @param model
     * @return
     */
    @IsLogin
    @RequestMapping(value = "/user")
    public String user(HttpSession session, Model model) {
        String openId = session.getAttribute("openid").toString();
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(openId);
        model.addAttribute("person", person);
        return "insurance/users";
    }

    /**
     * users页面上传身份证正反面照片
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
    public String upIdCard(@RequestParam("file") MultipartFile file, String backPath, String frontPath, String type) {
        if (backPath != null && frontPath != null) {
            return FileUtil.saveFile(file, null, type);
        } else {
            if (backPath != null) {
                return FileUtil.saveFile(file, backPath, type);
            } else {
                return FileUtil.saveFile(file, frontPath, type);
            }
        }
    }

    /**
     * 修改资料
     * 因为一开始登录时自动创建用户
     *
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

    /**
     * 更新状态为缴费待验证
     *
     * @param id
     * @param session
     * @return
     */
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/payment")
    public JsonResult payment(@RequestParam("id") Integer id, HttpSession session) {
        String openid = session.getAttribute("openid").toString();
        //获得用户信息
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(openid);
        //用于保存结果
        JsonResult jsonResult = new JsonResult();
        if (person != null && person.getId() != 0) {
            //更新当前状态为缴费待验证
            int i = bxInsuranceService.updatePayCostByIdAndPersonId(person.getId(), id, (byte) 2);
            if (i > 0) {
                //更新成功
                jsonResult.setCode(1);
                jsonResult.setResult("ok");
                return jsonResult;
            } else {
                jsonResult.setResult("保险状态不正常，无法缴费！");
                jsonResult.setCode(0);
                return jsonResult;
            }
        }
        jsonResult.setCode(0);
        jsonResult.setResult("用户信息不完整！");
        return jsonResult;
    }

    @IsLogin
    @RequestMapping(value = "/updateInsurance")
    public String getBxInsurance(@RequestParam("id") Integer id,Model model){
        BxInsurance bxInsurance = bxInsuranceService.selectByPrimaryKey(id);
        if(bxInsurance == null){
            model.addAttribute("msg","查无数据！");
            return "insurance/msg";
        }
        if(!bxInsurance.getStatus().equals((byte) 3)){
            model.addAttribute("msg","当前保险状态无法修改！");
            return "insurance/msg";
        }
        //判断时间
        BxProject bxProject = bxProjectService.selectByPrimaryKey(bxInsurance.getProjectId());
        Date startTime = bxProject.getStartTime();
        Date endTime = bxProject.getEndTime();
        Date now = new Date();
        //now.after(startTime) && now.before(endTime)
        if(startTime.after(now) || endTime.before(now)){
            //时间不满足条件
            model.addAttribute("msg","当前时间不可修改申请，可能已到期！");
            return "insurance/msg";
        }
        List<BxProject> bxProjects = bxProjectService.selectEffective();
        model.addAttribute("list", bxProjects);
        model.addAttribute("insurance",bxInsurance);
        return "insurance/reApply";
    }

    /**
     * 重新申请：更新用户保险信息
     * @param number
     * @param id
     * @param session
     * @return
     */
    @IsLogin
    @ResponseBody
    @RequestMapping(value = "/postReApply")
    public JsonResult reApply(@RequestParam("number") Integer number,@RequestParam("id") Integer id,@RequestParam("totalPrice") Double total,HttpSession session){
        JsonResult jsonResult = new JsonResult();
        String openid = session.getAttribute("openid").toString();
        XjhbPerson person = xjhbPersonService.findPersonByOpenId(openid);
        if(person != null && person.getId() > 0){
            BxInsurance bxInsurance = new BxInsurance();
            bxInsurance.setId(id);
            bxInsurance.setStatus((byte)1);
            bxInsurance.setPersonId(person.getId());
            bxInsurance.setBuyNumber(number);
            bxInsurance.setTotalPrice(total);
            int i = bxInsuranceService.updateByIdAndPersonId(bxInsurance);
            if(i > 0){
                jsonResult.setCode(1);
                jsonResult.setResult("ok");
                return jsonResult;
            }

        }else{
            jsonResult.setCode(0);
            jsonResult.setResult("无法获取到用户的信息");
            return jsonResult;
        }
        jsonResult.setResult("更新失败");
        jsonResult.setCode(0);
        return jsonResult;
    }
}
