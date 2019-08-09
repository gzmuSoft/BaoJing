package com.gzmusxxy.service.impl;

import com.gzmusxxy.entity.XjhbInformation;
import com.gzmusxxy.mapper.XjhbInformationMapper;
import com.gzmusxxy.service.XjhbInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yxf
 * @Date: 2019/8/5 17:53
 * @Version 1.0
 */
@Service
public class XjhbInformationServiceImpl implements XjhbInformationService  {


    @Autowired
    private final XjhbInformationMapper xjhbInformationMapper;

    public XjhbInformationServiceImpl(XjhbInformationMapper xjhbInformationMapper) {
        this.xjhbInformationMapper = xjhbInformationMapper;
    }

    /***
     * 保存申请信息
     */
    @Override
    public int saveInformation(XjhbInformation xjhbInformation){

        return xjhbInformationMapper.insert(xjhbInformation);
    }

    @Override
    public XjhbInformation findInfobyPersonId(int personId) {
        return xjhbInformationMapper.findInfobyPersonId(personId);
    }

}
