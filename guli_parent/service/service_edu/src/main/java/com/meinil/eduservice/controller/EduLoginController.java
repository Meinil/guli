package com.meinil.eduservice.controller;

import com.meinil.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Meinil
 * @Version 1.0
 */
@RestController
@RequestMapping(("/eduservice/user"))
public class EduLoginController {

    @ApiOperation("登录")
    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @ApiOperation("用户信息")
    @GetMapping("info")
    public R info() {
        return R.ok()
                .data("roles", "[admin]")
                .data("name", "admin")
                .data("avatar", "https://meinil-edu.oss-cn-shanghai.aliyuncs.com/2022/02/25/41cad560bbf04c88a32124d1f3e9e7aefile.png");
    }
}
