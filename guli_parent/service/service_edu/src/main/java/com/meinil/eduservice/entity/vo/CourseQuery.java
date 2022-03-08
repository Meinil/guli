package com.meinil.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Data
public class CourseQuery {
    @ApiModelProperty("课程标题")
    private String title;
    @ApiModelProperty(value = "课程状态", example = "Normal 已发布, Draft 未发布")
    private String status;
    @ApiModelProperty(value = "查询开始时间", example = "2022-02-23 10:10:22")
    private String begin;
    @ApiModelProperty(value = "查询结束时间", example = "2022-02-23 10:10:22")
    private String end;
}
