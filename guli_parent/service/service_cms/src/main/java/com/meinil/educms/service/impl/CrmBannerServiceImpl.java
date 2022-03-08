package com.meinil.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meinil.educms.entity.CrmBanner;
import com.meinil.educms.mapper.CrmBannerMapper;
import com.meinil.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author meinil
 * @since 2022-03-02
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    /**
     * 查询所有banner
     * Cacheable 对方法的返回值进行缓存,下次请求时如果缓存存在则直接读取缓存
     */
    @Override
    @Cacheable(key = "'selectAllBanner'", value = "banners")
    public List<CrmBanner> selectAllBanner() {
        // 根据修改时间进行降序排列,并且只查前4条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title","image_url", "link_url", "gmt_modified");
        wrapper.orderByDesc("gmt_modified");
        wrapper.last("limit 4");
        return baseMapper.selectList(wrapper);
    }
}
