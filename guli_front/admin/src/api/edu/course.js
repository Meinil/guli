import request from '@/utils/request'

export default {
    /**
     * 获取所有的课程分类(树形结构)
     */
    addCourseInfo(courseInfo) {
        return request({
            url: "/eduservice/course/addCourseInfo",
            method: "post",
            data: courseInfo
        })
    },

    /**
     * 查询所有讲师
     */
    getListTeacher() {
        return request({
            url: "/eduservice/teacher/findAll",
            method: "get"
        })
    },

    /**
     * 根据课程id查询课程信息
     */
    getCourseInfoById(id) {
        return request({
            url: `/eduservice/course/getCourseInfo/${id}`,
            method: "get"
        })
    },

    /**
     * 修改课程信息
     */
    updateCourseInfo(course) {
        return request({
            url: "/eduservice/course/updateCourseInfo",
            method: "post",
            data: course
        })
    },

    /**
     * 查询发布课程信息
     */
    getPublishCourseInfo(id) {
        return request({
            url: `/eduservice/course/getPublishCourseInfo/${id}`,
            method: "get"
        })
    },

    /**
     * 发布课程
     */
    publishCourse(id) {
        return request({
            url: `/eduservice/course/publishCourse/${id}`,
            method: "get"
        })
    },

    /**
     * 查询所有课程
     */
    getCourseList(current, limit, courseQuery) {
        return request({
            url: `/eduservice/course/getCourseList/${current}/${limit}`,
            method: "post",
            data: courseQuery
        })
    },

    /**
     * 根据课程删除id
     */
    removeCourse(id) {
        return request({
            url: `/eduservice/course/${id}`,
            method: "delete"
        })
    }
}