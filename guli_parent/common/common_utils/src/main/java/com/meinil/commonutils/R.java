package com.meinil.commonutils;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Data
public class R {
    @ApiModelProperty("是否成功")
    private Boolean success;
    @ApiModelProperty("返回码")
    private Integer code;
    @ApiModelProperty("返回消息")
    private String message;
    @ApiModelProperty("返回数据“")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> data;

    private R() {}

    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    /**
     * 设置是否成功
     */
    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    /**
     * 设置响应码
     */
    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 设置数据
     */
    public R data(String key, Object value) {
        if (data == null) {
            data = new HashMap<>();
        }
        this.data.put(key, value);
        return this;
    }
    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    /**
     * 设置响应消息
     */
    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
