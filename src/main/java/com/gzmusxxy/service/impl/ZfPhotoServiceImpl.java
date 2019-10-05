package com.gzmusxxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.ZfPhoto;
import com.gzmusxxy.mapper.ZfPhotoMapper;
import com.gzmusxxy.service.ZfPhotoService;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZfPhotoServiceImpl implements ZfPhotoService {

    @Autowired
    private ZfPhotoMapper zfPhotoMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return zfPhotoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ZfPhoto record) {
        return zfPhotoMapper.insert(record);
    }

    @Override
    public ZfPhoto selectByPrimaryKey(Integer id) {
        return zfPhotoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ZfPhoto> selectAll() {
        return zfPhotoMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(ZfPhoto record) {
        return zfPhotoMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo<ZfPhoto> selectByNameLike(String name, Integer pageNumber) {
        if (name != null && !name.equals("")){
            name = "%" + name + "%";
        }else {
            name = "%%";
        }
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<ZfPhoto> zfPhotos = zfPhotoMapper.selectByNameLike(name);
        return new PageInfo<>(zfPhotos);
    }

    @Override
    public ZfPhoto selectByApplyId(Integer id) {
        return zfPhotoMapper.selectByApplyId(id);
    }

    @Override
    public int updateByApplyId(ZfPhoto record) {
        return zfPhotoMapper.updateByApplyId(record);
    }
}
