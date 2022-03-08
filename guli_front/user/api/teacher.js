import request from "@/utils/request"

export default {
    /**
     * 分页查询讲师的方法
     */
    getTeacherList(current, limit) {
        return request({
            url: `/eduservice/teacherfront/getTeacherFrontList/${current}/${limit}`,
            method: "get"
        })
    },
    getTeacherFrontInfo(id) {
        return request({
            url: `/eduservice/teacherfront/getTeacherFrontInfo/${id}`,
            method: "get"
        })
    }
}