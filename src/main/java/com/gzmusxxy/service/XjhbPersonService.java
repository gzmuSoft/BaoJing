package com.gzmusxxy.service;

import com.gzmusxxy.entity.XjhbPerson;
import org.springframework.stereotype.Controller;

/**
 * @Author: yxf
 * @Date: 2019/8/5 17:54
 * @Version 1.0
 */
@Controller
public interface XjhbPersonService {

    int insert(XjhbPerson record);
}
