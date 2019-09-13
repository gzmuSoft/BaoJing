package com.gzmusxxy.service.impl;

import com.gzmusxxy.entity.QueArticle;
import com.gzmusxxy.mapper.QueArticleMapper;
import com.gzmusxxy.service.QueArticleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Description TODO
 * @Author RAINEROSION
 * @Date 2019/9/13 23:28
 */
public class QueArticleServiceImpl implements QueArticleService {
    @Autowired
    private QueArticleMapper queArticleMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return queArticleMapper.deleteByPrimaryKey(id);
    }

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

    @Override
    public int updateByPrimaryKey(QueArticle record) {
        return queArticleMapper.updateByPrimaryKey(record);
    }
}
