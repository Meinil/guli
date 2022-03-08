package com.meinil.commonutils.ordervo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Data
public class CourseWebOrder {
    @ApiModelProperty("课程id")
    private String id;

    @ApiModelProperty("课程标题")
    private String title;

    @ApiModelProperty("课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty("课程封面图片路径")
    private String cover;

    @ApiModelProperty("讲师姓名")
    private String teacherName;
}
