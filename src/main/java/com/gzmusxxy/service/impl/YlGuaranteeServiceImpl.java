package com.gzmusxxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.YlGuarantee;
import com.gzmusxxy.mapper.YlGuaranteeMapper;
import com.gzmusxxy.service.YlGuaranteeService;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YlGuaranteeServiceImpl implements YlGuaranteeService {

    @Autowired
    private YlGuaranteeMapper ylGuaranteeMapper;

    @Override
    public PageInfo<YlGuarantee> selectByNameCostLike(String name, Integer pageNumber) {
        if (name != null && !name.equals("")){
            name = "%" + name + "%";
        }else {
            name = "%%";
        }
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<YlGuarantee> ylGuarantees = ylGuaranteeMapper.selectByNameCostLike(name);
        return new PageInfo<>(ylGuarantees);
    }

    @Override
    public PageInfo<YlGuarantee> selectByNameLike(String name, Integer pageNumber) {
        if (name != null && !name.equals("")){
            name = "%" + name + "%";
        }else {
            name = "%%";
        }
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<YlGuarantee> ylGuarantees = ylGuaranteeMapper.selectByNameLike(name);
        return new PageInfo<>(ylGuarantees);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return ylGuaranteeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(YlGuarantee record) {
        return ylGuaranteeMapper.insert(record);
    }

    @Override
    public YlGuarantee selectByPrimaryKey(Integer id) {
        return ylGuaranteeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<YlGuarantee> selectAll() {
        return ylGuaranteeMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(YlGuarantee record) {
        return ylGuaranteeMapper.updateByPrimaryKey(record);
    }
}
