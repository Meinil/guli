<template>
    <div id="aCoursesList" class="bg-fa of">
        <!-- /课程详情 开始 -->
        <section class="container">
            <section class="path-wrap txtOf hLh30">
                <a href="/" title class="c-999 fsize14">首页</a>
                \
                <a href="#" title class="c-999 fsize14">{{
                    courseInfo.subjectLevelOne
                }}</a>
                \
                <span class="c-333 fsize14">{{
                    courseInfo.subjectLevelTwo
                }}</span>
            </section>
            <div>
                <article class="c-v-pic-wrap" style="height: 357px">
                    <section class="p-h-video-box" id="videoPlay">
                        <img
                            :src="courseInfo.cover"
                            :alt="courseInfo.title"
                            class="dis c-v-pic"
                        />
                    </section>
                </article>
                <aside class="c-attr-wrap">
                    <section class="ml20 mr15">
                        <h2 class="hLh30 txtOf mt15">
                            <span class="c-fff fsize24">{{
                                courseInfo.title
                            }}</span>
                        </h2>
                        <section class="c-attr-jg">
                            <span class="c-fff">价格：</span>
                            <b class="c-yellow" style="font-size: 24px"
                                >￥{{ courseInfo.price }}</b
                            >
                        </section>
                        <section class="c-attr-mt c-attr-undis">
                            <span class="c-fff fsize14">主讲：{{courseInfo.teacherName}}&nbsp;&nbsp;&nbsp;</span>
                        </section>
                        <section class="c-attr-mt of">
                            <span class="ml10 vam">
                                <em class="icon18 scIcon"></em>
                                <a class="c-fff vam" title="收藏" href="#"
                                    >收藏</a
                                >
                            </span>
                        </section>
                        <section class="c-attr-mt" v-if="courseInfo.price == 0 || isBuy">
                            <a
                                @click="createOrders"
                                href="#"
                                title="立即观看"
                                class="comm-btn c-btn-3"
                                >立即观看</a
                            >
                        </section>
                        <section class="c-attr-mt" v-else>
                            <a
                                @click="createOrders"
                                href="#"
                                title="立即购买"
                                class="comm-btn c-btn-3"
                                >立即购买</a
                            >
                        </section>
                    </section>
                </aside>
                <aside class="thr-attr-box">
                    <ol class="thr-attr-ol clearfix">
                        <li>
                            <p>&nbsp;</p>
                            <aside>
                                <span class="c-fff f-fM">购买数</span>
                                <br />
                                <h6 class="c-fff f-fM mt10">
                                    {{ courseInfo.buyCount }}
                                </h6>
                            </aside>
                        </li>
                        <li>
                            <p>&nbsp;</p>
                            <aside>
                                <span class="c-fff f-fM">课时数</span>
                                <br />
                                <h6 class="c-fff f-fM mt10">
                                    {{ courseInfo.lessonNum }}
                                </h6>
                            </aside>
                        </li>
                        <li>
                            <p>&nbsp;</p>
                            <aside>
                                <span class="c-fff f-fM">浏览数</span>
                                <br />
                                <h6 class="c-fff f-fM mt10">
                                    {{ courseInfo.viewCount }}
                                </h6>
                            </aside>
                        </li>
                    </ol>
                </aside>
                <div class="clear"></div>
            </div>
            <!-- /课程封面介绍 -->
            <div class="mt20 c-infor-box">
                <article class="fl col-7">
                    <section class="mr30">
                        <div class="i-box">
                            <div>
                                <section
                                    id="c-i-tabTitle"
                                    class="c-infor-tabTitle c-tab-title"
                                >
                                    <a
                                        name="c-i"
                                        class="current"
                                        title="课程详情"
                                        >课程详情</a
                                    >
                                </section>
                            </div>
                            <article class="ml10 mr10 pt20">
                                <div>
                                    <h6 class="c-i-content c-infor-title">
                                        <span>课程介绍</span>
                                    </h6>
                                    <div class="course-txt-body-wrap">
                                        <section class="course-txt-body">
                                            <p
                                                v-html="courseInfo.description"
                                            ></p>
                                        </section>
                                    </div>
                                </div>
                                <!-- /课程介绍 -->
                                <div class="mt50">
                                    <h6 class="c-g-content c-infor-title">
                                        <span>课程大纲</span>
                                    </h6>
                                    <section class="mt20">
                                        <div class="lh-menu-wrap">
                                            <menu
                                                id="lh-menu"
                                                class="lh-menu mt10 mr10"
                                            >
                                                <ul>
                                                    <!-- 文件目录 -->
                                                    <li
                                                        class="lh-menu-stair"
                                                        v-for="chapter in chapters"
                                                        :key="chapter.id"
                                                    >
                                                        <a
                                                            href="javascript: void(0)"
                                                            :title="
                                                                chapter.title
                                                            "
                                                            class="current-1"
                                                        >
                                                            <em
                                                                class="
                                                                    lh-menu-i-1
                                                                    icon18
                                                                    mr10
                                                                "
                                                            ></em
                                                            >{{ chapter.title }}
                                                        </a>
                                                        <ol
                                                            class="lh-menu-ol"
                                                            style="
                                                                display: block;
                                                            "
                                                        >
                                                            <li
                                                                class="
                                                                    lh-menu-second
                                                                    ml30
                                                                "
                                                                v-for="video in chapter.videos"
                                                                :key="video.id"
                                                            >
                                                                <a
                                                                    :href="
                                                                        '/player/' +
                                                                        video.videoSourceId
                                                                    "
                                                                    title
                                                                    target="_blank"
                                                                >
                                                                    <span
                                                                        class="
                                                                            fr
                                                                        "
                                                                    >
                                                                        <i
                                                                            class="
                                                                                free-icon
                                                                                vam
                                                                                mr10
                                                                            "
                                                                            >免费试听</i
                                                                        >
                                                                    </span>
                                                                    <em
                                                                        class="
                                                                            lh-menu-i-2
                                                                            icon16
                                                                            mr5
                                                                        "
                                                                        >&nbsp;</em
                                                                    >{{
                                                                        video.title
                                                                    }}
                                                                </a>
                                                            </li>
                                                        </ol>
                                                    </li>
                                                </ul>
                                            </menu>
                                        </div>
                                    </section>
                                </div>
                                <!-- /课程大纲 -->
                            </article>
                        </div>
                    </section>
                </article>
                <aside class="fl col-3">
                    <div class="i-box">
                        <div>
                            <section class="c-infor-tabTitle c-tab-title">
                                <a title href="javascript:void(0)">主讲讲师</a>
                            </section>
                            <section class="stud-act-list">
                                <ul style="height: auto">
                                    <li>
                                        <div class="u-face">
                                            <a href="#">
                                                <img
                                                    :src="courseInfo.avatar"
                                                    width="50"
                                                    height="50"
                                                    alt
                                                />
                                            </a>
                                        </div>
                                        <section class="hLh30 txtOf">
                                            <a
                                                class="c-333 fsize16 fl"
                                                href="#"
                                                >{{ courseInfo.teacherName }}</a
                                            >
                                        </section>
                                        <section class="hLh20 txtOf">
                                            <span class="c-999">{{
                                                courseInfo.intro
                                            }}</span>
                                        </section>
                                    </li>
                                </ul>
                            </section>
                        </div>
                    </div>
                </aside>
                <div class="clear"></div>
            </div>
            <div v-if="userInfo.id">
                <el-avatar :size="50" :src="userInfo.avatar" />
                    <el-input
                        type="textarea"
                        :rows="2"
                        placeholder="请输入内容"
                        v-model="textarea"
                        style="width: 400px"
                    >
                    </el-input>
                <el-button type="primary" @click="submitComment">发表评论</el-button>
            </div>
            <div v-if="!userInfo.id">
                <h2>请登录后评论</h2>
            </div>
            <ul>
                <li style="width: 600px; text-align: center" v-for="comment in comments" :key="comment.id">
                    <el-avatar :size="50" :src="comment.avatar" />
                    <span>
                        {{comment.content}}
                    </span>
                </li>
            </ul>
            <el-pagination
                background
                :total="total"
                :current-page="page"
                :page-size="limit"
                @current-change="getList"
                style="padding: 30px 0; text-align: center"
                layout="total, prev, pager, next, jumper"
            ></el-pagination>
        </section>
        <!-- /课程详情 结束 -->
    </div>
</template>

<script>
import cookie from "js-cookie";

import courseApi from "@/api/course";
import commentApi from "@/api/comment"
import orderApi from "@/api/order"

export default {
    data() {
        return {
            // 是否购买
            isBuy: false,
            // 课程信息
            courseInfo: {},
            // 章节
            chapters: [],
            // 课程id
            courseId: "",
            // 用户信息
            userInfo: {},
            // 用户评论
            comments: [],
            total: 0,
            page: 0,
            limit: 10,
            textarea: "",
            // 订单id
            orderId: ""
        }
    },

    created() {
        this.courseId = this.$route.params.id
        this.initCourseInfo()
        this.initUser()
    },

    mounted() {
        this.getList()
    },

    methods: {
        // 初始化课程详情
        initCourseInfo() {
            courseApi.getCourseInfo(this.courseId)
            .then((res) => {
                this.courseInfo = res.data.courseInfo
                this.chapters =  res.data.chapters
                this.isBuy = res.data.isBuy
            })
        },
        // 创建订单
        createOrders() {
            orderApi.createOrder(this.courseId)
            .then(res => {
                this.orderId = res.data.orderId;
                // 生成订单后跳转订单显示页面
                this.$router.push({
                    path: `/orders/${this.orderId}`
                })
            })
        },
        initUser() {
            let guli_ucenter = cookie.get("guli_ucenter")
            if (guli_ucenter) {
                this.userInfo = JSON.parse(guli_ucenter)
            }
        },
        // 分页查询评论
        getList(page = 1) {
            this.page = page
            commentApi.getCommentList(this.courseInfo.id, this.page, this.limit)
            .then(res => {
                this.comments = res.data.comments
                this.total = res.data.total
            })
        },
        // 发布评论
        submitComment() {
            let comment = {
                courseId: this.courseInfo.id,
                content: this.textarea,
                teacherId: this.courseInfo.teacherId
            }
            console.log(comment)
            commentApi.addComment(comment)
            .then(res => {
                this.getList()
                this.$message({
                    type: "success",
                    message: "发布评论成功"
                })
                this.textarea = ""
            })
        }
    }
};
</script>
