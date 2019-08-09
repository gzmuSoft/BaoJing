package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.XjhbInformation;
import com.gzmusxxy.entity.XjhbProject;

import java.util.List;

public interface XjhbInformationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(XjhbInformation record);

    XjhbInformation selectByPrimaryKey(Integer id);

    List<XjhbInformation> selectAll();

    int updateByPrimaryKey(XjhbInformation record);

    List<XjhbInformation> selectInformationByNameLike(String name);

    XjhbInformation findInfobyPersonId(int personId);
}
