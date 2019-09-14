package com.gzmusxxy.controller;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.common.JsonResult;
import com.gzmusxxy.entity.QueImages;
import com.gzmusxxy.service.QueImagesService;
import com.gzmusxxy.util.FileUtil;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 多彩报京
 */
@Controller
@RequestMapping("/colorful")
public class ColorfulController {
    @Autowired
    private QueImagesService queImagesService;

    /**
     * 图片展示
     * @return
     */
    @RequestMapping(value = "/photos")
    public String photosExhibition(@RequestParam("pageNumber") Integer pageNumber, Model model){
        PageInfo<QueImages> queImagesPageInfo = queImagesService.selectAll(pageNumber > 0 ? pageNumber : 1);
        model.addAttribute("pageInfo",queImagesPageInfo);
        model.addAttribute("pages", PageUtil.getPage(queImagesPageInfo.getPages(), pageNumber));
        return "colorful/images.html";
    }
    @RequestMapping(value = "/deletePhotosById")
    @ResponseBody
    public JsonResult deletePhotos(@RequestParam("id") Integer id){
        JsonResult jsonResult = new JsonResult();
        //先取出数据
        QueImages queImages = queImagesService.selectByPrimaryKey(id);
        int i = queImagesService.deleteByPrimaryKey(id);
        if(i>0){
            //数据删除成功以后删除文件
            if(queImages != null && queImages.getPath() != null){
                //执行文件删除
                FileUtil.deleteImgVideFile(FileUtil.FILE_PATH+queImages.getPath());
            }
            jsonResult.setCode(1);
            jsonResult.setResult("ok");
            return jsonResult;
        }
        jsonResult.setCode(0);
        jsonResult.setResult("删除失败！");
        return jsonResult;
    }
}
