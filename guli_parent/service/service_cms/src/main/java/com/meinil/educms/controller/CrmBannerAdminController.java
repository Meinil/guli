package com.meinil.educms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meinil.commonutils.R;
import com.meinil.educms.entity.CrmBanner;
import com.meinil.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author meinil
 * @since 2022-03-02
 */
@RestController
@RequestMapping("/educms/banneradmin")
public class CrmBannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation("分页查询banner")
    @GetMapping("pageBanner/{current}/{limit}")
    public R pageBanner(@PathVariable long current, @PathVariable long limit) {
        Page<CrmBanner> page = new Page<>(current, limit);
        bannerService.page(page, null);
        return R.ok()
                .data("banner", page.getRecords())
                .data("total", page.getTotal());
    }

    @ApiOperation("添加banner")
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner banner) {
        bannerService.save(banner);
        return R.ok();
    }

    @ApiOperation("修改banner")
    @PostMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation("删除banner")
    @DeleteMapping("{id}")
    public R deleteBanner(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }
}

