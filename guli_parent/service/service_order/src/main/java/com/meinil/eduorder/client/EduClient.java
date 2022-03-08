package com.meinil.eduorder.client;

import com.meinil.commonutils.ordervo.CourseWebOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Component
@FeignClient(name = "server-edu")
public interface EduClient {
    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    public CourseWebOrder getCourseInfoOrder(@PathVariable("id") String id);
}
