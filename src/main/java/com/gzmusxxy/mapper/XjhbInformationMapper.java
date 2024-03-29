package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.XjhbInformation;
import java.util.List;

public interface XjhbInformationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(XjhbInformation record);

    XjhbInformation selectByPrimaryKey(Integer id);

    List<XjhbInformation> selectAll();

    int updateByPrimaryKey(XjhbInformation record);

    List<XjhbInformation> selectApplyByNameLike(String name);

    List<XjhbInformation> selectCheckByNameLike(String name);

    List<XjhbInformation> selectAdoptByNameLike(String name);

    List<XjhbInformation> selectInformationByOpenId(String openId);

    List<XjhbInformation> selectAdoptByStatus(int status);

    int updateStatus();
}
