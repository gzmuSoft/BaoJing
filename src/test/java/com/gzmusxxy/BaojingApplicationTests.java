package com.gzmusxxy;

import com.gzmusxxy.entity.XjhbProject;
import com.gzmusxxy.service.PovertyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaojingApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    PovertyService povertyService;

    @Test
    public void aaa(){
        List<XjhbProject> list = povertyService.findXjhbProject();
        for (XjhbProject a:list){
            System.out.println(a);
        }
    }
}
