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
        System.out.println("管理员入口：http://127.0.0.1:8083/admin/login");
    }

}
