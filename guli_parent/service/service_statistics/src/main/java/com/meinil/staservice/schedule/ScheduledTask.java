package com.meinil.staservice.schedule;

import com.meinil.staservice.service.StatisticsDailyService;
import com.meinil.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService statisticsService;

    // corn表达式生成 https://cron.qqe2.com/
    @Scheduled(cron = "0 0 1 * * ?")
    public void task1() {
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsService.registerCount(day);
    }
}
