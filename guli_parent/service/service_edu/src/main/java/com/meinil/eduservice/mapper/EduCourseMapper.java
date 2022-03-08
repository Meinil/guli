package com.meinil.eduservice.mapper;

import com.meinil.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meinil.eduservice.entity.vo.CoursePublishVo;
import com.meinil.eduservice.entity.frontvo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-02-26
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    /**
     * 获取最终发布课程的信息
     */
    public CoursePublishVo getPublishCourseInfo(String id);

    /**
     * 根据课程id查询课程信息
     */
    CourseWebVo getBaseCourseInfo(String id);
}
