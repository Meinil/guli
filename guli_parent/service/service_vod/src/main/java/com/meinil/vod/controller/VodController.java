package com.meinil.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.meinil.commonutils.R;
import com.meinil.commonutils.ResultCode;
import com.meinil.servicebase.exceptionhandler.GuliException;
import com.meinil.vod.service.VodService;
import com.meinil.vod.utils.ConstantVodUtils;
import com.meinil.vod.utils.InitVodClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author Meinil
 * @Version 1.0
 */
@RestController
@RequestMapping("/eduvod/video")
public class VodController {
    @Autowired
    private VodService vodService;

    @ApiOperation("上传视频到aliyun")
    @PostMapping("uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file) {
        return R.ok()
                .data("videoId", vodService.uploadVideoAliyun(file));
    }

    @ApiOperation("根据视频id删除视频")
    @DeleteMapping("removeAliyunVideo/{id}")
    public R removeAliyunVideo(@PathVariable String id) {
        DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(id);
        DeleteVideoResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(ResultCode.ERROR, e.getErrMsg());
        }
        return R.ok();
    }

    @ApiOperation("批量删除视频")
    @DeleteMapping("deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeAliyunVideos(videoIdList);
        return R.ok();
    }

    @ApiOperation("根据视频id获取视频播放凭证")
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        // 根据视频id获取视频播放凭证
        DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

        // 构建请求对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(id);

        // 获取响应
        GetVideoPlayAuthResponse response = null;
        try {
            response = client.getAcsResponse(request);
            return R.ok()
                    .data("auth", response.getPlayAuth());
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(ResultCode.ERROR, e.getMessage());
        }
    }
}
