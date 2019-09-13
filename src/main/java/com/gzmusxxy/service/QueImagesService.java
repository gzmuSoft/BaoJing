package com.gzmusxxy.service;

import com.gzmusxxy.entity.QueImages;

import java.util.List;

/**
 * @Description 多彩报京：图片展览
 * @Author RAINEROSION
 * @Date 2019/9/13 23:24
 */
public interface QueImagesService {
    int deleteByPrimaryKey(Integer id);

    int insert(QueImages record);

    QueImages selectByPrimaryKey(Integer id);

    List<QueImages> selectAll();

    int updateByPrimaryKey(QueImages record);
}
