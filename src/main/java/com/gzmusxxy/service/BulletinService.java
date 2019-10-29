package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.Bulletin;

import java.util.List;

public interface BulletinService {

    int deleteByPrimaryKey(Integer id);

    Bulletin insert(Bulletin record);

    Bulletin selectByPrimaryKey(Integer id);

    List<Bulletin> selectAll();

    int updateByPrimaryKey(Bulletin record);

    List<Bulletin> selectBySourceId(Integer id);

    PageInfo<Bulletin> selectAllBySourceId(Integer pageNumber, Integer sourceId);

    int updateTitleAndContentById(Bulletin record);
}
