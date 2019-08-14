package com.gzmusxxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gzmusxxy.mapper")
public class
BaojingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaojingApplication.class, args);
        System.out.println("管理员：http://127.0.0.1:8083/admin/login");
        System.out.println("微信登录：http://127.0.0.1:8083/wechat/demo");
        System.out.println("先建后补：http://127.0.0.1:8083/poverty/apply");
    }

}
