package com.gzmusxxy.controller;

import com.gzmusxxy.entity.Bulletin;
import com.gzmusxxy.entity.BxProject;
import com.gzmusxxy.entity.ZfTemplate;
import com.gzmusxxy.service.BulletinService;
import com.gzmusxxy.service.ZfTemplateService;
import com.gzmusxxy.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 住房保障首页
     * @return
     */
    @RequestMapping(value = {"","/"})
    public String index(Model model){
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

    @RequestMapping(value = "/apply")
    public String apply(){
        return "house/apply";
    }

}
