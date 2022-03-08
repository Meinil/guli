import request from "@/utils/request"

export default {
    /**
     * 查询banner信息
     */
    getBannerList() {
        return request({
            url: "/educms/bannerfront/allBanner",
            method: "get"
        })
    }
}