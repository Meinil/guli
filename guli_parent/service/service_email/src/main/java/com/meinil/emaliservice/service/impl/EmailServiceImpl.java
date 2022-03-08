package com.meinil.emaliservice.service.impl;

import com.meinil.commonutils.ResultCode;
import com.meinil.emaliservice.service.EmailService;
import com.meinil.emaliservice.utils.RandomUtil;
import com.meinil.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String sendFromEmail;

    @Override
    public String send(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendFromEmail);
        message.setTo(email);
        message.setSubject("验证码");
        // 生成验证码
        String code = RandomUtil.getFourBitRandom();
        message.setText(String.format("你正在注册谷粒学院的验证码为: 【%s】, 该验证码5分钟内有效,请勿将其转发", code));
        try {
            sender.send(message);
        } catch (Exception e) {
            throw new GuliException(ResultCode.ERROR, e.getMessage());
        }

        return code;
    }
}
