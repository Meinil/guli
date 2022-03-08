package com.meinil.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meinil.commonutils.R;
import com.meinil.commonutils.ResultCode;
import com.meinil.servicebase.exceptionhandler.GuliException;
import com.meinil.staservice.client.UcenterFeignClient;
import com.meinil.staservice.entity.StatisticsDaily;
import com.meinil.staservice.mapper.StatisticsDailyMapper;
import com.meinil.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-06
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterFeignClient ucenterClient;

    @Override
    public void registerCount(String day) {
        R register = ucenterClient.countRegister(day);
        if (register.getCode().equals(ResultCode.ERROR)) {
            throw new GuliException(ResultCode.ERROR, register.getMessage());
        }
        Integer count = (Integer) register.getData().get("count");
        StatisticsDaily statistics = new StatisticsDaily();
        statistics.setRegisterNum(count);
        statistics.setDateCalculated(day);

        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        int i = baseMapper.selectCount(wrapper);
        if (i > 0) {
            baseMapper.update(statistics, wrapper);
        } else {
            baseMapper.insert(statistics);
        }
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.select(type, "date_calculated");
        wrapper.between("date_calculated", begin, end);
        List<StatisticsDaily> statisticsDailies = baseMapper.selectList(wrapper);
        List<String> xData = new ArrayList<>();
        List<Integer> yData = new ArrayList<>();
        for (StatisticsDaily daily : statisticsDailies) {
            xData.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    yData.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    yData.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    yData.add(daily.getVideoViewNum());
                    break;
                default:
                    yData.add(daily.getCourseNum());
                    break;
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("xData", xData);
        data.put("yData", yData);
        return data;
    }
}
