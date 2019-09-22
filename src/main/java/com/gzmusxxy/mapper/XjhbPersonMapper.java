package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.XjhbPerson;
import java.util.List;

public interface XjhbPersonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(XjhbPerson record);

    XjhbPerson selectByPrimaryKey(Integer id);

    List<XjhbPerson> selectAll();

    int updateByPrimaryKey(XjhbPerson record);

    XjhbPerson findPersonByOpenId(String openId);

    int updateNameAndIdentityById(XjhbPerson record);

    int updateIdCardFrontAndIdCardReverseById(XjhbPerson record);
}
