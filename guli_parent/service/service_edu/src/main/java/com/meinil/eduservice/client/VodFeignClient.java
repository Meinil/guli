package com.meinil.eduservice.client;

import com.meinil.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 服务熔断后会执行的方法
 * @Author Meinil
 * @Version 1.0
 */
@Component
public class VodFeignClient implements VodClient {
    @Override
    public R removeAliyunVideo(String id) {
        return R.error()
                .message("删除视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error()
                .message("删除多个视频出错了");
    }
}
