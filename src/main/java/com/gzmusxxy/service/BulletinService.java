package com.gzmusxxy.service;

import com.gzmusxxy.entity.Bulletin;

import java.util.List;

public interface BulletinService {

    int deleteByPrimaryKey(Integer id);

    Bulletin insert(Bulletin record);

    Bulletin selectByPrimaryKey(Integer id);

    List<Bulletin> selectAll();

    int updateByPrimaryKey(Bulletin record);

    Bulletin selectBySourceId(Integer id);
}
