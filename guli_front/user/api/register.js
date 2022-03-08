import request from "@/utils/request"

export default {
    /**
     * 注册
     */
     registerUser(member) {
        return request({
            url: "/educenter/member/register",
            method: "post",
            data: member
        })
    },
    /**
     * 发送验证码
     */
    sendCode(email) {
        return request({
            url: `/edumail/email/send/${email}`,
            method: "get"
        })
    }
}