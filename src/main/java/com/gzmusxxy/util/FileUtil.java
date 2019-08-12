package com.gzmusxxy.util;

import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 文件操作工具类
 */
public class FileUtil {

    public static final String FILE_PATH = "D:\\";

    public static final String FILE_PATH2 = "/home/fengxin/";

    /**
     * 保存文件到服务器
     * @param file file
     * @param path file全路径，为空则生成，否则覆盖类型相同者
     * @param type 文件后缀
     * @return
     */
    public static String saveFile(MultipartFile file, String path, String type){
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 文件保存路径
                String savePath;
                if (path == null){
                    savePath = FILE_PATH2 + UUID.randomUUID() + type;
                }else {
                    path = path.substring(0, path.lastIndexOf("."));
                    savePath = path + type;
                }
                System.out.println("文件保存的路径："+savePath);
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
     * @param fileName
     *            要删除的文件的路径
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists()) {
            if (file.delete()) {
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
     * @param path 需要下载文件的路径
     * @param name 下载文件的名字
     * @param request 请求
     * @param response 响应
     * @return
     */
    public static void downloadFile(String path, String name, HttpServletRequest request, HttpServletResponse response){
        String url = path;
        File fileurl = new File(url);
        //浏览器下载后的文件名称showValue,从url中截取到源文件名称以及，以及文件类型，如board.docx;
        String showValue = "默认";
        if (name != null) {
            showValue = name;
        }
        try{
            //将文件读入文件流
            InputStream inStream = new FileInputStream(fileurl);
            //获得浏览器代理信息
            final String userAgent = request.getHeader("USER-AGENT");
            //判断浏览器代理并分别设置响应给浏览器的编码格式
            String finalFileName = null;
            if(StringUtils.contains(userAgent, "MSIE")||StringUtils.contains(userAgent,"Trident")){//IE浏览器
                finalFileName = URLEncoder.encode(showValue,"UTF8");
                System.out.println("IE浏览器");
            }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
                finalFileName = new String(showValue.getBytes(), "ISO8859-1");
            }else{
                finalFileName = URLEncoder.encode(showValue,"UTF8");//其他浏览器
            }
            //设置HTTP响应头
            response.reset();//重置 响应头
            response.setContentType("application/x-download");//告知浏览器下载文件，而不是直接打开，浏览器默认为打开
            response.addHeader("Content-Disposition" ,"attachment;filename=\"" +finalFileName+ "\"");//下载文件的名称

            // 循环取出流中的数据
            byte[] b = new byte[1024];
            int len;
            while ((len = inStream.read(b)) > 0){
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
            response.getOutputStream().close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
