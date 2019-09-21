package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.YlGuarantee;
import java.util.List;

public interface YlGuaranteeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YlGuarantee record);

    YlGuarantee selectByPrimaryKey(Integer id);

    List<YlGuarantee> selectAll();

    int updateByPrimaryKey(YlGuarantee record);

    List<YlGuarantee> selectByNameCostLike(String name);

    List<YlGuarantee> selectByNameLike(String name);

    List<YlGuarantee> selectAccountByNameLike(String name);

    List<YlGuarantee> selectAllByStatus(Integer status);

    int updateStatus(Integer front, Integer after);
}