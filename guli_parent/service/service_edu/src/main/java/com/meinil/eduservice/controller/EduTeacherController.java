package com.meinil.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meinil.commonutils.R;
import com.meinil.eduservice.entity.EduTeacher;
import com.meinil.eduservice.entity.vo.TeacherQuery;
import com.meinil.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 讲师 前端控制器
 * @author Meinil
 * @since 2022-02-23
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation("所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        return R.ok().data("rows", eduTeacherService.list(null));
    }

    @ApiOperation("删除指定讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(
            @ApiParam(name="id", value="讲师id", required = true)
            @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        return flag ? R.ok() : R.error();
    }

    @ApiOperation("分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@ApiParam("当前页") @PathVariable Long current,
                             @ApiParam("每页记录数") @PathVariable Long limit) {
        Page<EduTeacher> teacherPage = new Page<>(current, limit);
        eduTeacherService.page(teacherPage, null);
        return R.ok()
                .data("total", teacherPage.getTotal())
                .data("rows", teacherPage.getRecords());
    }

    @ApiOperation("带分页条件查询")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@ApiParam("当前页") @PathVariable Long current,
                                  @ApiParam("每页记录数") @PathVariable Long limit,
                                  @ApiParam("查询条件,由前端封装") @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> teacherPage = new Page<>(current, limit);
        // 构建查询条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(teacherQuery.getName())) {
            wrapper.like("name", teacherQuery.getName());
        }
        if (!StringUtils.isEmpty(teacherQuery.getLevel())) {
            wrapper.eq("level", teacherQuery.getLevel());
        }
        if (!StringUtils.isEmpty(teacherQuery.getBegin())) {
            wrapper.ge("gmt_create", teacherQuery.getBegin());
        }
        if (!StringUtils.isEmpty(teacherQuery.getEnd())) {
            wrapper.le("gmt_modified", teacherQuery.getEnd());
        }

        // 排序
        wrapper.orderByDesc("gmt_create");
        eduTeacherService.page(teacherPage, wrapper);

        return R.ok()
                .data("total", teacherPage.getTotal())
                .data("rows", teacherPage.getRecords());
    }

    @ApiOperation("添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@ApiParam("教师实体") @RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        return save ? R.ok() : R.error();
    }

    @ApiOperation("根据讲师id查询")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        return R.ok().data("teacher", eduTeacherService.getById(id));
    }

    @ApiOperation("修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);
        return flag ? R.ok() : R.error();
    }
}

