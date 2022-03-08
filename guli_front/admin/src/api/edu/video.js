import request from '@/utils/request'

export default {
    /**
     * 添加小节
     */
    addVideo(video) {
        return request({
            url: "/eduservice/video/addVideo",
            method: "post",
            data: video
        })
    },

    /**
     * 更新小节
     */
    updateVideo(video) {
        return request({
            url: "/eduservice/video/updateVideo",
            method: "post",
            data: video
        })
    },

    /**
     * 根据id删除小节
     */
    deleteVideo(id) {
        return request({
            url: `/eduservice/video/${id}`,
            method: "delete"
        })
    },

    /**
     * 删除视频
     */
    deleteAliyunVideo(id) {
        return request({
            url: `/eduvod/video/removeAliyunVideo/${id}`,
            method: "delete"
        })
    }
}