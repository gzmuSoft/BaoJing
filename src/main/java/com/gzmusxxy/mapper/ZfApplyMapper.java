package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.ZfApply;
import java.util.List;

public interface ZfApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZfApply record);

    ZfApply selectByPrimaryKey(Integer id);

    List<ZfApply> selectAll();

    int updateByPrimaryKey(ZfApply record);

    List<ZfApply> selectByNameLike(String name);

    List<ZfApply> selectCompleteByNameLike(String name);
}