package com.gzmusxxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.QueVideos;
import com.gzmusxxy.mapper.QueVideosMapper;
import com.gzmusxxy.service.QueVideosService;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 多彩报京：视频播放
 * @Author RAINEROSION
 * @Date 2019/9/13 23:33
 */
@Service
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
    public PageInfo<QueVideos> selectAll(Integer pageNumber) {
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<QueVideos> list = queVideosMapper.selectAll();
        return new PageInfo<>(list);
    }

    @Override
    public int updateByPrimaryKey(QueVideos record) {
        return queVideosMapper.updateByPrimaryKey(record);
    }
}
