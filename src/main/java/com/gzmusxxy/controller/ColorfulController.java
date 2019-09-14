package com.gzmusxxy.controller;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.common.JsonResult;
import com.gzmusxxy.entity.QueArticle;
import com.gzmusxxy.entity.QueImages;
import com.gzmusxxy.service.QueArticleService;
import com.gzmusxxy.service.QueImagesService;
import com.gzmusxxy.service.QueVideosService;
import com.gzmusxxy.util.FileUtil;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 多彩报京
 */
@Controller
@RequestMapping("/colorful")
public class ColorfulController {
    @Autowired
    private QueImagesService queImagesService;
    @Autowired
    private QueArticleService queArticleService;
    @Autowired
    private QueVideosService queVideosService;
    /**
     * 图片展示
     * @param pageNumber
     * @param model
     * @return
     */
    @RequestMapping(value = "/photos")
    public String photosExhibition(@RequestParam("pageNumber") Integer pageNumber, Model model) {
        PageInfo<QueImages> queImagesPageInfo = queImagesService.selectAll(pageNumber > 0 ? pageNumber : 1);
        model.addAttribute("pageInfo", queImagesPageInfo);
        model.addAttribute("pages", PageUtil.getPage(queImagesPageInfo.getPages(), pageNumber));
        return "colorful/images";
    }

    /**
     * 删除指定id图片
     * @param id
     * @return
     */
    @RequestMapping(value = "/deletePhotosById")
    @ResponseBody
    public JsonResult deletePhotos(@RequestParam("id") Integer id) {
        JsonResult jsonResult = new JsonResult();
        //先取出数据
        QueImages queImages = queImagesService.selectByPrimaryKey(id);
        int i = queImagesService.deleteByPrimaryKey(id);
        if (i > 0) {
            //数据删除成功以后删除文件
            if (queImages != null && queImages.getPath() != null) {
                //执行文件删除
                FileUtil.deleteImgVideFile(FileUtil.FILE_PATH + queImages.getPath());
            }
            jsonResult.setCode(1);
            jsonResult.setResult("ok");
            return jsonResult;
        }
        jsonResult.setCode(0);
        jsonResult.setResult("删除失败！");
        return jsonResult;
    }

    /**
     * 获取所有文章
     * @return List类型的实例
     */
    @RequestMapping(value = "/getAllArticle")
    @ResponseBody
    public List<QueArticle> getAllArticle(){
        List<QueArticle> list = this.queArticleService.selectAll();
        try{
            if(list != null && list.size() > 0){
                Map<String,Object> map = new HashMap<>();
                map.put("articleList",list);
                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 文章分页
     * @param pageNumber 页码
     * @param model 模型
     * @return 返回到文章页
     */
    @RequestMapping(value = "/article")
    public String selectByArticlePage(@RequestParam("pageNumber") Integer pageNumber, Model model){
        PageInfo<QueArticle> pageInfo = this.queArticleService.selectByPage(pageNumber>0?pageNumber:1);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pages", PageUtil.getPage(pageInfo.getPages(),pageNumber));
        return "colorful/article";
    }
    /**
     * 根据文章编号删除文章
     * @param id  文章编号
     * @return JsonResult
     */
    @RequestMapping(value = "/deleteArticle")
    @ResponseBody
    public JsonResult deleteArticle(@RequestParam("id") Integer id){
        JsonResult jsonResult = new JsonResult();
        int i = this.queArticleService.deleteByPrimaryKey(id);
        if(i > 0){
            //删除成功
            jsonResult.setCode(1);
            jsonResult.setResult("文章删除成功");
            return jsonResult;
        }
        // 删除文章失败
        jsonResult.setCode(0);
        jsonResult.setResult("文章删除失败");
        return jsonResult;
    }

    /**
     * 添加文章
     * @param queArticle 文章信息
     * @return JsonResult
     */
    @RequestMapping(value = "/addArticle")
    @ResponseBody
    public JsonResult addArticle(QueArticle queArticle){
        JsonResult jsonResult = new JsonResult();
        queArticle.setCreateTime(new Date());
        int insert = this.queArticleService.insert(queArticle);
        if(insert > 0){
            jsonResult.setCode(1);
            jsonResult.setResult("添加成功");
            return jsonResult;
        }
        jsonResult.setCode(0);
        jsonResult.setResult("添加失败");
        return jsonResult;
    }
    /**
     * 根据文章id获取文章
     * @param id 文章id
     * @return 文章完整信息
     */
    @RequestMapping(value = "/getArticleById")
    @ResponseBody
    public QueArticle getArticleById(@RequestParam("id") Integer id){
        return this.queArticleService.selectByPrimaryKey(id);
    }
    /**
     * 获得文章信息编辑文章页面：用于填充编辑框
     * @param id 编号
     * @param model model
     * @return 返回到编辑页面
     */
    @RequestMapping(value = "/getArticleMsg")
    public String  getArticleMsg(@RequestParam("id") Integer id,Model model){
        QueArticle article = this.queArticleService.selectByPrimaryKey(id);
        model.addAttribute("article",article);
        return "colorful/editArticle";
    }
    /**
     * 更新文章信息
     * @param queArticle 更新的文章信息
     * @return 返回JsonResult类型的数据
     */
    @RequestMapping(value = "/updateArticle")
    @ResponseBody
    public JsonResult updateArticle(QueArticle queArticle){
        JsonResult jsonResult = new JsonResult();
        queArticle.setCreateTime(new Date());
        int i = this.queArticleService.updateByPrimaryKey(queArticle);
        if (i > 0){
            jsonResult.setCode(1);
            jsonResult.setResult("更新数据成功");
            return jsonResult;
        }
        // 失败返回0
        jsonResult.setCode(0);
        jsonResult.setResult("更新数据失败");
        return jsonResult;
    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    @RequestMapping("/uploadPic")
    @ResponseBody
    public Map<String, String> uploadPic(@RequestParam("file") MultipartFile file) {
        Map<String, String> fileNameMap = FileUtil.uploadFile(file, FileUtil.FILE_PATH + "picture/");
        Map<String, String> result = new Hashtable<>();
        if(!fileNameMap.isEmpty()){
            QueImages queImages = new QueImages();
            queImages.setName(fileNameMap.get("originalFilename"));
            queImages.setPath("picture/"+fileNameMap.get("fileName"));
            int insert = queImagesService.insert(queImages);
            if(insert > 0){
                result.put("success","文件上传成功！");
                return result;
            }
        }
        result.clear();
        result.put("error","文件上传失败！");
        return result;
    }
}
