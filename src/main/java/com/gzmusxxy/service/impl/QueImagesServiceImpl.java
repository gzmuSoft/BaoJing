package com.gzmusxxy.service.impl;

import com.gzmusxxy.entity.QueImages;
import com.gzmusxxy.mapper.QueImagesMapper;
import com.gzmusxxy.service.QueImagesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Description 多彩报京：图片展览
 * @Author RAINEROSION
 * @Date 2019/9/13 23:31
 */
public class QueImagesServiceImpl implements QueImagesService {
    @Autowired
    private QueImagesMapper queImagesMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return queImagesMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(QueImages record) {
        return queImagesMapper.insert(record);
    }

    @Override
    public QueImages selectByPrimaryKey(Integer id) {
        return queImagesMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<QueImages> selectAll() {
        return queImagesMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(QueImages record) {
        return queImagesMapper.updateByPrimaryKey(record);
    }
}
