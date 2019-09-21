package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.Bulletin;
import java.util.List;

public interface BulletinMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bulletin record);

    Bulletin selectByPrimaryKey(Integer id);

    List<Bulletin> selectAll();

    int updateByPrimaryKey(Bulletin record);

    List<Bulletin> selectBySourceId(Integer id);
}