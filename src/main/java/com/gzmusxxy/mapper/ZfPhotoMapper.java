package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.ZfPhoto;
import java.util.List;

public interface ZfPhotoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZfPhoto record);

    ZfPhoto selectByPrimaryKey(Integer id);

    List<ZfPhoto> selectAll();

    int updateByPrimaryKey(ZfPhoto record);

    List<ZfPhoto> selectByNameLike(String name);

    ZfPhoto selectByApplyId(Integer id);

    int updateByApplyId(ZfPhoto record);
}