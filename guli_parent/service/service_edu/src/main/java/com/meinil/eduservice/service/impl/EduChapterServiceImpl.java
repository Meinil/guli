package com.meinil.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meinil.commonutils.ResultCode;
import com.meinil.eduservice.entity.EduChapter;
import com.meinil.eduservice.entity.EduVideo;
import com.meinil.eduservice.entity.chapter.ChapterVo;
import com.meinil.eduservice.entity.chapter.VideoVo;
import com.meinil.eduservice.mapper.EduChapterMapper;
import com.meinil.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meinil.eduservice.service.EduVideoService;
import com.meinil.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-02-26
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String id) {
        // 根据id查询所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", id);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapperChapter);

        // 查询所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", id);
        List<EduVideo> eduVideos = eduVideoService.list(wrapperVideo);

        List<ChapterVo> finalList = new ArrayList<>();
        // 遍历查询章节list集合进行封装
        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            finalList.add(chapterVo);

            // 遍历查询小节
            for (EduVideo eduVideo : eduVideos) {
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo video = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, video);
                    chapterVo.addVideo(video);
                }
            }
        }
        return finalList;
    }

    @Override
    public boolean deleteChapter(String id) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", id);
        int count = eduVideoService.count(wrapper);
        if (count > 0) {
            throw new GuliException(ResultCode.ERROR, "章节不为空");
        }
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    public void removeChapterByCourseId(Serializable id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        baseMapper.delete(wrapper);
    }
}
