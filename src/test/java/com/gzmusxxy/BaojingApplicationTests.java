package com.gzmusxxy;

import com.gzmusxxy.entity.XjhbInformation;
import com.gzmusxxy.service.BxInsuranceService;
import com.gzmusxxy.service.BxProjectService;
import com.gzmusxxy.service.XjhbInformationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BaojingApplicationTests {

    @Autowired
    private BxProjectService bxProjectService;

    @Autowired
    private BxInsuranceService bxInsuranceService;

    @Autowired
    private XjhbInformationService xjhbInformationService;

    @Test
    public void contextLoads() {
        List<XjhbInformation> xjhbInformation = xjhbInformationService.selectInformationByOpenId("");

        System.out.println(xjhbInformation);
    }


}
