package com.meinil.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.meinil.commonutils.ResultCode;
import com.meinil.servicebase.exceptionhandler.GuliException;
import com.meinil.vod.service.VodService;
import com.meinil.vod.utils.ConstantVodUtils;
import com.meinil.vod.utils.InitVodClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideoAliyun(MultipartFile file) {

        String videoId = null;
        try(InputStream in = file.getInputStream()) {
            // 上传的文件名
            String fileName = file.getOriginalFilename();
            // 上传的文件标题
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantVodUtils.ACCESS_KEY_ID,
                    ConstantVodUtils.ACCESS_KEY_SECRET,
                    title,
                    fileName,
                    in
            );

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            videoId = response.getVideoId();
            if (videoId == null) {
                throw new GuliException(ResultCode.ERROR, response.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoId;
    }

    @Override
    public void removeAliyunVideos(List<String> videoIdList) {
        DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
        DeleteVideoRequest request = new DeleteVideoRequest();

        // 拼接id
        StringBuilder builder = new StringBuilder();
        videoIdList.forEach(item -> {
            builder.append(item).append(",");
        });
        request.setVideoIds(builder.replace(builder.length() - 1, builder.length(), "").toString());

        DeleteVideoResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(ResultCode.ERROR, e.getErrMsg());
        }
    }
}
