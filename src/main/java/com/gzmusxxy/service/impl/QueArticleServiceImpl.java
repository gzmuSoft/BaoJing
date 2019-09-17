package com.gzmusxxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.QueArticle;
import com.gzmusxxy.mapper.QueArticleMapper;
import com.gzmusxxy.service.QueArticleService;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description 多彩报京：报京文苑
 * @Author RAINEROSION
 * @Date 2019/9/13 23:28
 */
@Service
public class QueArticleServiceImpl implements QueArticleService {
    @Autowired
    private QueArticleMapper queArticleMapper;
    @Transactional
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return queArticleMapper.deleteByPrimaryKey(id);
    }
    @Transactional
    @Override
    public int insert(QueArticle record) {
        return queArticleMapper.insert(record);
    }

    @Override
    public QueArticle selectByPrimaryKey(Integer id) {
        return queArticleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<QueArticle> selectAll() {
        return queArticleMapper.selectAll();
    }
    @Transactional
    @Override
    public int updateByPrimaryKey(QueArticle record) {
        return queArticleMapper.updateByPrimaryKey(record);
    }
    @Transactional
    @Override
    public PageInfo<QueArticle> selectByPage(Integer pageNumber) {
        //PageHelper插件的分页信息：默认8条数据
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        List<QueArticle> list = this.queArticleMapper.selectAll();
        return new PageInfo<>(list);
    }
    @Transactional
    @Override
    public PageInfo<QueArticle> selectIdAndNameAndCreateTimeByPage(Integer pageNumber) {
        //PageHelper插件的分页信息：默认8条数据
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        List<QueArticle> list = this.queArticleMapper.selectIdAndNameAndCreateTimeByPage();
        return new PageInfo<>(list);
    }
}
