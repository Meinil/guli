import request from "@/utils/request"

export default {
    /**
     * 登录
     */
    loginUser(member) {
        return request({
            url: "/educenter/member/login",
            method: "post",
            data: member
        })
    },
    /**
     * 根据token获取用户信息
     */
    getUserInfo() {
        return request({
            url: "/educenter/member/getMemberInfo",
            method: "get"
        })
    },     
}