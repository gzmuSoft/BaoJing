package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.QueArticle;
import java.util.List;

public interface QueArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QueArticle record);

    QueArticle selectByPrimaryKey(Integer id);

    List<QueArticle> selectAll();

    int updateByPrimaryKey(QueArticle record);

    List<QueArticle> selectIdAndNameAndCreateTimeByPage();

}