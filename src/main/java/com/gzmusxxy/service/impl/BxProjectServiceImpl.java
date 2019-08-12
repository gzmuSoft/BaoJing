package com.gzmusxxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.BxProject;
import com.gzmusxxy.mapper.BxProjectMapper;
import com.gzmusxxy.service.BxProjectService;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BxProjectServiceImpl implements BxProjectService {

    @Autowired
    private BxProjectMapper bxProjectMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return bxProjectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(BxProject record) {
        return bxProjectMapper.insert(record);
    }

    @Override
    public BxProject selectByPrimaryKey(Integer id) {
        return bxProjectMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(BxProject record) {
        return bxProjectMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo<BxProject> selectProjectByNameLike(String name, Integer pageNumber) {
        if (name != null && name != ""){
            name = "%" + name + "%";
        }else {
            name = "%%";
        }
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<BxProject> list = bxProjectMapper.selectProjectByNameLike(name);
        return new PageInfo<>(list);
    }
}
