package com.meinil.servicebase.exceptionhandler;

import com.meinil.commonutils.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuliException extends RuntimeException{
    /**
     * 异常状态码
     */
    private Integer code;
    /**
     * 异常信息
     */
    private String msg;
}
