package com.meinil.educenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Data
public class LoginVo {
    @ApiModelProperty(value = "邮箱")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;
}
