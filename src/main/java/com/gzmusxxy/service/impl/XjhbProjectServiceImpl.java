package com.gzmusxxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.XjhbProject;
import com.gzmusxxy.mapper.XjhbProjectMapper;
import com.gzmusxxy.service.XjhbProjectService;
import com.gzmusxxy.util.FileUtil;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class XjhbProjectServiceImpl implements XjhbProjectService {
    @Autowired
    private XjhbProjectMapper xjhbProjectMapper;

    @Override
    public PageInfo<XjhbProject> selectProjectByNameLike(String name, Integer pageNumber) {
        if (name != null && name != ""){
            name = "%" + name + "%";
        }else {
            name = "%%";
        }
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<XjhbProject> list = xjhbProjectMapper.selectProjectByNameLike(name);
        return new PageInfo<>(list);
    }

    @Override
    public int insert(XjhbProject record) {
        return xjhbProjectMapper.insert(record);
    }

    @Override
    public int updateByPrimaryKey(XjhbProject record) {
        return xjhbProjectMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        XjhbProject xjhbProject = xjhbProjectMapper.selectByPrimaryKey(id);
        FileUtil.deleteFile(xjhbProject.getApplicationTemplate());
        return xjhbProjectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public XjhbProject selectByPrimaryKey(Integer id) {
        return xjhbProjectMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<XjhbProject> selectAll() {
        return xjhbProjectMapper.selectAll();
    }
}
