package com.meinil.educms.controller;


import com.meinil.commonutils.R;
import com.meinil.educms.entity.CrmBanner;
import com.meinil.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author meinil
 * @since 2022-03-02
 */
@RestController
@RequestMapping("/educms/bannerfront")
public class CrmBannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation("查询所有的banner")
    @GetMapping("allBanner")
    public R getAllBanner() {
        return R.ok()
                .data("banners", bannerService.selectAllBanner());
    }
}

