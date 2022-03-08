import request from '@/utils/request'

export default {
    /**
     * 获取所有的课程分类(树形结构)
     */
    getAllSubjects() {
        return request({
            url: "/eduservice/subject/getAllSubject",
            method: "get"
        })
    }
}