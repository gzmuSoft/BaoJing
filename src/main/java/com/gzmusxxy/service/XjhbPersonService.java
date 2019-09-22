package com.gzmusxxy.service;

import com.gzmusxxy.entity.XjhbPerson;


public interface XjhbPersonService {

    int insert(XjhbPerson record);

    XjhbPerson findPersonByOpenId(String openId);

    int updateByPrimaryKey(XjhbPerson record);

    XjhbPerson selectByPrimaryKey(Integer id);

    int updateNameAndIdentityById(XjhbPerson record);

    int updateIdCardFrontAndIdCardReverseById(XjhbPerson record);
}
