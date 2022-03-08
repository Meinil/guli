package com.meinil.educenter.controller;

import com.meinil.commonutils.ResultCode;
import com.meinil.educenter.service.UcenterMemberService;
import com.meinil.educenter.utils.WechatConstant;
import com.meinil.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Duration;
import java.util.UUID;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Controller
@RequestMapping("/api/ucenter/wx")
public class WechatController {
    @Autowired
    private UcenterMemberService memberService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation("生成微信扫描二维码")
    @GetMapping("login")
    public String getWechatCode() {
        String state = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("state_" + state, state, Duration.ofMinutes(5));
        return String.format("redirect:https://open.weixin.qq.com/connect/qrconnec?appid=%s&redirect_uri=%s&response_type=code&scope=SCOPE&state=%s#wechat_redirect",
                WechatConstant.WECHAT_APP_ID,
                WechatConstant.REDIRECT_URL,
                state);
    }

    @ApiOperation("获取用户信息,添加数据")
    @GetMapping("callback")
    public String callback(String code, String state) {
        String cacheState = redisTemplate.opsForValue().get("state_" + state);
        if (StringUtils.isEmpty(cacheState)) {
            throw new GuliException(ResultCode.ERROR, "state已过期");
        }
        return memberService.getWechatRequestAddress(code);
    }
}
