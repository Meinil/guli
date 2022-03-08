package com.meinil.eduservice.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author Meinil
 * @Version 1.0
 */
@FeignClient(name = "server-order")
@Component
public interface OrderFeignClient {
    @ApiOperation("是否购买了该课程")
    @GetMapping("/eduorder/paylog/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId);
}
