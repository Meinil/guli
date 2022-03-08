<template>
    <div class="app-container">
        讲师添加
        <el-form label-width="120px">
            <el-form-item label="讲师名称">
                <el-input v-model="teacher.name" />
            </el-form-item>
            <el-form-item label="讲师排序">
                <el-input-number
                    v-model="teacher.sort"
                    controls-position="right"
                    min="0"
                />
            </el-form-item>
            <el-form-item label="讲师头衔">
                <el-select
                    v-model="teacher.level"
                    clearable
                    placeholder="请选择"
                >
                    <el-option :value="1" label="高级讲师" />
                    <el-option :value="2" label="首席讲师" />
                </el-select>
            </el-form-item>
            <el-form-item label="讲师资历">
                <el-input v-model="teacher.career" />
            </el-form-item>
            <el-form-item label="讲师简介">
                <el-input v-model="teacher.intro" :rows="10" type="textarea" />
            </el-form-item>

            <!-- 讲师头像 -->
            <el-form-item label="讲师头像">
                <!-- 头衔缩略图 -->
                <pan-thumb :image="teacher.avatar" />
                <!-- 文件上传按钮 -->
                <el-button
                    type="primary"
                    icon="el-icon-upload"
                    @click="imagecropperShow = true"
                    >更换头像
                </el-button>

                <!--
                    v-show：是否显示上传组件
                    :key：类似于id，如果一个页面多个图片上传控件，可以做区分
                    :url：后台上传的url地址
                    @close：关闭上传组件
                    @crop-upload-success：上传成功后的回调 
                        <input type="file" name="file"/>
                -->
                <image-cropper
                    v-show="imagecropperShow"
                    :width="200"
                    :height="200"
                    :key="imagecropperKey"
                    :url="BASE_API + '/eduoss/fileoss'"
                    field="file"
                    @close="close()"
                    @crop-upload-success="cropSuccess"
                />
            </el-form-item>

            <el-form-item>
                <el-button
                    :disabled="saveBtnDisabled"
                    type="primary"
                    @click="saveOrUpdate()"
                    >保存</el-button
                >
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
import teacherApi from "@/api/edu/teacher"
import ImageCropper from "@/components/ImageCropper"
import PanThumb from "@/components/PanThumb"

export default {
    components: {
        ImageCropper,
        PanThumb
    },
    data() {
        return {
            teacher: {
                name: "",
                sort: 0,
                level: 1,
                career: "",
                intro: "",
                avatar: "",
            },
            // 上传弹窗的组件是否显示
            imagecropperShow: false,
            // 上传组件的唯一标识
            imagecropperKey: 0,
            // 头像上传地址
            BASE_API: process.env.BASE_API,
            // 保存按钮是否禁用
            saveBtnDisabled: false,
        };
    },
    created() {
        this.init();
    },
    watch: {
        $route(to, from) {
            this.init();
        },
    },
    methods: {
        // 关闭弹窗的方法
        close() {
            this.imagecropperShow = false
            this.imagecropperKey++
        },
        // 上传成功的方法
        cropSuccess(data) {
            this.imagecropperShow = false
            console.log(data)
            this.teacher.avatar = data.url
        },
        init() {
            if (this.$route.params && this.$route.params.id) {
                this.getInfo(this.$route.params.id);
            } else {
                this.teacher = {};
            }
        },
        getInfo(id) {
            teacherApi.getTeacherById(id).then((res) => {
                this.teacher = res.data.teacher;
            });
        },
        saveOrUpdate() {
            // 判断是修改还是添加
            if (this.teacher.id) {
                this.updateTeacher();
            } else {
                this.saveTeacher();
            }
        },
        // 修改讲师
        updateTeacher() {
            teacherApi.updateTeacherInfo(this.teacher).then((res) => {
                this.$message({
                    type: "success",
                    message: "添加成功",
                });
                // 切换页面
                this.$router.push({
                    path: "/teacher/table",
                });
            });
        },
        // 添加讲师
        saveTeacher() {
            teacherApi.addTeacher(this.teacher).then((res) => {
                this.$message({
                    type: "success",
                    message: "添加成功",
                });
                // 切换页面
                this.$router.push({
                    path: "/teacher/table",
                });
            });
        },
    },
};
</script>