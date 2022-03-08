package com.meinil.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meinil.commonutils.R;
import com.meinil.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meinil.eduservice.entity.frontvo.CourseFrontVo;
import com.meinil.eduservice.entity.vo.CourseInfoVo;
import com.meinil.eduservice.entity.vo.CoursePublishVo;
import com.meinil.eduservice.entity.vo.CourseQuery;
import com.meinil.eduservice.entity.frontvo.CourseWebVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-02-26
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 保存课程信息
     */
    String saveCourse(CourseInfoVo courseInfoVo);

    /**
     * 根据id查询课程信息
     */
    CourseInfoVo getCourseInfo(String id);

    /**
     * 更新课程信息
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 查询发布课程信息
     */
    CoursePublishVo publishCourseInfo(String id);

    /**
     * 根据条件查询课程
     */
    R conditionQuery(long current, long limit, CourseQuery courseQuery);

    /**
     * 根据课程id删除课程
     */
    void removeCourse(String id);

    /**
     * 前端查询课程
     */
    Map<String, Object> getCourseFrontList(Page<EduCourse> page, CourseFrontVo courseFrontVo);

    /**
     * 查询课程详情页信息
     */
    CourseWebVo getBaseCourseInfo(String id);
}
