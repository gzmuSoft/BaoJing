package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.ZfApply;
import java.util.List;

public interface ZfApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZfApply record);

    ZfApply selectByPrimaryKey(Integer id);

    List<ZfApply> selectAll();

    List<ZfApply> selectByStatus(Integer status);

    int updateByPrimaryKey(ZfApply record);

    List<ZfApply> selectByNameLike(String name);

    List<ZfApply> selectCompleteByNameLike(String name);

    List<ZfApply> selectByPersonId(Integer personId);
}