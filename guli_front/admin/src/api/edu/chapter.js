import request from '@/utils/request'

export default {
    
    /**
     * 根据课程id获取章节和小节的信息
     */
    getAllChapterVideo(id) {
        return request({
            url: `/eduservice/chapter/getChapterVideo/${id}`,
            method: "get"
        })
    },

    /**
     * 添加章节
     */
    addChapter(chapter) {
        return request({
            url: "/eduservice/chapter/addChapter",
            method: "post",
            data: chapter
        })
    },

    /**
     * 更新章节
     */
    updateChapter(chapter) {
        return request({
            url: "/eduservice/chapter/updateChapter",
            method: "post",
            data: chapter
        })
    },

    /**
     * 根据id查询章节信息
     */
    getChapter(id) {
        return request({
            url: `/eduservice/chapter/getChapterInfo/${id}`,
            method: "get"
        })
    },

    /**
     * 根据id删除章节
     */
    deleteChapter(id) {
        return request({
            url: `/eduservice/chapter/${id}`,
            method: "delete"
        })
    }
}