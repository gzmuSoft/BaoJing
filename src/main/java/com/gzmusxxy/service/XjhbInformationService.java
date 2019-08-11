package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.XjhbInformation;

import java.util.List;

public interface XjhbInformationService {

    /**
     * 保存申请信息
     * */
    int saveInformation(XjhbInformation xjhbInformation);

    PageInfo<XjhbInformation> selectApplyByNameLike(String name, Integer pageNumber);

    List<XjhbInformation> findInfobyPersonId(int personId);

    int updateByPrimaryKey(XjhbInformation record);

    XjhbInformation selectByPrimaryKey(Integer id);

    PageInfo<XjhbInformation> selectCheckByNameLike(String name,Integer pageNumber);

    PageInfo<XjhbInformation> selectAdoptByNameLike(String name,Integer pageNumber);

    List<XjhbInformation> selectAdoptByStatus(int status);

    Integer updateStatus();
}
