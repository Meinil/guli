package com.meinil.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author Meinil
 * @Version 1.0
 */
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
@MapperScan("com.meinil.staservice.mapper")
@ComponentScan(basePackages = {"com.meinil"})
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class, args);
    }
}
