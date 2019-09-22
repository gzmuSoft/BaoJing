package com.gzmusxxy.service;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.IndustryNeed;
import com.gzmusxxy.entity.IndustryUser;

/**
 * @Author QFMX
 * @Date 2019/9/21 19:09
 * @Description 供销交流平台业务逻辑
 */
public interface MarketAdminService {
    /**
     * 分页查询用户
     * @param pageNumber 页码
     * @return 返回用户分页信息
     */
    PageInfo<IndustryUser> getUser(int pageNumber);

    /**
     * 分页查询供需信息
     * @param pageNumber 页码
     * @return 返回供需分页信息
     */
    PageInfo<IndustryNeed> getNeed(int pageNumber);
    /**
     * 根据需求类型和用户名查询需求信息
     * @param pageNumber 页码
     * @param record 需求信息
     * @return 返回需求信息
     */
    PageInfo<IndustryNeed> getNeedByNameType(int pageNumber,IndustryNeed record);

    /**
     * 根据用户名查询用户信息
     * @param pageNumber 页码
     * @param user 用户信息
     * @return 分页信息
     */
    PageInfo<IndustryUser> getUserByName(int pageNumber,IndustryUser user);
}
