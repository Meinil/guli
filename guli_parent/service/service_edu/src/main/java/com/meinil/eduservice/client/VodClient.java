package com.meinil.eduservice.client;

import com.meinil.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author Meinil
 * @Version 1.0
 */

@FeignClient(name = "service-vod", fallback = VodFeignClient.class)
@Component
public interface VodClient {
    /**
     * '@PathVariable'必须指定名称
     */
    @ApiOperation("根据视频id删除视频")
    @DeleteMapping("/eduvod/video/removeAliyunVideo/{id}")
    public R removeAliyunVideo(@PathVariable("id") String id);

    @ApiOperation("批量删除视频")
    @DeleteMapping("/eduvod/video/deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
