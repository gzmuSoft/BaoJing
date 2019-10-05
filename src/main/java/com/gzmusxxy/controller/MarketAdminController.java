package com.gzmusxxy.controller;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.annotation.AdminLogin;
import com.gzmusxxy.common.JsonResult;
import com.gzmusxxy.entity.IndustryNeed;
import com.gzmusxxy.entity.IndustryUser;
import com.gzmusxxy.service.IndustryNeedService;
import com.gzmusxxy.service.IndustryUserService;
import com.gzmusxxy.service.MarketAdminService;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author QFMX
 * @Date  2019/9/21 18:38
 * @Description 供销交流平台后台管理
 */
@Controller
@RequestMapping("/supplyAdmin")
public class MarketAdminController {
    @Autowired
    private IndustryUserService industryUserService;
    @Autowired
    private IndustryNeedService industryNeedService;
    @Autowired
    private MarketAdminService marketAdminService;

    /**
     * 删除用户(暂不启用)
     * @param id 用户id
     * @return 成功返回1，失败返回0
     */
    @AdminLogin
    @RequestMapping(value = "/deleteUser")
    @ResponseBody
    public JsonResult deleteUser(@RequestParam("id") Integer id){
        JsonResult jsonResult = new JsonResult();
        try {
            int delete = this.industryUserService.deleteByPrimaryKey(id);
            if(delete > 0){
                jsonResult.setCode(1);
                jsonResult.setResult("删除当前用户成功");
                return jsonResult;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        jsonResult.setCode(0);
        jsonResult.setResult("删除当前用户失败");
        return jsonResult;
    }
    /**
     * 更新（编辑）用户
     * @param user 用户信息
     * @return 成功返回0，失败返回1
     */
    @AdminLogin
    @RequestMapping(value = "/updateUser")
    @ResponseBody
    public JsonResult updateUser(IndustryUser user){
        JsonResult jsonResult = new JsonResult();
        try {
            int update = this.industryUserService.updateById(user);
            if (update > 0){
                jsonResult.setCode(1);
                jsonResult.setResult("更新成功");
                return jsonResult;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        jsonResult.setCode(0);
        jsonResult.setResult("更新失败");
        return jsonResult;
    }

    /**
     * 多条件分页查询
     * @param pageNumber 页码
     * @param model model
     * @param industryUser 分页数据
     * @return 返回到页面
     */
    @AdminLogin
    @RequestMapping(value = "/getUserByPage")
    public String getUserByPage(@RequestParam("pageNumber") Integer pageNumber, Model model,IndustryUser industryUser){
        if(industryUser.getUsername() != "" || industryUser.getUsername().trim() != null){
            PageInfo<IndustryUser> userByName = this.marketAdminService.getUserByName(pageNumber > 0 ? pageNumber : 1, industryUser);
            model.addAttribute("pageInfo",userByName);
            model.addAttribute("pages", PageUtil.getPage(userByName.getPages(),pageNumber));
            return "marketAdmin/user";
        }
        PageInfo<IndustryUser> pageInfo = this.marketAdminService.getUser(pageNumber>0?pageNumber:1);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pages", PageUtil.getPage(pageInfo.getPages(),pageNumber));
        return "marketAdmin/user";
    }

    /**
     * 根据用户id查询用户信息
     * @param id 用户编号
     * @return 返回的用户管理页面
     */
    @AdminLogin
    @RequestMapping(value = "/getUserById")
    @ResponseBody
    public IndustryUser getUserById(@RequestParam("id") Integer id){
        IndustryUser industryUser = this.industryUserService.selectByPrimaryKey(id);
        return industryUser;
    }
    /**
     *  需求分页查询
     * @param pageNumber 页码
     * @param model model
     * @return 返回分页信息到分页页面
     */
    @AdminLogin
    @RequestMapping(value = "/getNeedByPages")
    public String getNeedByPages(@RequestParam("pageNumber") Integer pageNumber, Model model){
        PageInfo<IndustryNeed> pageInfo = this.marketAdminService.getNeed(pageNumber > 0 ? pageNumber : 1);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pages", PageUtil.getPage(pageInfo.getPages(),pageNumber));
        return "marketAdmin/need";
    }

    /**
     * 根据供需编号查询内容
     * @param id 编号
     * @return IndustryNeed对象
     */
    @AdminLogin
    @RequestMapping(value = "/getNeedById")
    @ResponseBody
    public IndustryNeed getNeedById(@RequestParam("id") Integer id){
       return this.industryNeedService.selectByPrimaryKey(id);
    }
    @AdminLogin
    @RequestMapping(value = "/deleteNeedById")
    @ResponseBody
    public JsonResult deleteNeedById(@RequestParam("id") Integer id){
        JsonResult jsonResult = new JsonResult();
        try {
            int delete = this.industryNeedService.deleteByPrimaryKey(id);
            if(delete > 0){
                jsonResult.setCode(1);
                jsonResult.setResult("删除成功");
                return jsonResult;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        jsonResult.setCode(0);
        jsonResult.setResult("删除失败");
        return jsonResult;
    }

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 返回用户信息
     */
    @AdminLogin
    @RequestMapping(value = "/getUserByName")
    @ResponseBody
    public IndustryUser getUserByName(@RequestParam("username") String username){
        return this.industryUserService.selectUserByUserName(username);
    }

    /**
     * 条件分页查询
     * @param industryNeed 条件
     * @param pageNumber 页码
     * @return 分页分页信息
     */
    @AdminLogin
    @RequestMapping(value = "/getNeedByPage")
    public String getNeedByPage(IndustryNeed industryNeed,@RequestParam("pageNumber") Integer pageNumber,Model model){
        if(industryNeed.getUsername() != "" || industryNeed.getUsername().trim() != null){
            PageInfo<IndustryNeed> needByNameType = this.marketAdminService.getNeedByNameType(pageNumber > 0 ? pageNumber : 1, industryNeed);
            model.addAttribute("pageInfo",needByNameType);
            model.addAttribute("pages", PageUtil.getPage(needByNameType.getPages(),pageNumber));
            return "marketAdmin/need";
        }
        PageInfo<IndustryUser> user = this.marketAdminService.getUser(pageNumber > 0 ? pageNumber : 1);
        model.addAttribute("pageInfo",user);
        model.addAttribute("pages", PageUtil.getPage(user.getPages(),pageNumber));
        return "marketAdmin/need";
    }
}