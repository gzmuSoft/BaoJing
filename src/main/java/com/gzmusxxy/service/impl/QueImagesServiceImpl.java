package com.gzmusxxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.QueImages;
import com.gzmusxxy.mapper.QueImagesMapper;
import com.gzmusxxy.service.QueImagesService;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 多彩报京：图片展览
 * @Author RAINEROSION
 * @Date 2019/9/13 23:31
 */
@Service
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
    public PageInfo<QueImages> selectAll(Integer pageNumber) {
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<QueImages> list = queImagesMapper.selectAll();
        return new PageInfo<>(list);
    }

    @Override
    public int updateByPrimaryKey(QueImages record) {
        return queImagesMapper.updateByPrimaryKey(record);
    }
}
