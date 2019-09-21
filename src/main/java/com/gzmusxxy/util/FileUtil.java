package com.gzmusxxy.util;

import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import static com.sun.deploy.cache.Cache.exists;

/**
 * 文件操作工具类
 */
public class FileUtil {

    //    public static final String FILE_PATH = "C:/Users/Administrator/Desktop/upload/";
    public static final String FILE_PATH = "G:/";

    /**
     * 判断文件是否存在
     *
     * @param path 文件路径
     * @return
     */
    public static boolean existFile(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 保存文件到服务器
     *
     * @param file file
     * @param path file全路径，为空则生成，否则覆盖类型相同者
     * @param type 文件后缀
     * @return
     */
    public static String saveFile(MultipartFile file, String path, String type) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 文件保存路径
                String savePath;
                if (path == null) {
                    savePath = FILE_PATH + UUID.randomUUID() + type;
                } else {
                    path = path.substring(0, path.lastIndexOf("."));
                    savePath = path + type;
                }
                System.out.println("文件保存的路径：" + savePath);
                // 转存文件
                file.transferTo(new File(savePath));
                return savePath;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的路径
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("删除文件:" + fileName);
                return true;
            } else {
                System.out.println("文件删除失败");
                return false;
            }
        } else {
            System.out.println("删除失败！没找到文件");
            return false;
        }
    }

    /**
     * 下载服务器上文件
     *
     * @param path     需要下载文件的路径
     * @param name     下载文件的名字
     * @param request  请求
     * @param response 响应
     * @return
     */
    public static void downloadFile(String path, String name, HttpServletRequest request, HttpServletResponse response) {
        String url = path;
        File fileurl = new File(url);
        //浏览器下载后的文件名称showValue,从url中截取到源文件名称以及，以及文件类型，如board.docx;
        String showValue = "默认";
        if (name != null) {
            showValue = name;
        }
        try {
            //将文件读入文件流
            InputStream inStream = new FileInputStream(fileurl);
            //获得浏览器代理信息
            final String userAgent = request.getHeader("USER-AGENT");
            //判断浏览器代理并分别设置响应给浏览器的编码格式
            String finalFileName = null;
            if (StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "Trident")) {//IE浏览器
                finalFileName = URLEncoder.encode(showValue, "UTF8");
                System.out.println("IE浏览器");
            } else if (StringUtils.contains(userAgent, "Mozilla")) {//google,火狐浏览器
                finalFileName = new String(showValue.getBytes(), "ISO8859-1");
            } else {
                finalFileName = URLEncoder.encode(showValue, "UTF8");//其他浏览器
            }
            //设置HTTP响应头
            response.reset();//重置 响应头
            response.setContentType("application/x-download");//告知浏览器下载文件，而不是直接打开，浏览器默认为打开
            response.addHeader("Content-Disposition", "attachment;filename=\"" + finalFileName + "\"");//下载文件的名称

            // 循环取出流中的数据
            byte[] b = new byte[1024];
            int len;
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
            response.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 多彩报京专用：删除图片视频文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteImgVideFile(String fileName) {

        File file = new File(fileName);

        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 文件重命名
     *
     * @param path    文件目录
     * @param oldname 原来的文件名
     * @param newname 新文件名
     */
    public static void renameFile(String path, String oldname, String newname) {
        if (!oldname.equals(newname)) {
            File oldfile = new File(path + "/" + oldname);
            File newfile = new File(path + "/" + newname);
            if (!oldfile.exists()) {
                return;
            }
            if (!newfile.exists()) {
                oldfile.renameTo(newfile);
            }
        } else {
            // System.out.println("新文件名和旧文件名相同...");
        }
    }

    /**
     * @param file 文件
     * @param path 路径
     */
    public static Map<String,String> uploadFile(MultipartFile file, String path) {
        //文件扩展名
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        //构建文件扩展名
        String filename = UUID.randomUUID().toString().replace("-", "") + Long.toHexString(System.currentTimeMillis()) + extension;
        String filepath = path + filename;
        Map<String,String> map = new Hashtable<>();
        try {
            File dir = new File(path);
            //判断文件夹是否存在
            if (!dir.exists()) {
                //创建文件夹
                dir.mkdirs();
            }
            //转存文件
            file.transferTo(new File(filepath));
            map.put("originalFilename",file.getOriginalFilename());
            map.put("fileName",filename);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //清空
        map.clear();
        return map;
    }
}
