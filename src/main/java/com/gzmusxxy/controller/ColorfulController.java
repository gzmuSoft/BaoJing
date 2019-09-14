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
import org.springframework.web.multipart.MultipartFile;

import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

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
     * @param pageNumber
     * @param model
     * @return
     */
    @RequestMapping(value = "/photos")
    public String photosExhibition(@RequestParam("pageNumber") Integer pageNumber, Model model) {
        PageInfo<QueImages> queImagesPageInfo = queImagesService.selectAll(pageNumber > 0 ? pageNumber : 1);
        model.addAttribute("pageInfo", queImagesPageInfo);
        model.addAttribute("pages", PageUtil.getPage(queImagesPageInfo.getPages(), pageNumber));
        return "colorful/images.html";
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
