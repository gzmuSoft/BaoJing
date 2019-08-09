package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.XjhbInformation;

public interface XjhbInformationService {

    /**
     * 保存申请信息
     * */
    int saveInformation(XjhbInformation xjhbInformation);


    XjhbInformation findInfobyPersonId(int personId);

    PageInfo<XjhbInformation> selectInformationByNameLike(String name, Integer pageNumber);
}
