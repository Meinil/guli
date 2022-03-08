import request from '@/utils/request'

export default {
    /**
     * 讲师列表(带条件查询)
     */
    getTeacherList(current, limit, teacherQuery) {
        return request({
            url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
            method: "post",
            data: teacherQuery
        })
    },

    /**
     * 通过id删除讲师
     */
    removeTeacherById(id) {
        return request({
            url: `/eduservice/teacher/${id}`,
            method: "delete"
        })
    },

    /**
     * 添加讲师
     */
    addTeacher(teacher) {
        return request({
            url: "/eduservice/teacher/addTeacher",
            method: "post",
            data: teacher
        })
    },

    /**
     * 根据id获取讲师
     */
    getTeacherById(id) {
        return request({
            url: `/eduservice/teacher/getTeacher/${id}`,
            method: "get"
        })
    },

    /**
     * 修改讲师
     */
    updateTeacherInfo(teacher) {
        return request({
            url: "/eduservice/teacher/updateTeacher",
            method: "post",
            data: teacher
        })
    }
}
