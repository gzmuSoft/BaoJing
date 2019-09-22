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
    public PageInfo<YlGuarantee> selectAccountByNameLike(String name, Integer pageNumber) {
        if (name != null && !name.equals("")){
            name = "%" + name + "%";
        }else {
            name = "%%";
        }
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<YlGuarantee> ylGuarantees = ylGuaranteeMapper.selectAccountByNameLike(name);
        return new PageInfo<>(ylGuarantees);
    }

    @Override
    public List<YlGuarantee> selectAllByStatus(Integer status) {
        return ylGuaranteeMapper.selectAllByStatus(status);
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

    @Override
    public Integer updateStatus(Integer front, Integer after) {
        return ylGuaranteeMapper.updateStatus(front, after);
    }

    @Override
    public int updateRemarkAndSatusByIdAndPersonId(Integer id, String remark, Integer status,Integer personId) {
        return ylGuaranteeMapper.updateRemarkAndSatusByIdAndPersonId(id, remark, status, personId);
    }

    @Override
    public List<YlGuarantee> selectByPersonId(Integer personId) {
        return ylGuaranteeMapper.selectByPersonId(personId);
    }

    @Override
    public int updateStatusById(byte status, Integer id) {
        return ylGuaranteeMapper.updateStatusById(status,id);
    }
}
