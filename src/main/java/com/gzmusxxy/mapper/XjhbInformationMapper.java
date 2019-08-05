package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.XjhbInformation;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public interface XjhbInformationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(XjhbInformation record);

    XjhbInformation selectByPrimaryKey(Integer id);

    List<XjhbInformation> selectAll();

    int updateByPrimaryKey(XjhbInformation record);
}
