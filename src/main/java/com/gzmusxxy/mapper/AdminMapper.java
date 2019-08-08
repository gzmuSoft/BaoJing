package com.gzmusxxy.mapper;

import com.gzmusxxy.entity.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    Admin selectByPrimaryKey(Integer id);

    Admin selectByUsername(String username);

    List<Admin> selectAll();

    int updateByPrimaryKey(Admin record);
}