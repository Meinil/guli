package com.meinil.eduservice.entity.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Data
public class ChapterVo {
    @ApiModelProperty("章节id")
    private String id;
    @ApiModelProperty("章节标题")
    private String title;

    @ApiModelProperty("小节集合")
    private List<VideoVo> videos;

    public void addVideo(VideoVo video) {
        if (videos == null) {
            videos = new ArrayList<>();
        }
        videos.add(video);
    }
}
