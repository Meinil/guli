package com.meinil.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meinil.commonutils.R;
import com.meinil.eduservice.entity.EduCourse;
import com.meinil.eduservice.entity.EduTeacher;
import com.meinil.eduservice.service.EduCourseService;
import com.meinil.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Meinil
 * @Version 1.0
 */
@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @ApiOperation("分页查询讲师")
    @GetMapping("getTeacherFrontList/{current}/{limit}")
    public R getTeacherFrontList(@PathVariable long current,
                                 @PathVariable long limit) {
        Page<EduTeacher> page = new Page<>(current, limit);
        return R.ok()
                .data(teacherService.getTeacherFrontList(page));
    }

    @ApiOperation("讲师详情页")
    @GetMapping("getTeacherFrontInfo/{id}")
    public R getTeacherFrontInfo(@PathVariable String id) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        return R.ok()
                .data("teacher", teacherService.getById(id))
                .data("courses", courseService.list(wrapper));
    }
}
