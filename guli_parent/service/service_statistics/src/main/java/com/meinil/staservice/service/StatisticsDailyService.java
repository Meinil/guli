package com.meinil.staservice.service;

import com.meinil.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-06
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    /**
     * 统计某一天的注册人数
     */
    void registerCount(String day);

    /**
     * 查询图表信息
     * @param type 数据类型
     * @param begin 开始时间
     * @param end 结束时间
     */
    Map<String, Object> getShowData(String type, String begin, String end);
}
