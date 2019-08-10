package com.gzmusxxy.mapper;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.XjhbInformation;
import java.util.List;

public interface XjhbInformationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(XjhbInformation record);

    XjhbInformation selectByPrimaryKey(Integer id);

    List<XjhbInformation> selectAll();

    int updateByPrimaryKey(XjhbInformation record);

    PageInfo<XjhbInformation> selectInformationByNameLike(String name, Integer pageNumber);

    List<XjhbInformation> findInfobyPersonId(int personId);

}
