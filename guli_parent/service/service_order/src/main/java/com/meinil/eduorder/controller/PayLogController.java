package com.meinil.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meinil.commonutils.R;
import com.meinil.commonutils.ResultCode;
import com.meinil.eduorder.entity.Order;
import com.meinil.eduorder.service.OrderService;
import com.meinil.eduorder.service.PayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author meinil
 * @since 2022-03-05
 */
@RestController
@RequestMapping("/eduorder/paylog")
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    @Autowired
    private OrderService orderService;

    @ApiOperation("生成微信支付二维码")
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        Map map = payLogService.createNative(orderNo);
        return R.ok()
                .data(map);
    }

    @ApiOperation("查询订单状态")
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            return R.error()
                    .message("支付失败了");
        }

        // 获取订单状态
        if ("SUCCESS".equals(map.get("trade_state"))){
            payLogService.updateOrderStatus(map);
            return R.ok()
                    .message("支付成功");
        }
        return R.ok()
                .message("支付中");
    }

    @ApiOperation("是否购买了该课程")
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,
                         @PathVariable String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);

        return orderService.count(wrapper) > 0;
    }
}

