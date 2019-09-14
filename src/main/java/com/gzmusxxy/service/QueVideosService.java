package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.QueVideos;

import java.util.List;

/**
 * @Description 视频播放
 * @Author RAINEROSION
 * @Date 2019/9/13 23:26
 */
public interface QueVideosService {
    int deleteByPrimaryKey(Integer id);

    int insert(QueVideos record);

    QueVideos selectByPrimaryKey(Integer id);

    PageInfo<QueVideos> selectAll(Integer pageNumber);

    int updateByPrimaryKey(QueVideos record);
}
