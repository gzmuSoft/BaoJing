package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.XjhbInformation;
import java.util.List;

public interface XjhbInformationMapper {
    int insert(XjhbInformation record);

    List<XjhbInformation> selectAll();
}