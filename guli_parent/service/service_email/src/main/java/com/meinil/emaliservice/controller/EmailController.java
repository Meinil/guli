package com.meinil.emaliservice.controller;

import com.meinil.commonutils.R;
import com.meinil.emaliservice.service.EmailService;
import com.meinil.emaliservice.utils.RandomUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

/**
 * @Author Meinil
 * @Version 1.0
 */
@RestController
@RequestMapping("/edumail/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation("发送邮件验证")
    @GetMapping("send/{email}")
    public R sendEmail(@PathVariable String email) {
        // 先从redis从取数据
        String code = redisTemplate.opsForValue().get(email);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }

        // redis中不存在,则发送验证码,并将其放入redis中
        code = emailService.send(email);
        redisTemplate.opsForValue().set(email, code, Duration.ofMinutes(5));
        return R.ok();
    }
}
