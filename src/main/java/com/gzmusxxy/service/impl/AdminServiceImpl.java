package com.gzmusxxy.service.impl;

import com.gzmusxxy.entity.Admin;
import com.gzmusxxy.mapper.AdminMapper;
import com.gzmusxxy.service.AdminService;
import com.gzmusxxy.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper admimMapper;

    @Override
    public int verify(String username, String password) {
        Admin admin = admimMapper.selectByUsername(username);
        if (admin != null && admin.getUsername() != null && !admin.getUsername().equals("")){
            if (password != null && !password.equals("")){
                if (EncryptUtil.me.Base64Decode(admin.getPassword()).equals(password)){
                    return admin.getId();
                }
            }
        }
        return 0;
    }

    @Override
    public Admin selectByPrimaryKey(Integer id) {
        return admimMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Admin record) {
        Admin admin = admimMapper.selectByPrimaryKey(record.getId());
        if (!admin.getPassword().equals(record.getPassword())){
            //不相等
            record.setPassword(EncryptUtil.me.Base64Encode(record.getPassword()));
        }
        return admimMapper.updateByPrimaryKey(record);
    }

    @Override
    public Admin selectByRole(Integer role) {
        return admimMapper.selectByRole(role);
    }

    /**
     * 查询权限  不需要的则null
     * @param roleA
     * @param roleB
     * @return
     */
    @Override
    public List<String> selectEmailByRole(Integer roleA, Integer roleB) {
        if (roleA == null){
            roleA = 0;
        }
        if (roleB == null){
            roleB = 0;
        }
        return admimMapper.selectEmailByRole(roleA,roleB);
    }
}
