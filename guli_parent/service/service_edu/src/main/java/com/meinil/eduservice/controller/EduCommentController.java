package com.meinil.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meinil.commonutils.JwtUtils;
import com.meinil.commonutils.R;
import com.meinil.commonutils.ResultCode;
import com.meinil.eduservice.client.UcenterClient;
import com.meinil.eduservice.entity.EduComment;
import com.meinil.eduservice.service.EduCommentService;
import com.meinil.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-05
 */
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController {
    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private EduCommentService commentService;

    @ApiOperation("分页查询评论")
    @GetMapping("getCommentPageList/{id}/{current}/{limit}")
    public R getCommentPageList(@PathVariable String id,
                                @PathVariable long current,
                                @PathVariable long limit) {
        Page<EduComment> page = new Page<>(current, limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        commentService.page(page, wrapper);
        return R.ok()
                .data("comments", page.getRecords())
                .data("total", page.getTotal());
    }

    @ApiOperation("添加评论")
    @PostMapping("addComment")
    public R addComment(@RequestBody EduComment comment, HttpServletRequest request) {
        R r = ucenterClient.getMemberInfo(request.getHeader("Authorization"));
        if (r.getCode().equals(ResultCode.ERROR)) {
            throw new GuliException(ResultCode.ERROR, r.getMessage());
        }

        commentService.addComment(comment, r.getData());
        return R.ok();
    }
}

