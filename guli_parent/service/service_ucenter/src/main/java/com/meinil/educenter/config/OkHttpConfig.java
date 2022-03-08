package com.meinil.educenter.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Configuration
public class OkHttpConfig {
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
               .connectTimeout(Duration.ofMillis(5000))
               .build();
    }
}
