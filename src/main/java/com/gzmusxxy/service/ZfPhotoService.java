package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.ZfPhoto;

import java.util.List;

public interface ZfPhotoService {

    int deleteByPrimaryKey(Integer id);

    int insert(ZfPhoto record);

    ZfPhoto selectByPrimaryKey(Integer id);

    List<ZfPhoto> selectAll();

    int updateByPrimaryKey(ZfPhoto record);

    PageInfo<ZfPhoto> selectByNameLike(String name, Integer pageNumber);

    ZfPhoto selectByApplyId(Integer id);

    int updateByApplyId(ZfPhoto record);
}
