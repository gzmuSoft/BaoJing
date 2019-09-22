package com.gzmusxxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.JyApply;
import com.gzmusxxy.mapper.JyApplyMapper;
import com.gzmusxxy.service.JyApplyService;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JyApplyServiceImpl implements JyApplyService {

    @Autowired
    private JyApplyMapper jyApplyMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return jyApplyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(JyApply record) {
        return jyApplyMapper.insert(record);
    }

    @Override
    public JyApply selectByPrimaryKey(Integer id) {
        return jyApplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<JyApply> selectAll() {
        return jyApplyMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(JyApply record) {
        return jyApplyMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo<JyApply> selectByNameLike(String name, Integer pageNumber) {
        if (name != null && !name.equals("")){
            name = "%" + name + "%";
        }else {
            name = "%%";
        }
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<JyApply> jyStudents = jyApplyMapper.selectByNameLike(name);
        return new PageInfo<>(jyStudents);
    }

}
