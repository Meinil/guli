package com.meinil.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author Meinil
 * @Version 1.0
 */
// 启用远程调用
@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"com.meinil.servicebase", "com.meinil.eduservice"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
