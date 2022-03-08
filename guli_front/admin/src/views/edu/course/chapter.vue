<template>
    <div class="app-container">
        <h2 style="text-align: center">发布新课程</h2>

        <el-steps
            :active="2"
            process-status="wait"
            align-center
            style="margin-bottom: 40px"
        >
            <el-step title="填写课程基本信息" />
            <el-step title="创建课程大纲" />
            <el-step title="最终发布" />
        </el-steps>

        <ul class="chanpterList">
            <li v-for="chapter in chapterVideoList" :key="chapter.id">
                <p>
                    {{ chapter.title }}
                    <span class="acts">
                        <el-button type="text" @click="openVideo(chapter.id)"
                            >添加小节</el-button
                        >
                        <el-button
                            type="text"
                            @click="openEditChapter(chapter.id)"
                            >编辑</el-button
                        >
                        <el-button
                            type="text"
                            @click="removeChapter(chapter.id)"
                            >删除</el-button
                        >
                    </span>
                </p>
                <ul class="chanpterList videoList">
                    <li v-for="video in chapter.videos" :key="video.id">
                        <p>
                            {{ video.title }}
                            <span class="acts">
                                <el-button
                                    type="text"
                                    @click="openEditVideo(video.id)"
                                    >编辑</el-button
                                >
                                <el-button
                                    type="text"
                                    @click="removeVideo(video.id)"
                                    >删除</el-button
                                >
                            </span>
                        </p>
                    </li>
                </ul>
            </li>
        </ul>

        <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
            <el-form :model="video" label-width="120px">
                <el-form-item label="课时标题">
                    <el-input v-model="video.title" />
                </el-form-item>
                <el-form-item label="课时排序">
                    <el-input-number
                        v-model="video.sort"
                        :min="0"
                        controls-position="right"
                    />
                </el-form-item>
                <el-form-item label="是否免费">
                    <el-radio-group v-model="video.free">
                        <el-radio :label="true">免费</el-radio>
                        <el-radio :label="false">默认</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="上传视频">
                    <el-upload
                        :on-success="handleVodUploadSuccess"
                        :on-remove="handleVodRemove"
                        :before-remove="beforeVodRemove"
                        :on-exceed="handleUploadExceed"
                        :file-list="fileList"
                        :action="BASE_API + '/eduvod/video/uploadAliyunVideo'"
                        :limit="1"
                        class="upload-demo"
                    >
                        <el-button size="small" type="primary"
                            >上传视频</el-button
                        >
                        <el-tooltip placement="right-end">
                            <div slot="content">
                                最大支持1G，<br />
                                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br />
                                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br />
                                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br />
                                SWF、TS、VOB、WMV、WEBM 等视频格式上传
                            </div>
                            <i class="el-icon-question" />
                        </el-tooltip>
                    </el-upload>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVideoFormVisible = false"
                    >取 消</el-button
                >
                <el-button
                    :disabled="saveVideoBtnDisabled"
                    type="primary"
                    @click="saveOrUpdateVideo"
                    >确 定</el-button
                >
            </div>
        </el-dialog>

        <el-button type="primary" plain @click="openChapterDialog"
            >添加课程章节</el-button
        >
        <!-- 添加和修改章节表单 -->
        <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
            <el-form :model="chapter" label-width="120px">
                <el-form-item label="章节标题">
                    <el-input v-model="chapter.title" />
                </el-form-item>
                <el-form-item label="章节排序">
                    <el-input-number
                        v-model="chapter.sort"
                        :min="0"
                        controls-position="right"
                    />
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogChapterFormVisible = false"
                    >取 消</el-button
                >
                <el-button type="primary" @click="saveOrUpdate"
                    >确 定</el-button
                >
            </div>
        </el-dialog>

        <el-form label-width="120px">
            <el-form-item>
                <el-button @click="previous">上一步</el-button>
                <el-button
                    :disabled="saveBtnDisabled"
                    type="primary"
                    @click="next"
                    >下一步</el-button
                >
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
import chapter from "@/api/edu/chapter";
import video from "@/api/edu/video";
export default {
    data() {
        return {
            dialogChapterFormVisible: false, // 章节弹窗是否显示
            chapter: {
                courseId: "",
                title: "",
                sort: 0,
            },
            chapterVideoList: [],
            saveBtnDisabled: false,

            fileList: [],                  // 上传的视频列表
            BASE_API: process.env.BASE_API,

            dialogVideoFormVisible: false, // 小节弹窗是否显示
            video: {
                title: "",
                sort: 0,
                free: 0,
                videoSourceId: "",
            },
            saveVideoBtnDisabled: false,

            courseId: "", // 课程id
        };
    },
    created() {
        if (this.$route.params && this.$route.params.id) {
            this.courseId = this.$route.params.id;
            this.getChapter(this.courseId);
        }
    },
    methods: {
        // ==============================小节操作================================
        // 上传视频成功
        handleVodUploadSuccess(response, file, fileList) {
            this.video.videoSourceId = response.data.videoId
            this.video.videoOriginalName = file.name
        },
        // 确认移除视频
        handleVodRemove() {
            video.deleteAliyunVideo(this.video.videoSourceId)
            .then(res => {
                this.$message({
                    type: "success",
                    message: "删除视频成功"
                })
                this.fileList = []
                this.video.videoSourceId = ""
                this.video.videoOriginalName = ""
            })
        },
        // 移除视频之前
        beforeVodRemove(file, fileList) {
            return this.$confirm(`确定移除${file.name}`)
        },
        handleUploadExceed() {
            this.$message.warning("请先移除已上传的视频")
        },
        // 添加小节弹窗
        openVideo(id) {
            this.dialogVideoFormVisible = true;

            // 设置课程id
            this.video.courseId = this.courseId;
            // 设置章节id
            this.video.chapterId = id;
            this.video.title = "";
            this.fileList = []
            this.video.sort = 0;
            this.video.free = 0;
            this.video.videoSourceId = "";
            this.video.videoOriginalName = ""
        },
        // 添加小节
        addVideo() {
            video.addVideo(this.video).then((res) => {
                this.dialogVideoFormVisible = false;
                this.$message({
                    type: "success",
                    message: "添加成功",
                });
                this.getChapter();
            });
        },
        // 更新小节
        openEditVideo(id) {},
        // 删除小节
        removeVideo(id) {
            this.$confirm("此操作将会删除小节,是否继续", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(() => {
                video.deleteVideo(id).then((res) => {
                    this.$message({
                        type: "success",
                        message: "删除小节成功",
                    });
                    // 刷新页面
                    this.getChapter();
                });
            });
        },
        saveOrUpdateVideo() {
            this.addVideo();
        },

        // ==============================章节操作================================
        // 打开弹窗
        openChapterDialog() {
            this.chapter = {};
            this.chapter.sort = 0;
            this.dialogChapterFormVisible = true;
        },
        // 根据课程id查询章节和小节
        getChapter() {
            chapter.getAllChapterVideo(this.courseId).then((res) => {
                this.chapterVideoList = res.data.chapters;
            });
        },
        previous() {
            this.$router.push({ path: `/course/info/${this.courseId}` });
        },
        next() {
            this.$router.push({ path: `/course/publish/${this.courseId}` });
        },
        // 删除章节
        removeChapter(id) {
            this.$confirm("此操作将会删除章节,是否继续", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(() => {
                chapter.deleteChapter(id).then((res) => {
                    this.$message({
                        type: "success",
                        message: "删除章节成功",
                    });
                    // 刷新页面
                    this.getChapter();
                });
            });
        },
        // 添加章节
        addChapter() {
            this.chapter.courseId = this.courseId;
            chapter.addChapter(this.chapter).then((res) => {
                this.dialogChapterFormVisible = false; // 关闭弹窗
                this.$message({
                    type: "success",
                    message: "添加课程信息成功",
                });
                // 刷新页面
                this.getChapter();
            });
        },
        // 更新章节
        updateChapter() {
            this.chapter.courseId = this.courseId;
            chapter.updateChapter(this.chapter).then((res) => {
                this.dialogChapterFormVisible = false; // 关闭弹窗
                this.$message({
                    type: "success",
                    message: "修改课程信息成功",
                });
                this.chapter = {};
                // 刷新页面
                this.getChapter();
            });
        },
        // 更新章节弹窗
        openEditChapter(id) {
            chapter.getChapter(id).then((res) => {
                // 弹窗修改
                this.chapter = res.data.chapter;
                this.dialogChapterFormVisible = true;
            });
        },
        // 保存课程章节信息
        saveOrUpdate() {
            if (!this.chapter.id) {
                this.addChapter();
            } else {
                this.updateChapter();
            }
        },
    },
};
</script>

<style scoped>
.chanpterList {
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
}
.chanpterList li {
    position: relative;
}
.chanpterList p {
    float: left;
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #ddd;
}
.chanpterList .acts {
    float: right;
    font-size: 14px;
}

.videoList {
    padding-left: 50px;
}
.videoList p {
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #ddd;
}
</style>