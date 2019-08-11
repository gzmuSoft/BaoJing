package com.gzmusxxy.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelUtil {

    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @param filePath 需要保存的地址
     * @param fileName 需要保存的名字
     * @return 最终保存路径
     */
    public static String getHSSFWorkbook(String sheetName,String []title,String [][]values, HSSFWorkbook wb, String filePath, String fileName){

        // 创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        //设置宽度
        for (int i = 0; i < title.length + 1; i++) {
            sheet.setColumnWidth(i, (int)((50 + 0.72) * 256)/2);
        }


        // 在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short)450);

        // 标题样式
        HSSFCellStyle styleT = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setFontHeightInPoints((short) 14);//设置字体大小
        styleT.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        styleT.setBorderBottom(CellStyle.BORDER_THIN); // 底部边框
        styleT.setBorderLeft(CellStyle.BORDER_THIN);
        styleT.setBorderRight(CellStyle.BORDER_THIN);
        styleT.setBorderTop(CellStyle.BORDER_THIN);
        styleT.setFont(font);
        // 创建单元格样式
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font2 = wb.createFont();
        font2.setFontHeightInPoints((short) 12);//设置字体大小
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        style.setBorderBottom(CellStyle.BORDER_THIN); // 底部边框
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setFont(font2);
        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(styleT);
        }

        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            row.setHeight((short)400);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                HSSFCell cell1 = row.createCell(j);
                cell1.setCellStyle(style);
                cell1.setCellValue(values[i][j]);
            }
        }

        File file=new File(filePath);
        OutputStream stream=null;
        try {
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
              String name = fileName + "_"+sdf.format(new Date())+".xls";
              stream = new FileOutputStream(new File(file,name));
              //document.write(stream);
              wb.write(stream);
              return filePath + name;
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }finally{
          if(stream != null);
          try {
            stream.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        return null;
    }
}