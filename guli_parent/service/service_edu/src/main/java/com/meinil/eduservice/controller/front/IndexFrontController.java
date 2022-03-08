package com.meinil.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meinil.commonutils.R;
import com.meinil.eduservice.entity.EduCourse;
import com.meinil.eduservice.entity.EduTeacher;
import com.meinil.eduservice.service.EduCourseService;
import com.meinil.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Meinil
 * @Version 1.0
 */
@RestController
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation("查询前8条热门课程和前四个名师")
    @GetMapping("index")
    public R index() {
        // 前8课程
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("view_count");
        courseWrapper.last("limit 8");

        // 前四讲师
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("sort");
        teacherWrapper.last("limit 4");
        return R.ok()
                .data("courses", courseService.list(courseWrapper))
                .data("teachers", teacherService.list(teacherWrapper));
    }
}
