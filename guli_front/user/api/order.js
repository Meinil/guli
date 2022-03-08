import request from "@/utils/request"

export default {
    /**
     * 生成订单
     * @param {*} id 课程id
     */
    createOrder(id) {
        return request({
            url: `/eduorder/order/createOrder/${id}`,
            method: "post"
        })
    },

    /**
     * 获取订单信息
     * @param {*} id 订单id
     */
    getOrderInfo(id) {
        return request({
            url: `/eduorder/order/getOrderInfo/${id}`,
            method: "get"
        })
    },

    /**
     * 获取支付订单的二维码
     * @param {*} id 订单号
     * @returns 
     */
    createNative(id) {
        return request({
            url: `/eduorder/paylog/createNative/${id}`,
            method: "get"
        })
    },
    queryPayStatus(id) {
        return request({
            url: `/eduorder/paylog/queryPayStatus/${id}`,
            method: "get"
        })
    },
}