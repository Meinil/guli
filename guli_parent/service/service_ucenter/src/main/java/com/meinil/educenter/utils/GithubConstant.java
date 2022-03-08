package com.meinil.educenter.utils;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Data
@Component
public class GithubConstant implements InitializingBean{
    @Value("${github.open.client-id}")
    private String clientId;

    @Value("${github.open.client-secret}")
    private String clientSecret;

    @Value("${github.open.redirect-url}")
    private String redirectUrl;

    public static String GITHUB_CLIENT_ID;
    public static String CLIENT_SECRET;
    public static String REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        GITHUB_CLIENT_ID = clientId;
        CLIENT_SECRET = clientSecret;
        REDIRECT_URL = URLEncoder.encode(redirectUrl, "utf-8");
    }
}
