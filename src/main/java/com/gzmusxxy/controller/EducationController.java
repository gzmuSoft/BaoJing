package com.gzmusxxy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 教育保障
 * @Author RAINEROSION
 * @Date 2019/10/3 21:36
 */
@Controller
@RequestMapping(value = "/education")
public class EducationController {
    /**
     * 教育保障首页
     * @return
     */
    @RequestMapping(value = {"","/"})
    public String index(){
        return "education/index";
    }
}
