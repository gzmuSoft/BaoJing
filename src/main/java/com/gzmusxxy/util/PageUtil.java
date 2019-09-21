package com.gzmusxxy.util;

/**
 * 分页工具类
 */
public class PageUtil {
    /**
     * 设置表格每页显示数据的行数
     */
    public static final Integer PAGE_ROW_COUNT = 8;

    /**
     * 设置通知每页显示数据的行数
     */
    public static final Integer ROW_COUNT = 1;


    /**
     * 固定显示5页
     */
    public static int page = 5;

    /**
     * 分页按钮
     * @param pages 总页数
     * @param pageNumber 当前页
     * @return 分页按钮
     */
    public static int[] getPage(int pages,int pageNumber) {

        int numPage[];
        //总共页数
        int pageCount = pages;
        //判断是否需要省略
        if (pageCount > page) {
            numPage = new int[page];
            //两边都可以显示
            if (pageNumber - 2 > 0 && pageCount - 2 >= pageNumber) {
                for (int i = 0; i < numPage.length; i++) {
                    numPage[i] = pageNumber - 2 + i;
                }
            }else if (pageNumber - 2 > 0 && pageCount - 2 < pageNumber){
                //前面可以显示 后面不够
                for (int i = numPage.length-1; i >= 0; i--) {
                    numPage[i] = pageCount--;
                }
            }else {
                //后面可以显示 前面不够
                for (int i = 0; i < numPage.length; i++) {
                    numPage[i] = i + 1;
                }
            }
        }else {
            numPage = new int[pageCount];
            for (int i = 0; i < numPage.length; i++) {
                numPage[i] = i + 1;
            }
        }
        return numPage;
    }
}
