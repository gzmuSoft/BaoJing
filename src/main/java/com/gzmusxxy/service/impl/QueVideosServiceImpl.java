package com.gzmusxxy.service.impl;

import com.gzmusxxy.entity.QueVideos;
import com.gzmusxxy.mapper.QueVideosMapper;
import com.gzmusxxy.service.QueImagesService;
import com.gzmusxxy.service.QueVideosService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Description TODO
 * @Author RAINEROSION
 * @Date 2019/9/13 23:33
 */
public class QueVideosServiceImpl implements QueVideosService {

    @Autowired
    private QueVideosMapper queVideosMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return queVideosMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(QueVideos record) {
        return queVideosMapper.insert(record);
    }

    @Override
    public QueVideos selectByPrimaryKey(Integer id) {
        return queVideosMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<QueVideos> selectAll() {
        return queVideosMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(QueVideos record) {
        return queVideosMapper.updateByPrimaryKey(record);
    }
}
