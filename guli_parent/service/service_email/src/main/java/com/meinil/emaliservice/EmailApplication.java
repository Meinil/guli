package com.meinil.emaliservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author Meinil
 * @Version 1.0
 */
@ComponentScan(basePackages = {"com.meinil"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class EmailApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmailApplication.class, args);
    }
}
