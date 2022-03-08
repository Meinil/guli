package com.meinil.emaliservice.service;

/**
 * @Author Meinil
 * @Version 1.0
 */
public interface EmailService {

    /**
     * 发送验证码
     */
    String send(String email);
}
