package com.meinil.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author Meinil
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.meinil.educms.mapper")
@ComponentScan(basePackages = {"com.meinil"})
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
