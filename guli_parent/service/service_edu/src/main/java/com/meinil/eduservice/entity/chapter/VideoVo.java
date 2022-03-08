package com.meinil.eduservice.entity.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Data
public class VideoVo {
    @ApiModelProperty("小节id")
    private String id;
    @ApiModelProperty("小节标题")
    private String title;
    @ApiModelProperty("视频id")
    private String videoSourceId;
}
