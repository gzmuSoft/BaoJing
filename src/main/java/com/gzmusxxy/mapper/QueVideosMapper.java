package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.QueVideos;
import java.util.List;

public interface QueVideosMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QueVideos record);

    QueVideos selectByPrimaryKey(Integer id);

    List<QueVideos> selectAll();

    int updateByPrimaryKey(QueVideos record);
}