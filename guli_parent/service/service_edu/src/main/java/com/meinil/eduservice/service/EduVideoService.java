package com.meinil.eduservice.service;

import com.meinil.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-02-26
 */
public interface EduVideoService extends IService<EduVideo> {
    /**
     * 根据课程id删除小节
     */
    void removeVideoByCourseId(Serializable id);
}
