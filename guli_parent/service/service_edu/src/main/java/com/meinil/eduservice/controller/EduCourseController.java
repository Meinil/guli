package com.meinil.eduservice.controller;


import com.meinil.commonutils.R;
import com.meinil.eduservice.entity.EduCourse;
import com.meinil.eduservice.entity.vo.CourseInfoVo;
import com.meinil.eduservice.entity.vo.CourseQuery;
import com.meinil.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-26
 */
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation("查询课程信息")
    @PostMapping("getCourseList/{current}/{limit}")
    public R getCourseList(@PathVariable long current,
                           @PathVariable long limit,
                           @RequestBody(required = false) CourseQuery courseQuery) {
        return eduCourseService.conditionQuery(current, limit, courseQuery);
    }

    @ApiOperation("添加课程基本信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        return R.ok()
                .data("id", eduCourseService.saveCourse(courseInfoVo));
    }

    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable String id) {
        return R.ok()
                .data("course", eduCourseService.getCourseInfo(id));
    }

    @ApiOperation("修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @ApiOperation("查询发布课程的信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        return R.ok()
                .data("course", eduCourseService.publishCourseInfo(id));
    }

    @ApiOperation("课程最终发布")
    @GetMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    @ApiOperation("课程删除")
    @DeleteMapping("{id}")
    public R deleteCourse(@PathVariable String id) {
        eduCourseService.removeCourse(id);
        return R.ok();
    }
}

