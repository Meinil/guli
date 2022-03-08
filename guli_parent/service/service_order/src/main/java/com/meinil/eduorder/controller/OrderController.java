package com.meinil.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meinil.commonutils.JwtUtils;
import com.meinil.commonutils.R;
import com.meinil.eduorder.entity.Order;
import com.meinil.eduorder.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author meinil
 * @since 2022-03-05
 */
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation("生成订单,返回订单id")
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request) {
        String orderId =  orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok()
                .data("orderId", orderId);
    }

    @ApiOperation("根据订单id查询订单信息")
    @GetMapping("getOrderInfo/{id}")
    public R getOrderInfo(@PathVariable String id) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", id);
        return R.ok()
                .data("order", orderService.getOne(wrapper));
    }
}

