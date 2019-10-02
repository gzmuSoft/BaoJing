package com.gzmusxxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.ZfApply;
import com.gzmusxxy.mapper.ZfApplyMapper;
import com.gzmusxxy.service.ZfApplyService;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZfApplyServiceImpl implements ZfApplyService {

    @Autowired
    private ZfApplyMapper zfApplyMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return zfApplyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ZfApply record) {
        return zfApplyMapper.insert(record);
    }

    @Override
    public ZfApply selectByPrimaryKey(Integer id) {
        return zfApplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ZfApply> selectAll() {
        return zfApplyMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(ZfApply record) {
        return zfApplyMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo<ZfApply> selectByNameLike(String name, Integer pageNumber) {
        if (name != null && !name.equals("")){
            name = "%" + name + "%";
        }else {
            name = "%%";
        }
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<ZfApply> zfApplies = zfApplyMapper.selectByNameLike(name);
        return new PageInfo<>(zfApplies);
    }

    @Override
    public PageInfo<ZfApply> selectCompleteByNameLike(String name, Integer pageNumber) {
        if (name != null && !name.equals("")){
            name = "%" + name + "%";
        }else {
            name = "%%";
        }
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<ZfApply> zfApplies = zfApplyMapper.selectCompleteByNameLike(name);
        return new PageInfo<>(zfApplies);
    }
}
