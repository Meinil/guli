package com.meinil.staservice.controller;


import com.meinil.commonutils.R;
import com.meinil.staservice.service.StatisticsDailyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-06
 */
@RestController
@RequestMapping("/staservice/statistics")
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService staService;

    @ApiOperation("生成某一天的注册人数")
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day) {
        staService.registerCount(day);
        return R.ok();
    }

    @ApiOperation("查询图表显示的数据")
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,
                      @PathVariable String begin,
                      @PathVariable String end) {
        Map<String, Object> data = staService.getShowData(type, begin, end);
        return R.ok()
                .data(data);
    }
}

