package com.gzmusxxy.controller;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.annotation.MarketLogin;
import com.gzmusxxy.entity.IndustryNeed;
import com.gzmusxxy.entity.IndustryUser;
import com.gzmusxxy.service.IndustryNeedService;
import com.gzmusxxy.service.IndustryUserService;
import com.gzmusxxy.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Description 供销交流平台
 * @Author RAINEROSION
 * @Date 2019/9/17 21:38
 */
@Controller
@RequestMapping("/supply")
public class MarketController {

    @Autowired
    private IndustryUserService industryUserService;
    @Autowired
    private IndustryNeedService industryNeedService;

    /**
     * 供销平台首页
     *
     * @return
     */
    @RequestMapping(value = {"", "/"})
    public String index() {
        return "market/index";
    }

    /**
     * 登录方法
     *
     * @return
     */
    @RequestMapping(value = "/login")
    public String login() {
        return "market/login";
    }

    /**
     * 接收用户输入的数据
     * @param session
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/postLogin")
    @ResponseBody
    public String postLogin(HttpSession session, @RequestParam("username") String username,@RequestParam("password") String password) {
        IndustryUser login = industryUserService.login(username.trim(), password.trim());
        if(login == null){
            return "0";
        }else {
            session.setAttribute("user",login);
            return "1";
        }
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    @MarketLogin
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/supply/message?title=2";
    }

    /**
     * 登录表单
     * @return
     */
    @RequestMapping(value = "/register")
    public String register(){
        return "market/register";
    }

    /**
     * 提交用户注册信息
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/postRegister")
    @ResponseBody
    public String postRegister(@RequestParam("username") String username,@RequestParam("password") String password){
        IndustryUser industryUser = new IndustryUser();
        Random random = new Random();
        industryUser.setName("用户"+String.format("%04d",random.nextInt(9999)));
        industryUser.setUsername(username.trim());
        industryUser.setPassword((DigestUtils.md5DigestAsHex(password.trim().getBytes())));
        industryUser.setRegistertime(new Date());
        IndustryUser industryUser1 = industryUserService.selectUserByUserName(username.trim());
        if(industryUser1 != null){
            return "2";
        }
        int insert = industryUserService.insert(industryUser);
        if(insert >= 1){
            return "1";
        }
        return "0";
    }

    /**
     * 检查用户是否存在
     * @param username
     * @return
     */
    @RequestMapping(value = "/checkName")
    @ResponseBody
    public String checkName(@RequestParam("username") String username){
        IndustryUser industryUser = industryUserService.selectUserByUserName(username.trim());
        if(industryUser != null){
            return "1";
        }else{
            return "0";
        }
    }

    /**
     * 查询需求
     * @param page
     * @param type
     * @return
     */
    @RequestMapping(value = "/need")
    @ResponseBody
    public List<IndustryNeed> getNeed(@RequestParam("page") Integer page,@RequestParam("type") Integer type){
        System.out.println("类型为" + type+"页码"+page);
        PageInfo<IndustryNeed> industryNeedPageInfo = industryNeedService.selectByType(type, page);
        return industryNeedPageInfo.getList();
    }

    /**
     * 查询需求信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/needDetail/{id}")
    public String needDetail(@PathVariable("id") Integer id, Model model){
        IndustryNeed industryNeed = industryNeedService.selectByPrimaryKey(id);
        if(industryNeed == null){
            industryNeed = new IndustryNeed();
        }
        industryNeedService.updateReadNumber(id);
        model.addAttribute("industryNeed",industryNeed);
        return "market/needDetail";
    }

    /**
     * 用户中心
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/my")
    @MarketLogin
    public String user(HttpSession session,Model model){
        //获取登录用户的数据
        IndustryUser user = (IndustryUser)session.getAttribute("user");
        System.out.println(user);
        //统计商品数据的数量
        int goodsCount = industryNeedService.selectCountByUserIdAndType(user.getId(), 1);
        //统计需求数据的数量
        int needsCount = industryNeedService.selectCountByUserIdAndType(user.getId(), 2);
        //将数据保存后添加到模型中
        Hashtable<String, Integer> countList = new Hashtable<>();
        countList.put("goodsCount",goodsCount);
        countList.put("needsCount",needsCount);
        model.addAttribute("countList",countList);
        //将用户数据保存到模型
        model.addAttribute("user",user);
        return "market/person";
    }

    /**
     * 供销系统提交页面
     * @return
     */
    @MarketLogin
    @RequestMapping(value = "/addNeed")
    public String addNeed(){
        return "market/need";
    }

    /**
     * 修改用户个人信息
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/changeInfo")
    @MarketLogin
    public String changeInfo(HttpSession session,Model model){
        model.addAttribute("user",session.getAttribute("user"));
        return "market/changeInfo";
    }

    /**
     * 提交更新数据
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/postChangeInfo")
    @ResponseBody
    @MarketLogin
    public String postChangeInfo(HttpSession session,Model model,@RequestParam("username") String username,@RequestParam("phone") String phone){
        IndustryUser user = (IndustryUser)session.getAttribute("user");
        user.setName(username);
        user.setPhone(phone);
        int i = industryUserService.updateNameAndPhoneById(user);
        if(i >= 1){
            return "1";
        }else{
            return "0";
        }
    }

    @RequestMapping(value = "/postAddNeeds")
    public String postAddNeeds(HttpSession session,@RequestParam(value = "img",required = false) MultipartFile file,@RequestParam("type") Integer type,@RequestParam("content") String content){
        //获取session数据
        IndustryUser user = (IndustryUser)session.getAttribute("user");
        //保存数据到bean中
        IndustryNeed industryNeed = new IndustryNeed();
        industryNeed.setContent(content);
        industryNeed.setType(type);
        industryNeed.setUserid(user.getId());
        industryNeed.setUsername(user.getUsername());
        if(!file.isEmpty()){
            Map<String, String> fileNameMap = FileUtil.uploadFile(file, FileUtil.FILE_PATH + "market/");
            industryNeed.setFile("market/"+fileNameMap.get("fileName"));
        }

        industryNeed.setDate(new Date());
        //插入数据
        int insert = industryNeedService.insert(industryNeed);
        if(insert > 0){
            return "redirect:/supply/message?title=3";
        }else{
            return "redirect:/supply/message?title=4";
        }
    }
    @RequestMapping(value = "/message")
    public String message(){
        return "market/message";
    }
}
