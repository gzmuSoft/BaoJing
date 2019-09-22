package com.gzmusxxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.JyStudent;
import com.gzmusxxy.mapper.JyStudentMapper;
import com.gzmusxxy.service.JyStudentService;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JyStudentServiceImpl implements JyStudentService {

    @Autowired
    private JyStudentMapper jyStudentMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return jyStudentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(JyStudent record) {
        return jyStudentMapper.insert(record);
    }

    @Override
    public JyStudent selectByPrimaryKey(Integer id) {
        return jyStudentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<JyStudent> selectAll() {
        return jyStudentMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(JyStudent record) {
        return jyStudentMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo<JyStudent> selectByNameLike(String name, Integer pageNumber) {
        if (name != null && !name.equals("")){
            name = "%" + name + "%";
        }else {
            name = "%%";
        }
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<JyStudent> jyStudents = jyStudentMapper.selectByNameLike(name);
        return new PageInfo<>(jyStudents);
    }
}
