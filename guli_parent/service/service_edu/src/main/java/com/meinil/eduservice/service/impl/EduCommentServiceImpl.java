package com.meinil.eduservice.service.impl;

import com.meinil.eduservice.entity.EduComment;
import com.meinil.eduservice.mapper.EduCommentMapper;
import com.meinil.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-05
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Override
    public void addComment(EduComment comment, Map<String, Object> data) {
        data = (Map)data.get("userInfo");
        String memberId = (String)data.get("id");
        String nickname = (String) data.get("nickname");
        String avatar = (String) data.get("avatar");

        comment.setAvatar(avatar);
        comment.setNickname(nickname);
        comment.setMemberId(memberId);
        baseMapper.insert(comment);
    }
}
