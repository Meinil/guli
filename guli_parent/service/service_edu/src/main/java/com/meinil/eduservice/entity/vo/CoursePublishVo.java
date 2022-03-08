package com.meinil.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Data
public class CoursePublishVo {
    @ApiModelProperty("课程id")
    private String id;
    @ApiModelProperty("课程名称")
    private String title;
    @ApiModelProperty("课程价格")
    private double price;
    @ApiModelProperty("课时数")
    private int lessonNum;
    @ApiModelProperty("课程封面")
    private String cover;
    @ApiModelProperty("课程简介")
    private String description;
    @ApiModelProperty("课程讲师")
    private String teacher;
    @ApiModelProperty("一级分类")
    private String oneSubject;
    @ApiModelProperty("二级分类")
    private String twoSubject;
}
