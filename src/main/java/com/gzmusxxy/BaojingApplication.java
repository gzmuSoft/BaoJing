package com.gzmusxxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gzmusxxy.mapper")
public class BaojingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaojingApplication.class, args);
        System.out.println("管理员：http://127.0.0.1:8083/admin/login");
        System.out.println("微信登录：http://127.0.0.1:8083/wechat/demo");
        System.out.println("先建后补：http://127.0.0.1:8083/poverty/apply");
        System.out.println("保险前台：http://127.0.0.1:8083/insurance");
        System.out.println("保险后台：http://127.0.0.1:8083/supply");
        System.out.println("多彩报京：http://127.0.0.1:8083/exhibition");
        System.out.println("医疗保障：http://127.0.0.1:8083/medical");
    }

}
