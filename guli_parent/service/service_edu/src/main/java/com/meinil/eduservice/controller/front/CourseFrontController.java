package com.meinil.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meinil.commonutils.JwtUtils;
import com.meinil.commonutils.R;
import com.meinil.commonutils.ordervo.CourseWebOrder;
import com.meinil.eduservice.client.OrderFeignClient;
import com.meinil.eduservice.entity.EduCourse;
import com.meinil.eduservice.entity.frontvo.CourseFrontVo;
import com.meinil.eduservice.entity.frontvo.CourseWebVo;
import com.meinil.eduservice.service.EduChapterService;
import com.meinil.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Meinil
 * @Version 1.0
 */
@RestController
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderFeignClient orderClient;

    @ApiOperation("带条件分页查询课程")
    @PostMapping("getCourseFrontList/{current}/{limit}")
    public R getCourseFrontList(@PathVariable long current,
                                @PathVariable long limit,
                                @RequestBody(required = false)CourseFrontVo courseFrontVo) {
        Page<EduCourse> page = new Page<>(current, limit);
        return R.ok()
                .data(courseService.getCourseFrontList(page, courseFrontVo));
    }

    @ApiOperation("课程详情页")
    @GetMapping("getCourseFrontInfo/{id}")
    public R getCourseFrontInfo(@PathVariable String id, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        boolean buyCourse = false;
        if(!StringUtils.isEmpty(memberId)) {
            // 是否购买
            buyCourse = orderClient.isBuyCourse(id, memberId);
        }
        return R.ok()
                .data("isBuy", buyCourse)
                .data("courseInfo", courseService.getBaseCourseInfo(id))
                .data("chapters", chapterService.getChapterVideoByCourseId(id));
    }

    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("getCourseInfoOrder/{id}")
    public CourseWebOrder getCourseInfoOrder(@PathVariable String id) {
        CourseWebVo baseCourseInfo = courseService.getBaseCourseInfo(id);
        CourseWebOrder courseWebOrder = new CourseWebOrder();
        BeanUtils.copyProperties(baseCourseInfo, courseWebOrder);
        return courseWebOrder;
    }
}
