package com.meinil.eduservice.client;

import com.meinil.commonutils.R;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Component
public class UcenterFeignClient implements UcenterClient{
    @Override
    public R getMemberInfo(String token) {
        return R.error()
                .message("远程调用失败");
    }

    @Override
    public R getMemberInfo(HttpServletRequest request) {
        return R.error()
                .message("远程调用失败");
    }
}
