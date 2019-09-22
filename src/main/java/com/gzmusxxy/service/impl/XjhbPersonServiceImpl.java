package com.gzmusxxy.service.impl;

import com.gzmusxxy.entity.XjhbPerson;
import com.gzmusxxy.mapper.XjhbPersonMapper;
import com.gzmusxxy.service.XjhbPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yxf
 * @Date: 2019/8/5 17:54
 * @Version 1.0
 */
@Service
public class XjhbPersonServiceImpl implements XjhbPersonService {


    @Autowired
    XjhbPersonMapper xjhbPersonMapper;

    @Override
    public int insert(XjhbPerson record) {
        return xjhbPersonMapper.insert(record);
    }

    @Override
    public XjhbPerson findPersonByOpenId(String openId) {
        return xjhbPersonMapper.findPersonByOpenId(openId);
    }

    @Override
    public int updateByPrimaryKey(XjhbPerson record) {
        return xjhbPersonMapper.updateByPrimaryKey(record);
    }

    @Override
    public XjhbPerson selectByPrimaryKey(Integer id) {
        return xjhbPersonMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateNameAndIdentityById(XjhbPerson record) {
        return xjhbPersonMapper.updateNameAndIdentityById(record);
    }

    @Override
    public int updateIdCardFrontAndIdCardReverseById(XjhbPerson record) {
        return xjhbPersonMapper.updateIdCardFrontAndIdCardReverseById(record);
    }
}
