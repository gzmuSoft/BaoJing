package com.gzmusxxy.controller;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.common.Msg;
import com.gzmusxxy.entity.QueArticle;
import com.gzmusxxy.entity.QueImages;
import com.gzmusxxy.entity.QueVideos;
import com.gzmusxxy.service.QueArticleService;
import com.gzmusxxy.service.QueImagesService;
import com.gzmusxxy.service.QueVideosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Description 多彩报京数据展览前台控制器
 * @Author RAINEROSION
 * @Date 2019/9/17 17:29
 */
@Controller
@RequestMapping("/exhibition")
public class ExhibitionController {
    @Autowired
    private QueImagesService queImagesService;
    @Autowired
    private QueVideosService queVideosService;
    @Autowired
    private QueArticleService queArticleService;
    /**
     * 数据展示的首页
     * @return
     */
    @RequestMapping(value = {"","/"})
    public String Index(){
        return "colorful/index";
    }

    /**
     * 获取图片列表
     * @param pageNumber 页码
     * @return
     */
    @RequestMapping(value = "/getPhotoList")
    @ResponseBody
    public Msg getPhotoList(@RequestParam(value = "pn",defaultValue = "1",required = false) Integer pageNumber, HttpSession session){
        PageInfo<QueImages> queImagesPageInfo = queImagesService.selectAll(pageNumber > 0 ? pageNumber : 1);
        session.setAttribute("CurrentPage", queImagesPageInfo.getPageNum());
        session.setAttribute("TotalPages", queImagesPageInfo.getPages());
        return Msg.success().add("paging", queImagesPageInfo);
    }

    /**
     * 获取视频列表
     * @param pageNumber 页码
     * @return
     */
    @RequestMapping(value = "/getVideoList")
    @ResponseBody
    public Msg getVideoList(@RequestParam(value = "pn",defaultValue = "1",required = false) Integer pageNumber, HttpSession session){
        PageInfo<QueVideos> queVideosPageInfo = queVideosService.selectAll(pageNumber > 0 ? pageNumber : 1);
        session.setAttribute("CurrentPage", queVideosPageInfo.getPageNum());
        session.setAttribute("TotalPages", queVideosPageInfo.getPages());
        return Msg.success().add("paging", queVideosPageInfo);
    }
    /**
     * 获取文章列表
     * @param pageNumber 页码
     * @return
     */
    @RequestMapping(value = "/getArticleList")
    @ResponseBody
    public Msg getArticleList(@RequestParam(value = "pn",defaultValue = "1",required = false) Integer pageNumber, HttpSession session){
        PageInfo<QueArticle> queArticlesPageInfo = queArticleService.selectByPage(pageNumber > 0 ? pageNumber : 1);
        session.setAttribute("CurrentPage", queArticlesPageInfo.getPageNum());
        session.setAttribute("TotalPages", queArticlesPageInfo.getPages());
        return Msg.success().add("paging", queArticlesPageInfo);
    }

    /**
     * 查询预览图片
     * @param id
     * @return
     */
    @RequestMapping(value = "/getImageDetailById")
    public String previewPic(@RequestParam("id") Integer id, Model model){
        QueImages queImages = queImagesService.selectByPrimaryKey(id);
        if(queImages != null){
            model.addAttribute("fileName",queImages.getName());
            model.addAttribute("path",queImages.getPath());
        }else{
            model.addAttribute("fileName","文件不存在");
            model.addAttribute("path","");
        }
        return "colorful/previewPic";
    }
    /**
     * 查询播放视频
     * @param id
     * @return
     */
    @RequestMapping(value = "/getVideoDetailById")
    public String previewVideo(@RequestParam("id") Integer id, Model model){
        QueVideos queVideos = queVideosService.selectByPrimaryKey(id);
        if(queVideos == null){
            //避免前台报错
            queVideos = new QueVideos();
        }
        model.addAttribute("video",queVideos);
        return "colorful/previewVideo";
    }
    /**
     * 查询预览文章
     * @param id
     * @return
     */
    @RequestMapping(value = "/getArticleDetailById")
    public String previewArticle(@RequestParam("id") Integer id, Model model){
        QueArticle queArticle = queArticleService.selectByPrimaryKey(id);
        if(queArticle == null){
            //避免前台报错
            queArticle = new QueArticle();
        }
        model.addAttribute("article",queArticle);
        return "colorful/previewArticles";
    }

}
