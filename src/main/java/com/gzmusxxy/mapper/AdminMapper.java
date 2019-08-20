package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.Admin;
import java.util.List;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    Admin selectByPrimaryKey(Integer id);

    Admin selectByUsername(String username);

    List<Admin> selectAll();

    int updateByPrimaryKey(Admin record);

    List<String> selectEmailByRole(Integer roleA,Integer roleB);
}
