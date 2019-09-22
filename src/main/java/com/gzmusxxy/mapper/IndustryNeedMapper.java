package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.IndustryNeed;
import org.springframework.stereotype.Component;

import java.util.List;
public interface IndustryNeedMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IndustryNeed record);

    IndustryNeed selectByPrimaryKey(Integer id);

    List<IndustryNeed> selectAll();

    int updateByPrimaryKey(IndustryNeed record);

    List<IndustryNeed> selectByType(Integer type);

    int selectCountByUserIdAndType(Integer userId,Integer type);

    int updateReadNumber(Integer id);
    List<IndustryNeed> selectByNameType(IndustryNeed record);
}