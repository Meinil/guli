package com.meinil.educms.service;

import com.meinil.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author meinil
 * @since 2022-03-02
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 前台用户查询所有banner
     */
    List<CrmBanner> selectAllBanner();
}
