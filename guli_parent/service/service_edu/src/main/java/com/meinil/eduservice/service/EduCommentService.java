package com.meinil.eduservice.service;

import com.meinil.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-05
 */
public interface EduCommentService extends IService<EduComment> {
    /**
     * 添加评论
     */
    void addComment(EduComment comment, Map<String, Object> data);
}
