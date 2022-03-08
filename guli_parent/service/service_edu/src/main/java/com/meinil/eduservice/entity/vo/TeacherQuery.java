package com.meinil.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Data
public class TeacherQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("教师名称,模糊查询")
    private String name;
    @ApiModelProperty("头衔 1高级讲师 2首席讲师")
    private Integer level;
    @ApiModelProperty(value = "查询开始时间", example = "2022-02-23 10:10:22")
    private String begin;
    @ApiModelProperty(value = "查询结束时间", example = "2022-02-23 10:10:22")
    private String end;
}
