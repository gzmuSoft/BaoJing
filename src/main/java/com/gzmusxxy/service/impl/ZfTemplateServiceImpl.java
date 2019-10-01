package com.gzmusxxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.ZfTemplate;
import com.gzmusxxy.mapper.ZfTemplateMapper;
import com.gzmusxxy.service.ZfTemplateService;
import com.gzmusxxy.util.FileUtil;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZfTemplateServiceImpl implements ZfTemplateService {

    @Autowired
    private ZfTemplateMapper zfTemplateMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        ZfTemplate zfTemplate = zfTemplateMapper.selectByPrimaryKey(id);
        if (zfTemplate.getTemplatePath() != null) {
            FileUtil.deleteFile(zfTemplate.getTemplatePath());
        }
        return zfTemplateMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ZfTemplate record) {
        return zfTemplateMapper.insert(record);
    }

    @Override
    public ZfTemplate selectByPrimaryKey(Integer id) {
        return zfTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ZfTemplate> selectAll() {
        return zfTemplateMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(ZfTemplate record) {
        ZfTemplate zfTemplate = zfTemplateMapper.selectByPrimaryKey(record.getId());
        if (!zfTemplate.getTemplatePath().equals(record.getTemplatePath())) {
            FileUtil.deleteFile(zfTemplate.getTemplatePath());
        }
        return zfTemplateMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo<ZfTemplate> selectByNameLike(String name, Integer pageNumber) {
        if (name != null && !name.equals("")){
            name = "%" + name + "%";
        }else {
            name = "%%";
        }
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<ZfTemplate> zfTemplates = zfTemplateMapper.selectByNameLike(name);
        return new PageInfo<>(zfTemplates);
    }
}
