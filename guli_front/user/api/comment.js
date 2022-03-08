import request from "@/utils/request"

export default {
    /**
     * 查询banner信息
     */
    getCommentList(id, current, limit) {
        return request({
            url: `/eduservice/comment/getCommentPageList/${id}/${current}/${limit}`,
            method: "get"
        })
    },
    addComment(comment) {
        return request({
            url: "/eduservice/comment/addComment",
            method: "post",
            data: comment
        })
    }
}