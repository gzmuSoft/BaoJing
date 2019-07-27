package com.gzmusxxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gzmusxxy.mapper")
public class BaojingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaojingApplication.class, args);
    }

}
