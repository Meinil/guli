package com.meinil.eduorder.service;

import com.meinil.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author meinil
 * @since 2022-03-05
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 根据订单号生成微信支付二维码
     */
    Map createNative(String orderNo);

    /**
     * 根据订单号查询订单信息
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     * 更新订单状态
     */
    void updateOrderStatus(Map<String, String> map);
}
