package com.meinil.eduservice.client;

import com.meinil.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Meinil
 * @Version 1.0
 */
@FeignClient(name = "server-ucenter", fallback = UcenterFeignClient.class)
@Component
public interface UcenterClient {
    @ApiOperation("根据token获取用户信息")
    @GetMapping("/educenter/member/getMemberInfo/{token}")
    public R getMemberInfo(@PathVariable("token") String token);

    @ApiOperation("根据token获取用户信息")
    @GetMapping("/educenter/member/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request);
}
