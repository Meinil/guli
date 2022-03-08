package com.meinil.commonutils;

/**
 * @Author Meinil
 * @Version 1.0
 */
public interface ResultCode {
    /**
     * 成功响应码
     */
    public final static Integer SUCCESS = 20000;

    /**
     *失败响应码
     */
    public final static Integer ERROR = 20001;

    /**
     * 支付中
     */
    public final static Integer PAYING = 25000;
}
