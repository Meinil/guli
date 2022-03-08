package com.meinil.eduservice.controller;


import com.meinil.commonutils.R;
import com.meinil.eduservice.entity.EduChapter;
import com.meinil.eduservice.service.EduChapterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    @ApiOperation("根据课程id查询")
    @GetMapping("getChapterVideo/{id}")
    public R getChapterVideo(@ApiParam("课程id") @PathVariable String id) {
        return R.ok()
                .data("chapters", chapterService.getChapterVideoByCourseId(id));
    }

    @ApiOperation("添加章节")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter chapter) {
        chapterService.save(chapter);
        return R.ok();
    }

    @ApiOperation("根据章节id查询")
    @GetMapping("getChapterInfo/{id}")
    public R getChapterInfo(@PathVariable String id) {
        return R.ok()
                .data("chapter", chapterService.getById(id));
    }

    @ApiOperation("修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter chapter) {
        chapterService.updateById(chapter);
        return R.ok();
    }

    @ApiOperation("根据章节id删除章节")
    @DeleteMapping("{id}")
    public R deleteChapter(@PathVariable String id) {
        return chapterService.deleteChapter(id) ? R.ok().message("删除成功") : R.error().message("删除失败");
    }
}

