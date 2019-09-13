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

    //    public static final String FILE_PATH = "C:/Users/Administrator/Desktop/upload/";
    public static final String FILE_PATH = "D:/";

    //多彩报京文件夹
    private static  String filenameTemp;

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
    //多彩报京专用

    /**
     * 创建文件
     *
     * @param fileName    文件名称
     * @param filecontent 文件内容
     * @return 是否创建成功，成功则返回true
     */
    public static boolean createFile(String path, String fileName, String filecontent) {
        Boolean bool = false;
        filenameTemp = path + fileName + ".html";//文件路径+名称+文件类型
        File file = new File(filenameTemp);
        try {
            //如果文件不存在，则创建新的文件
            if (!file.exists()) {
                file.createNewFile();
                bool = true;
                //System.out.println("success create file,the file is "+filenameTemp);
                //创建文件成功后，写入内容到文件里
            }
            writeFileContent(filenameTemp, filecontent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

    /**
     * 向文件中写入内容
     *
     * @param filepath 文件路径与名称
     * @param newstr   写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(String filepath, String newstr) throws IOException {
        Boolean bool = false;
        String filein = newstr + "\r\n";//新写入的行，换行
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        //FileOutputStream fos  = null;
        OutputStreamWriter oStreamWriter = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            buffer.append(filein);
            oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            pw = new PrintWriter(oStreamWriter);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (oStreamWriter != null) {
                oStreamWriter.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }
    /**
     * 删除图片视频文件
     *
     * @param fileName
     *            要删除的文件的文件名
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
     * 删除文件
     * @param filePathName 文件名称
     * @return
     */
    public static boolean delFile(String filePathName){
        Boolean bool = false;
        filenameTemp = filePathName+".html";
        File file  = new File(filenameTemp);
        try {
            if(file.exists()){
                file.delete();
                bool = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bool;
    }

    /** 文件重命名
     * @param path 文件目录
     * @param oldname  原来的文件名
     * @param newname 新文件名
     */
    public static void renameFile(String path,String oldname,String newname){
        if(!oldname.equals(newname)){
            File oldfile=new File(path+"/"+oldname);
            File newfile=new File(path+"/"+newname);
            if(!oldfile.exists()){
                return;
            }
            if(!newfile.exists()){
                oldfile.renameTo(newfile);
            }
        }else{
            // System.out.println("新文件名和旧文件名相同...");
        }
    }
}
