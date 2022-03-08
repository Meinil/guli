<template>
    <div class="app-container">
        <h2 style="text-align: center">发布新课程</h2>
        <el-steps
            :active="1"
            process-status="wait"
            align-center
            style="margin-bottom: 40px"
        >
            <el-step title="填写课程基本信息" />
            <el-step title="创建课程大纲" />
            <el-step title="最终发布" />
        </el-steps>
        <el-form label-width="120px">
            <el-form-item label="课程标题">
                <el-input
                    v-model="courseInfo.title"
                    placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"
                />
            </el-form-item>

            <!-- 课程分类 -->
            <el-form-item label="课程分类">
                <el-select
                    v-model="courseInfo.subjectParentId"
                    placeholder="一级分类"
                    @change="subjectLevelOneChanged"
                >
                    <el-option
                        v-for="subject in subjectOneList"
                        :key="subject.id"
                        :label="subject.title"
                        :value="subject.id"
                    />
                </el-select>
                <el-select
                    v-model="courseInfo.subjectId"
                    placeholder="二级分类"
                >
                    <el-option
                        v-for="subject in subjectTwoList"
                        :key="subject.id"
                        :label="subject.title"
                        :value="subject.id"
                    />
                </el-select>
            </el-form-item>

            <!-- 课程讲师 -->
            <el-form-item label="课程讲师">
                <el-select v-model="courseInfo.teacherId" placeholder="请选择">
                    <el-option
                        v-for="teacher in teacherList"
                        :key="teacher.id"
                        :label="teacher.name"
                        :value="teacher.id"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="总课时">
                <el-input-number
                    :min="0"
                    v-model="courseInfo.lessonNum"
                    controls-position="right"
                    placeholder="请填写课程的总课时数"
                />
            </el-form-item>

            <!-- 课程简介 -->
            <el-form-item label="课程简介">
                <Tinymce :height="300" v-model="courseInfo.description" />
            </el-form-item>

            <!-- 课程封面-->
            <el-form-item label="课程封面" title="点击更换">
                <el-upload
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload"
                    :action="BASE_API + '/eduoss/fileoss'"
                    class="avatar-uploader"
                >
                    <img :src="courseInfo.cover" />
                </el-upload>
            </el-form-item>

            <!-- 课程价格 -->
            <el-form-item label="课程价格">
                <el-input-number
                    :min="0"
                    v-model="courseInfo.price"
                    controls-position="right"
                    placeholder="免费课程请设置为0元"
                />
                元
            </el-form-item>

            <el-form-item>
                <el-button
                    :disabled="saveBtnDisabled"
                    type="primary"
                    @click="saveOrUpdate"
                    >保存并下一步</el-button
                >
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
import course from "@/api/edu/course"
import subject from "@/api/edu/subject"
import Tinymce from "@/components/Tinymce"

export default {
    components: {
        Tinymce
    },
    data() {
        return {
            courseInfo: {
                id: "",
                title: "",
                subjectParentId: "",    // 一级分类
                subjectId: "",          // 二级分类
                teacherId: "",
                lessonNum: "",
                description: "",
                cover: "https://meinil-edu.oss-cn-shanghai.aliyuncs.com/2022/02/25/default-cover.png",
                price: 0
            },
            subjectOneList: [],     // 一级分类
            subjectTwoList: [],     // 二级分类
            teacherList: [],        // 所有讲师
            BASE_API: process.env.BASE_API,
            saveBtnDisabled: false
        };
    },
    created() {
        
        // 获取课程id
        if (this.$route.params && this.$route.params.id) {
			this.courseInfo.id = this.$route.params.id
            this.getInfo()
		} else {
            // 初始化一级分类
            this.getOneSubject()
        }
        // 初始化讲师
        this.getListTeacher()
    },
    methods: {
        // 根据课程id查询课程信息
        getInfo() {
            course.getCourseInfoById(this.courseInfo.id)
            .then(res => {
                this.courseInfo = res.data.course
                // 查询分类信息
                subject.getAllSubjects()
                .then(res => {
                    // 获取一级分类
                    this.subjectOneList = res.data.subjects;
                    for(let i = 0; i < this.subjectOneList.length; i++) {
                        if (this.subjectOneList[i].id == this.courseInfo.subjectParentId) {
                            this.subjectTwoList = this.subjectOneList[i].children;
                            break
                        }
                    }
                })
            })
        },

        // 封面上传成功
        handleAvatarSuccess(res, file) {
            this.courseInfo.cover = res.data.url
        },
        // 封面上传失败
        beforeAvatarUpload(file) {
            const isLt2M = file.size / 1024 / 1024 < 2

            if (!isLt2M) {
                this.$message.error("课程封面不能超过2MB")
            }
            return isLt2M
        },
        // 初始化讲师
        getListTeacher() {
            course.getListTeacher()
            .then(res => {
                this.teacherList = res.data.rows
            })
        },

        // 查询所有的一级分类
        getOneSubject() {
            subject.getAllSubjects()
            .then(res => {
                this.subjectOneList = res.data.subjects
            })
        },

        // 点击某个一级分类，触发change，显示对应的二级分类
        subjectLevelOneChanged(value) {
            for(let i = 0; i < this.subjectOneList.length; i++) {
                if (this.subjectOneList[i].id == value) {
                    this.subjectTwoList = this.subjectOneList[i].children;
                    this.courseInfo.subjectId = ""
                    break
                }
            }
        },

        // 添加课程
        addCourse() {
            course.addCourseInfo(this.courseInfo)
            .then(res => {
                this.$message({
                    type: "success",
                    message: "添加课程信息成功"
                })
                this.$router.push({ path: `/course/chapter/${res.data.id}` })
            })
        },

        // 修改课程
        updateCourse() {
            course.updateCourseInfo(this.courseInfo)
            .then(res => {
                this.$message({
                    type: "success",
                    message: "修改课程信息成功"
                })
                this.$router.push({ path: `/course/chapter/${this.courseInfo.id}` })
            })
        },
        saveOrUpdate() {
            if (this.courseInfo.id == "") {
                this.addCourse()
            } else {
                this.updateCourse()
            }
        },
    },
};
</script>
<style scoped>
    .tinymce-container {
        line-height: 29px;
    }
</style>