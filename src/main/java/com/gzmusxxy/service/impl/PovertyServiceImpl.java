package com.gzmusxxy.service.impl;

import com.gzmusxxy.entity.XjhbInformation;
import com.gzmusxxy.entity.XjhbPerson;
import com.gzmusxxy.entity.XjhbProject;
import com.gzmusxxy.mapper.XjhbInformationMapper;
import com.gzmusxxy.mapper.XjhbPersonMapper;
import com.gzmusxxy.mapper.XjhbProjectMapper;
import com.gzmusxxy.service.PovertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PovertyServiceImpl implements PovertyService {

    private final XjhbInformationMapper xjhbInformationMapper;

    private final XjhbPersonMapper xjhbPersonMapper;

    @Autowired
    public PovertyServiceImpl(XjhbPersonMapper xjhbPersonMapper, XjhbProjectMapper xjhbProjectMapper, XjhbInformationMapper xjhbInformationMapper) {
        this.xjhbPersonMapper = xjhbPersonMapper;
        this.xjhbProjectMapper = xjhbProjectMapper;
        this.xjhbInformationMapper = xjhbInformationMapper;
    }

    final
    XjhbProjectMapper xjhbProjectMapper;
    @Override
    public int insert(XjhbPerson record) {
        return xjhbPersonMapper.insert(record);
    }


    /**
     * 查询项目名称
     * */
    @Override
    public List<XjhbProject> findXjhbProject() {

        return xjhbProjectMapper.selectAll();
    }

    /***
     * 保存申请信息
     */
    @Override
    public int saveInformation(XjhbInformation xjhbInformation){

        return xjhbInformationMapper.insert(xjhbInformation);
    }

}
