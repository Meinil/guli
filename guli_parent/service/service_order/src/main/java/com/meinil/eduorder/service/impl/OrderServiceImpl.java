package com.meinil.eduorder.service.impl;

import com.meinil.commonutils.ordervo.CourseWebOrder;
import com.meinil.commonutils.ordervo.UcenterMemberOrder;
import com.meinil.eduorder.client.EduClient;
import com.meinil.eduorder.client.UcenterClient;
import com.meinil.eduorder.entity.Order;
import com.meinil.eduorder.mapper.OrderMapper;
import com.meinil.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author meinil
 * @since 2022-03-05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String createOrder(String courseId, String memberId) {
        CourseWebOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        UcenterMemberOrder userInfo = ucenterClient.getUserInfo(memberId);
        // 生成订单号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String orderId = sdf.format(date) + memberId;

        Order order = new Order();
        order.setOrderNo(orderId);
        order.setCourseId(courseInfoOrder.getId());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfo.getMobile());
        order.setNickname(userInfo.getNickname());
        order.setStatus(0);
        order.setPayType(1);

        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
