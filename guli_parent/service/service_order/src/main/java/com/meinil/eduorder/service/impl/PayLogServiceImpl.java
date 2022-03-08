package com.meinil.eduorder.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.meinil.commonutils.ResultCode;
import com.meinil.eduorder.entity.Order;
import com.meinil.eduorder.entity.PayLog;
import com.meinil.eduorder.mapper.PayLogMapper;
import com.meinil.eduorder.service.OrderService;
import com.meinil.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meinil.eduorder.utils.HttpClient;
import com.meinil.eduorder.utils.WechatPayConstant;
import com.meinil.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author meinil
 * @since 2022-03-05
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map createNative(String orderNo) {
        // 查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);

        // 使用map设置生成二维码所用的参数
        Map<String, String> map = new HashMap<>();
        map.put("appid", WechatPayConstant.APPID);
        map.put("mch_id", WechatPayConstant.PARTNER);
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        map.put("body",order.getCourseTitle());
        map.put("out_trade_no", orderNo);
        map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
        map.put("spbill_create_ip", WechatPayConstant.ADDR);
        map.put("notify_url", WechatPayConstant.NOTIFY_URL);
        map.put("trade_type", "NATIVE");

        try {
            // 发送http请求,传递参数类型为xml格式
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setXmlParam(WXPayUtil.generateSignedXml(map, WechatPayConstant.PARTNER_KEY));
            client.setHttps(true);
            client.post();

            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            Map finalResult = new HashMap();
            finalResult.put("out_trade_no", orderNo);
            finalResult.put("course_id", order.getCourseId());
            finalResult.put("total_fee", order.getTotalFee());
            finalResult.put("result_code", resultMap.get("result_code"));
            finalResult.put("code_url", resultMap.get("code_url"));

            return finalResult;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 查询订单状态
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", WechatPayConstant.APPID);
        map.put("mch_id", WechatPayConstant.PARTNER);
        map.put("out_trade_no", orderNo);
        map.put("nonce_str", WXPayUtil.generateNonceStr());

        // 发送http请求
        try {
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(map, WechatPayConstant.PARTNER_KEY));
            client.setHttps(true);
            client.post();

            String xml = client.getContent();
            return WXPayUtil.xmlToMap(xml);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 更新订单状态
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        // 获取订单号
        String orderNo = map.get("out_trade_no");
        // 查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);

        // 0 为未支付 1为支付
        if (!order.getStatus().equals(1)) {
            order.setStatus(1);
            orderService.updateById(order);

            // 支付表中添加支付记录
            PayLog payLog = new PayLog();
            payLog.setOrderNo(orderNo);
            // 支付时间
            payLog.setPayTime(new Date());
            // 支付类型
            payLog.setPayType(1);
            // 总金额(分)
            payLog.setTotalFee(order.getTotalFee());
            // 支付状态
            payLog.setTradeState(map.remove("trade_state"));
            // 支付流水号
            payLog.setTransactionId(map.remove("transaction_id"));
            payLog.setAttr(JSON.toJSONString(map));
            baseMapper.insert(payLog);
        }
    }
}
