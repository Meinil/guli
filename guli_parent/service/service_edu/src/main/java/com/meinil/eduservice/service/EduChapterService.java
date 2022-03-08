package com.meinil.eduservice.service;

import com.meinil.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meinil.eduservice.entity.chapter.ChapterVo;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-02-26
 */
public interface EduChapterService extends IService<EduChapter> {
    /**
     * 根据id查询课程章节
     */
    List<ChapterVo> getChapterVideoByCourseId(String id);

    /**
     * 根据章节id删除章节
     */
    boolean deleteChapter(String id);

    /**
     * 根据课程id删除章节
     */
    void removeChapterByCourseId(Serializable id);
}
