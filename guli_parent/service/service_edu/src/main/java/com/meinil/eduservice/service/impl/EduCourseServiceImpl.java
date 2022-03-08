package com.meinil.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meinil.commonutils.R;
import com.meinil.commonutils.ResultCode;
import com.meinil.eduservice.entity.EduCourse;
import com.meinil.eduservice.entity.EduCourseDescription;
import com.meinil.eduservice.entity.frontvo.CourseFrontVo;
import com.meinil.eduservice.entity.vo.CourseInfoVo;
import com.meinil.eduservice.entity.vo.CoursePublishVo;
import com.meinil.eduservice.entity.vo.CourseQuery;
import com.meinil.eduservice.entity.frontvo.CourseWebVo;
import com.meinil.eduservice.mapper.EduCourseMapper;
import com.meinil.eduservice.service.EduChapterService;
import com.meinil.eduservice.service.EduCourseDescriptionService;
import com.meinil.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meinil.eduservice.service.EduVideoService;
import com.meinil.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-02-26
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    /**
     * 课程描述
     */
    @Autowired
    private EduCourseDescriptionService descriptionService;

    /**
     * 课程小节
     */
    @Autowired
    private EduVideoService videoService;

    /**
     * 课程章节
     */
    @Autowired
    private EduChapterService chapterService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveCourse(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        // 插入课程
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0) {
            throw new GuliException(ResultCode.ERROR, "添加课程失败");
        }

        // 插入课程描述信息
        EduCourseDescription description = new EduCourseDescription();
        description.setDescription(courseInfoVo.getDescription());
        description.setId(eduCourse.getId());
        descriptionService.save(description);

        return eduCourse.getId();
    }

    /**
     * 根据课程id查询课程详细信息
     */
    @Override
    public CourseInfoVo getCourseInfo(String id) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        // 查询课程表
        EduCourse eduCourse = baseMapper.selectById(id);
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        // 查询描述表
        EduCourseDescription description = descriptionService.getById(id);
        courseInfoVo.setDescription(description.getDescription());
        return courseInfoVo;
    }

    @Override
    @Transactional(rollbackFor = GuliException.class)
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if (i <= 0) {
            throw new GuliException(ResultCode.ERROR, "修改失败");
        }

        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        boolean b = descriptionService.updateById(description);
        if (!b) {
            throw new GuliException(ResultCode.ERROR, "修改失败");
        }
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        return baseMapper.getPublishCourseInfo(id);
    }

    @Override
    public R conditionQuery(long current, long limit, CourseQuery courseQuery) {
        Page<EduCourse> page = new Page<>(current, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQuery.getTitle())) {
            wrapper.like("title", courseQuery.getTitle());
        }
        if (!StringUtils.isEmpty(courseQuery.getStatus())) {
            wrapper.eq("status", courseQuery.getStatus());
        }
        if (!StringUtils.isEmpty(courseQuery.getBegin())) {
            wrapper.ge("gmt_modified", courseQuery.getBegin());
        }
        if (!StringUtils.isEmpty(courseQuery.getEnd())) {
            wrapper.le("gmt_modified", courseQuery.getEnd());
        }
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(page, wrapper);
        return R.ok()
                .data("courses", page.getRecords())
                .data("total", page.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeCourse(String id) {
        // 根据课程id删除小节
        videoService.removeVideoByCourseId(id);

        // 根据课程id删除章节
        chapterService.removeChapterByCourseId(id);

        // 根据课程id删除描述
        descriptionService.removeById(id);

        // 根据课程id删除课程本身
        int i = baseMapper.deleteById(id);
        if (i <= 0) {
            throw new GuliException(ResultCode.ERROR, "删除失败");
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> page, CourseFrontVo courseFrontVo) {
        Map<String, Object> data = new HashMap<>();

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        // 一级分类
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }

        // 二级分类
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }

        // 关注度
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            String buyCountSort = courseFrontVo.getBuyCountSort();
            if ("1".equals(buyCountSort)) {
                wrapper.orderByDesc("view_count");
            } else {
                wrapper.orderByAsc("view_count");
            }
        }

        // 时间
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            String gmtCreateSort = courseFrontVo.getGmtCreateSort();
            if ("1".equals(gmtCreateSort)) {
                wrapper.orderByDesc("gmt_create");
            } else {
                wrapper.orderByAsc("gmt_create");
            }
        }

        // 价格
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            String priceSort = courseFrontVo.getPriceSort();
            if ("1".equals(priceSort)) {
                wrapper.orderByDesc("price");
            } else {
                wrapper.orderByAsc("price");
            }
        }

        baseMapper.selectPage(page, wrapper);
        data.put("items", page.getRecords());
        data.put("current", page.getCurrent());
        data.put("pages", page.getPages());
        data.put("size", page.getSize());
        data.put("hasNext", page.hasNext());
        data.put("hasPrevious", page.hasPrevious());
        data.put("total", page.getTotal());
        return data;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String id) {
        return baseMapper.getBaseCourseInfo(id);
    }
}
