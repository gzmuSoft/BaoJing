package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.IndustryGoods;
import java.util.List;

public interface IndustryGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IndustryGoods record);

    IndustryGoods selectByPrimaryKey(Integer id);

    List<IndustryGoods> selectAll();

    int updateByPrimaryKey(IndustryGoods record);
}