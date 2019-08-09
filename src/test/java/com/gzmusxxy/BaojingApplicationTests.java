package com.gzmusxxy;

import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.XjhbInformation;
import com.gzmusxxy.service.XjhbInformationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaojingApplicationTests {

    @Autowired
    private XjhbInformationService xjhbInformationService;

    @Test
    public void contextLoads() {
        PageInfo<XjhbInformation> xjhbInformationPageInfo = xjhbInformationService.selectInformationByNameLike(null, 1);
        System.out.println(xjhbInformationPageInfo);
    }


}
