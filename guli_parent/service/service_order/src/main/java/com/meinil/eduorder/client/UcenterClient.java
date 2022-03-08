package com.meinil.eduorder.client;

import com.meinil.commonutils.ordervo.CourseWebOrder;
import com.meinil.commonutils.ordervo.UcenterMemberOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Component
@FeignClient(name = "server-ucenter")
public interface UcenterClient {
    @ApiOperation("根据用户id获取用户信息")
    @PostMapping("/educenter/member/getUserInfo/{id}")
    public UcenterMemberOrder getUserInfo(@PathVariable("id") String id);
}
