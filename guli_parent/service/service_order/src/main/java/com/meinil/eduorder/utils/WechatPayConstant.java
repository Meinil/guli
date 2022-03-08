package com.meinil.eduorder.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Data
@Component
public class WechatPayConstant implements InitializingBean {
    @Value("${wechat.pay.appid}")
    private String appid;
    @Value("${wechat.pay.partner}")
    private String partner;
    @Value("${wechat.pay.partner-key}")
    private String partnerKey;
    @Value("${wechat.pay.notify-url}")
    private String notifyUrl;
    @Value("${wechat.pay.addr}")
    private String addr;

    public static String APPID;
    public static String PARTNER;
    public static String PARTNER_KEY;
    public static String NOTIFY_URL;
    public static String ADDR;

    @Override
    public void afterPropertiesSet() throws Exception {
        APPID = this.appid;
        PARTNER = this.partner;
        PARTNER_KEY = this.partnerKey;
        NOTIFY_URL = this.notifyUrl;
        ADDR = this.addr;
    }
}
