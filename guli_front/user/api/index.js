import request from "@/utils/request"

export default {
    /**
     * 查询主页信息
     */
    getIndexData() {
        return request({
            url: "/eduservice/indexfront/index",
            method: "get"
        })
    }
}