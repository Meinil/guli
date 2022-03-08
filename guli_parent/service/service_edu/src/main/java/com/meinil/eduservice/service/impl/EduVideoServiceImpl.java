package com.meinil.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meinil.eduservice.client.VodClient;
import com.meinil.eduservice.entity.EduVideo;
import com.meinil.eduservice.mapper.EduVideoMapper;
import com.meinil.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 * @author testjava
 * @since 2022-02-26
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void removeVideoByCourseId(Serializable id) {
        // 根据课程id查询所有的视频id
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", id);
        videoWrapper.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(videoWrapper);

        // 删除所有视频
        List<String> ids = new ArrayList<>();
        eduVideos.forEach(video -> {
            String videoSourceId = video.getVideoSourceId();
            if (videoSourceId != null) {
                ids.add(video.getVideoSourceId());
            }
        });
        if (ids.size() != 0) {
            vodClient.deleteBatch(ids);
        }

        // 删除所有小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        baseMapper.delete(wrapper);
    }
}
