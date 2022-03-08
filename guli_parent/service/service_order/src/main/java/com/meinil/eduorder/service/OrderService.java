package com.meinil.eduorder.service;

import com.meinil.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author meinil
 * @since 2022-03-05
 */
public interface OrderService extends IService<Order> {

    /**
     * 生成订单号
     */
    String createOrder(String courseId, String memberId);
}
