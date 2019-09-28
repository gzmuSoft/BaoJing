package com.gzmusxxy.service.impl;

import com.gzmusxxy.entity.IndustryUser;
import com.gzmusxxy.mapper.IndustryUserMapper;
import com.gzmusxxy.service.IndustryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * @Description TODO
 * @Author RAINEROSION
 * @Date 2019/9/17 22:56
 */
@Service
public class IndustryUserServiceImpl implements IndustryUserService {

    @Autowired
    private IndustryUserMapper industryUserMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return industryUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(IndustryUser record) {
        return industryUserMapper.insert(record);
    }

    @Override
    public IndustryUser selectByPrimaryKey(Integer id) {
        return industryUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<IndustryUser> selectAll() {
        return industryUserMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(IndustryUser record) {
        return industryUserMapper.updateByPrimaryKey(record);
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return 失败返回null，成功返回实体
     */
    @Override
    public IndustryUser login(String username,String password) {
        IndustryUser login = industryUserMapper.selectUserByUserName(username);
        if(login != null){
            String psw = DigestUtils.md5DigestAsHex(password.getBytes());
            System.out.println(psw);
            if(!login.getPassword().equals(psw)){
                return null;
            }
            return login;
        }else{
            return null;
        }
    }

    @Override
    public IndustryUser selectUserByUserName(String username) {
        return industryUserMapper.selectUserByUserName(username);
    }

    @Override
    public int updateNameAndPhoneById(IndustryUser record) {
        return industryUserMapper.updateNameAndPhoneById(record);
    }

    @Transactional
    @Override
    public int updateById(IndustryUser record) {
        return this.industryUserMapper.updateById(record);
    }
}
