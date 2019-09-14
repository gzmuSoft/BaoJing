package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.YlGuarantee;
import java.util.List;

public interface YlGuaranteeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YlGuarantee record);

    YlGuarantee selectByPrimaryKey(Integer id);

    List<YlGuarantee> selectAll();

    int updateByPrimaryKey(YlGuarantee record);
}