import request from "@/utils/request"

export default {
    /**
     * 查询banner信息
     */
    getPlayerAuth(id) {
        return request({
            url: `/eduvod/video/getPlayAuth/${id}`,
            method: "get"
        })
    }
}