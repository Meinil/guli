package com.meinil.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author Meinil
 * @Version 1.0
 */
@SpringBootApplication
@ComponentScan("com.meinil")
@MapperScan(basePackages = {"com.meinil.educenter.mapper"})
public class CenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(CenterApplication.class, args);
    }
}
