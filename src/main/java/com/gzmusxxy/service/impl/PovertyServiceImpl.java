package com.gzmusxxy.service.impl;

import com.gzmusxxy.entity.XjhbPerson;
import com.gzmusxxy.mapper.XjhbPersonMapper;
import com.gzmusxxy.service.PovertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PovertyServiceImpl implements PovertyService {

    @Autowired
    private XjhbPersonMapper xjhbPersonMapper;

    @Override
    public int insert(XjhbPerson record) {
        return xjhbPersonMapper.insert(record);
    }
}
