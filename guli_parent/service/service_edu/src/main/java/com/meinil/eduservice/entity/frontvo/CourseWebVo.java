package com.meinil.eduservice.entity.frontvo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Data
public class CourseWebVo {
    @ApiModelProperty("课程id")
    private String id;

    @ApiModelProperty("课程标题")
    private String title;

    @ApiModelProperty("课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty("总课时")
    private Integer lessonNum;

    @ApiModelProperty("课程封面图片路径")
    private String cover;

    @ApiModelProperty("销售数量")
    private Long buyCount;

    @ApiModelProperty("浏览数量")
    private Long viewCount;

    @ApiModelProperty("课程简介")
    private String description;

    @ApiModelProperty("讲师id")
    private String teacherId;

    @ApiModelProperty("讲师姓名")
    private String teacherName;

    @ApiModelProperty("讲师资历")
    private String intro;

    @ApiModelProperty("讲师头像")
    private String avatar;

    @ApiModelProperty("一级分类id")
    private String subjectLevelOneId;

    @ApiModelProperty("一级分类名称")
    private String subjectLevelOne;

    @ApiModelProperty("二级分类id")
    private String subjectLevelTwoId;

    @ApiModelProperty("二级分类名称")
    private String subjectLevelTwo;
}
