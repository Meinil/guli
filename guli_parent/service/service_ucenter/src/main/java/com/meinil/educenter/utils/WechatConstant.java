package com.meinil.educenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Component
public class WechatConstant implements InitializingBean {
    @Value("${wechat.open.app-id}")
    private String appId;

    @Value("${wechat.open.app-secret}")
    private String appSecret;

    @Value("${wechat.open.redirect-url}")
    private String redirectUrl;

    public static String WECHAT_APP_ID;
    public static String WECHAT_APP_SECRET;
    public static String REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        WECHAT_APP_ID = appId;
        WECHAT_APP_SECRET = appSecret;
        REDIRECT_URL = URLEncoder.encode(redirectUrl, "utf-8");
    }
}
