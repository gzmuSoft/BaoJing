package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.QueArticle;

import java.util.List;

/**
 * @Description 多彩报京：报京文苑
 * @Author RAINEROSION
 * @Date 2019/9/13 23:21
 */
public interface QueArticleService {
    int deleteByPrimaryKey(Integer id);

    int insert(QueArticle record);

    QueArticle selectByPrimaryKey(Integer id);

    List<QueArticle> selectAll();

    int updateByPrimaryKey(QueArticle record);

    /**
     * 分页查询
     * @param pageNumber 页码
     * @return 返回分页信息
     */
    PageInfo<QueArticle> selectByPage(Integer pageNumber);

    /**
     * 分页查询
     * @param pageNumber
     * @return
     */
    public PageInfo<QueArticle> selectIdAndNameAndCreateTimeByPage(Integer pageNumber);
}
