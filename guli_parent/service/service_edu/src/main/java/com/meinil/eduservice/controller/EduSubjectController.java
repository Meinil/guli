package com.meinil.eduservice.controller;


import com.meinil.commonutils.R;
import com.meinil.eduservice.service.EduSubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-25
 */
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    @ApiOperation("添加课程分类")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file);
        return R.ok();
    }

    @ApiOperation("课程树形结构")
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        return R.ok()
                .data("subjects", eduSubjectService.getAllSubject());
    }
}

