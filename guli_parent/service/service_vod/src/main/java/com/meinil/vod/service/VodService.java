package com.meinil.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author Meinil
 * @Version 1.0
 */
public interface VodService {
    /**
     * 上传视频到aliyun
     */
    String uploadVideoAliyun(MultipartFile file);

    /**
     * 批量删除视频
     */
    void removeAliyunVideos(List<String> videoIdList);
}
