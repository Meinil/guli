package com.meinil.educenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meinil.commonutils.JwtUtils;
import com.meinil.commonutils.R;
import com.meinil.commonutils.ResultCode;
import com.meinil.commonutils.ordervo.UcenterMemberOrder;
import com.meinil.educenter.entity.UcenterMember;
import com.meinil.educenter.entity.vo.LoginVo;
import com.meinil.educenter.service.UcenterMemberService;
import com.meinil.educenter.entity.vo.RegisterVo;
import com.meinil.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 * @author Meinil
 * @since 2022-03-03
 */
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation("登录")
    @PostMapping("login")
    public R loginUser(@RequestBody LoginVo loginVo) {
        return R.ok()
                .message("登录成功")
                .data("token", memberService.login(loginVo));
    }

    @ApiOperation("注册")
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok()
                .message("注册成功");
    }

    @ApiOperation("根据token获取用户信息")
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            throw new GuliException(ResultCode.ERROR, "请登录后重试");
        }

        return R.ok()
                .data("userInfo", memberService.getUserInfo(memberId));
    }

    @ApiOperation("根据token获取用户信息")
    @GetMapping("getMemberInfo/{token}")
    public R getMemberInfo(@PathVariable String token) {
        String memberId = JwtUtils.getMemberIdByJwtToken(token);
        if (StringUtils.isEmpty(memberId)) {
            throw new GuliException(ResultCode.ERROR, "请登录后重试");
        }

        return R.ok()
                .data("userInfo", memberService.getUserInfo(memberId));
    }

    @ApiOperation("根据用户id获取用户信息")
    @PostMapping("getUserInfo/{id}")
    public UcenterMemberOrder getUserInfo(@PathVariable String id) {
        UcenterMember ucenterMember = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(ucenterMember, ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    @ApiOperation("查询某一天的注册人数")
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day) {
        return R.ok()
                .data("count", memberService.countRegister(day));
    }
}

