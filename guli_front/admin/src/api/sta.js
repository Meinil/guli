import request from '@/utils/request'

export default {
    /**
     * 生成某一天的注册人数
     * @param {*} day 
     */
    createStaData(day) {
        return request({
            url: `/staservice/statistics/registerCount/${day}`,
            method: 'post'
        })
    },

    /**
     * 获取图表数据
     * @param {*} type 类型
     * @param {*} begin 开始时间
     * @param {*} end 结束时间
     * @returns 
     */
    getShowData({type, begin, end}) {
        return request({
            url: `/staservice/statistics/showData/${type}/${begin}/${end}`,
            method: 'get'
        })
    }
}
