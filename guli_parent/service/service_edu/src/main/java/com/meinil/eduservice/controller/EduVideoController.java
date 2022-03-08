package com.meinil.eduservice.controller;


import com.meinil.commonutils.R;
import com.meinil.commonutils.ResultCode;
import com.meinil.eduservice.client.VodClient;
import com.meinil.eduservice.entity.EduVideo;
import com.meinil.eduservice.service.EduVideoService;
import com.meinil.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-26
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private VodClient vodClient;

    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    @ApiOperation("删除小节")
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        // 根据小节id查询视频id实现删除
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            R r = vodClient.removeAliyunVideo(videoSourceId);
            if (r.getCode() == 20001) {
                throw new GuliException(ResultCode.ERROR, "删除视频失败");
            }
        }
        videoService.removeById(id);
        return R.ok();
    }

    @ApiOperation("修改小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }
}

