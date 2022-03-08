package com.meinil.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Component
public class OssConstantUtils implements InitializingBean {
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.file.key-id}")
    private String keyId;
    @Value("${aliyun.oss.file.key-secrete}")
    private String keySecret;
    @Value("${aliyun.oss.file.bucket-name}")
    private String bucketName;

    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = this.endpoint;
        KEY_ID = this.keyId;
        KEY_SECRET = this.keySecret;
        BUCKET_NAME = this.bucketName;
    }
}
