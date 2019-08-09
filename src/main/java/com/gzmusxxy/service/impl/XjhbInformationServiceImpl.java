package com.gzmusxxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.XjhbInformation;
import com.gzmusxxy.entity.XjhbProject;
import com.gzmusxxy.mapper.XjhbInformationMapper;
import com.gzmusxxy.service.XjhbInformationService;
import com.gzmusxxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public PageInfo<XjhbInformation> selectInformationByNameLike(String name,Integer pageNumber) {
        if (name != null && name != ""){
            name = "%" + name + "%";
        }else {
            name = "%%";
        }
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<XjhbInformation> list = xjhbInformationMapper.selectInformationByNameLike(name);
        return new PageInfo<>(list);
    }


}
