package com.meinil.eduservice.entity.frontvo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Data
public class CourseFrontVo {
    @ApiModelProperty("课程名称")
    private String title;

    @ApiModelProperty("讲师id")
    private String teacherId;

    @ApiModelProperty("一级分类id")
    private String subjectParentId;

    @ApiModelProperty("二级分类id")
    private String subjectId;

    @ApiModelProperty("销量排序 1降序非1升序")
    private String buyCountSort;

    @ApiModelProperty("观看量排序 1降序非1升序")
    private String viewCountSort;

    @ApiModelProperty("最新时间排序 1降序非1升序")
    private String gmtCreateSort;

    @ApiModelProperty("价格排序 1降序非1升序")
    private String priceSort;
}
