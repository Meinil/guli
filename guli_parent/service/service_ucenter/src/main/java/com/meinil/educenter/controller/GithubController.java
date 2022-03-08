package com.meinil.educenter.controller;

import com.alibaba.fastjson.annotation.JSONField;
import com.meinil.commonutils.ResultCode;
import com.meinil.educenter.service.UcenterMemberService;
import com.meinil.educenter.utils.GithubConstant;
import com.meinil.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.util.UUID;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Controller
@RequestMapping("/educenter/member/github")
public class GithubController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation("授权登录")
    @GetMapping("login")
    public String getGithubCode() {
        String state = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("state_"+state, state, Duration.ofMinutes(5));
        return String.format("redirect:https://github.com/login/oauth/authorize?client_id=%s&redirect_uri=%s&state=%s",
                GithubConstant.GITHUB_CLIENT_ID,
                GithubConstant.REDIRECT_URL,
                state);
    }

    @ApiOperation("github回调地址")
    @GetMapping("callback")
    public String callback(@RequestParam String code,
                           @RequestParam String state) {
        String cacheState = redisTemplate.opsForValue().get("state_" + state);
        if (cacheState == null) {
            throw new GuliException(ResultCode.ERROR, "授权码已过期,请重新授权");
        }
        return memberService.getGithubRequestAddress(code);
    }
}
