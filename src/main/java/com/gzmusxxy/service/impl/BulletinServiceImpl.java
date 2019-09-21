package com.gzmusxxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.Bulletin;
import com.gzmusxxy.mapper.BulletinMapper;
import com.gzmusxxy.service.BulletinService;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BulletinServiceImpl implements BulletinService {

    @Autowired
    private BulletinMapper bulletinMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return bulletinMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Bulletin insert(Bulletin record) {
        int re = bulletinMapper.insert(record);
        if (re == 1){
            return bulletinMapper.selectBySourceId(record.getSourceId()).get(0);
        }else {
            return null;
        }
    }

    @Override
    public Bulletin selectByPrimaryKey(Integer id) {
        return bulletinMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Bulletin> selectAll() {
        return bulletinMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Bulletin record) {
        return bulletinMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Bulletin> selectBySourceId(Integer id) {
        return bulletinMapper.selectBySourceId(id);
    }

    @Override
    public PageInfo<Bulletin> selectAllBySourceId(Integer pageNumber, Integer sourceId) {
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.ROW_COUNT);
        List<Bulletin> list = bulletinMapper.selectBySourceId(sourceId);
        return new PageInfo<>(list);
    }


}
