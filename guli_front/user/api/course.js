import request from "@/utils/request"

export default {
    /**
     * 查询课程信息
     */
    getCourseList(current, limit, condition) {
        return request({
            url: `/eduservice/coursefront/getCourseFrontList/${current}/${limit}`,
            method: "post",
            data: condition
        })
    },

    /**
     * 获取分类信息
     */
    getAllSubject() {
        return request({
            url: "/eduservice/subject/getAllSubject",
            method: "get"
        })
    },

    /**
     * 获取课程详情
     */
    getCourseInfo(id) {
        return request({
            url: `/eduservice/coursefront/getCourseFrontInfo/${id}`,
            method: "get"
        })
    }
}