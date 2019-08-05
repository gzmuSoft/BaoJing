package com.gzmusxxy.service;

import com.gzmusxxy.entity.XjhbInformation;
import org.springframework.stereotype.Controller;

/**
 * @Author: yxf
 * @Date: 2019/8/5 17:53
 * @Version 1.0
 */
@Controller
public interface XjhbInformationService {

    /**
     * 保存申请信息
     * */
    int saveInformation(XjhbInformation xjhbInformation);
}
