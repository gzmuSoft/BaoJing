package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.QueImages;
import java.util.List;

public interface QueImagesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QueImages record);

    QueImages selectByPrimaryKey(Integer id);

    List<QueImages> selectAll();

    int updateByPrimaryKey(QueImages record);
}