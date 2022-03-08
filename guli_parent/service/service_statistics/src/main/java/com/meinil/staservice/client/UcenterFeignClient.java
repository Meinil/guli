package com.meinil.staservice.client;

import com.meinil.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author Meinil
 * @Version 1.0
 */
@FeignClient(name = "server-ucenter")
public interface UcenterFeignClient {
    @ApiOperation("查询某一天的注册人数")
    @GetMapping("/educenter/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
