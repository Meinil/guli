<template>
    <div id="aCoursesList" class="bg-fa of">
        <!-- /课程列表 开始 -->
        <section class="container">
            <header class="comm-title">
                <h2 class="fl tac">
                    <span class="c-333">全部课程</span>
                </h2>
            </header>
            <section class="c-sort-box">
                <section class="c-s-dl">
                    <dl>
                        <dt>
                            <span class="c-999 fsize14">课程类别</span>
                        </dt>
                        <dd class="c-s-dl-li">
                            <ul class="clearfix">
                                <li>
                                    <a title="全部" href="#" @click="allCourse">全部</a>
                                </li>
                                <li
                                    v-for="(item, index) in subjectParent"
                                    :key="index"
                                    @click="searchOne(item.id, index)"
                                    :class="{ active: oneIndex == index }"
                                >
                                    <a :title="item.title" href="#">{{
                                        item.title
                                    }}</a>
                                </li>
                            </ul>
                        </dd>
                    </dl>
                    <dl>
                        <dt>
                            <span class="c-999 fsize14"></span>
                        </dt>
                        <dd class="c-s-dl-li">
                            <ul class="clearfix">
                                <li
                                    v-for="(item, index) in subject"
                                    :key="index"
                                    @click="searchTwo(item.id, index)"
                                    :class="{ active: twoIndex == index }"
                                >
                                    <a :title="item.title" href="#">{{
                                        item.title
                                    }}</a>
                                </li>
                            </ul>
                        </dd>
                    </dl>
                    <div class="clear"></div>
                </section>
                <div class="js-wrap">
                    <section class="fr">
                        <span class="c-ccc">
                            <i class="c-master f-fM">1</i>/
                            <i class="c-666 f-fM">1</i>
                        </span>
                    </section>
                    <section class="fl">
                        <ol class="js-tap clearfix">
                            <li
                                :class="{
                                    'current bg-orange': buyCountSort != '',
                                }"
                            >
                                <a
                                    title="销量"
                                    href="javascript:void(0);"
                                    @click="searchBuyCount"
                                    >销量
                                    <span :class="{ hide: buyCountSort == '' }"
                                        >{{buyCountSort == "1" ? "↓" : "↑"}}</span
                                    >
                                </a>
                            </li>
                            <li
                                :class="{
                                    'current bg-orange': gmtCreateSort != '',
                                }"
                            >
                                <a
                                    title="最新"
                                    href="javascript:void(0);"
                                    @click="searchGmtCreate"
                                    >最新
                                    <span :class="{ hide: gmtCreateSort == '' }"
                                        >{{gmtCreateSort == "1" ? "↓" : "↑"}}</span
                                    >
                                </a>
                            </li>
                            <li
                                :class="{
                                    'current bg-orange': priceSort != '',
                                }"
                            >
                                <a
                                    title="价格"
                                    href="javascript:void(0);"
                                    @click="searchPrice"
                                    >价格&nbsp;
                                    <span :class="{ hide: priceSort == '' }"
                                        >{{priceSort == "1" ? "↓" : "↑"}}</span
                                    >
                                </a>
                            </li>
                        </ol>
                    </section>
                </div>
                <div class="mt40">
                    <!-- /无数据提示 开始-->
                    <section class="no-data-wrap" v-if="data.total == 0">
                        <em class="icon30 no-data-ico">&nbsp;</em>
                        <span class="c-666 fsize14 ml10 vam"
                            >没有相关数据，小编正在努力整理中...</span
                        >
                    </section>
                    <!-- /无数据提示 结束-->
                    <article class="comm-course-list" v-if="data.total >= 0">
                        <ul class="of" id="bna">
                            <li v-for="item in data.items" :key="item.id">
                                <div class="cc-l-wrap">
                                    <section class="course-img">
                                        <img
                                            :src="item.cover"
                                            class="img-responsive"
                                            :alt="item.title"
                                        />
                                        <div class="cc-mask">
                                            <a
                                                :href="'/course/' + item.id"
                                                title="开始学习"
                                                class="comm-btn c-btn-1"
                                                >开始学习</a
                                            >
                                        </div>
                                    </section>
                                    <h3 class="hLh30 txtOf mt10">
                                        <a
                                            :href="'/course/' + item.id"
                                            :title="item.title"
                                            class="course-title fsize18 c-333"
                                            >{{ item.title }}</a
                                        >
                                    </h3>
                                    <section class="mt10 hLh20 of">
                                        <span class="fr jgTag bg-green">
                                            <i class="c-fff fsize12 f-fA">{{
                                                item.price == 0
                                                    ? "免费"
                                                    : item.price + "￥"
                                            }}</i>
                                        </span>
                                        <span class="fl jgAttr c-ccc f-fA">
                                            <i class="c-999 f-fA">{{
                                                item.buyCount
                                            }}</i>
                                            |
                                            <i class="c-999 f-fA">9634评论</i>
                                        </span>
                                    </section>
                                </div>
                            </li>
                        </ul>
                        <div class="clear"></div>
                    </article>
                </div>
                <!-- 公共分页 开始 -->
                <div>
                    <div class="paging">
                        <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
                        <a
                            :class="{ undisable: !data.hasPrevious }"
                            href="#"
                            title="首页"
                            @click.prevent="gotoPage(1)"
                            >首页</a
                        >

                        <a
                            :class="{ undisable: !data.hasPrevious }"
                            href="#"
                            title="前一页"
                            @click.prevent="gotoPage(data.current - 1)"
                            >&lt;</a
                        >

                        <a
                            v-for="page in data.pages"
                            :key="page"
                            :class="{
                                current: data.current == page,
                                undisable: data.current == page,
                            }"
                            :title="'第' + page + '页'"
                            href="#"
                            @click.prevent="gotoPage(page)"
                            >{{ page }}</a
                        >

                        <a
                            :class="{ undisable: !data.hasNext }"
                            href="#"
                            title="后一页"
                            @click.prevent="gotoPage(data.current + 1)"
                            >&gt;</a
                        >

                        <a
                            :class="{ undisable: !data.hasNext }"
                            href="#"
                            title="末页"
                            @click.prevent="gotoPage(data.pages)"
                            >末页</a
                        >

                        <div class="clear" />
                    </div>
                </div>
                <!-- 公共分页 结束 -->
            </section>
        </section>
        <!-- /课程列表 结束 -->
    </div>
</template>
<script>
import courseApi from "@/api/course";
export default {
    data() {
        return {
            page: 1,
            data: {},
            subjectParent: [], // 一级分类
            subject: [], // 二级分类
            condition: {}, // 查询条件
            oneIndex: -1,
            twoIndex: -1,
            buyCountSort: "",
            gmtCreateSort: "",
            priceSort: "",
        };
    },
    created() {
        this.initCourseFirst();
        this.initSubject();
    },
    methods: {
        initCourseFirst() {
            courseApi.getCourseList(1, 8, this.condition).then((res) => {
                this.data = res.data;
            });
        },
        // 查询所有分类
        initSubject() {
            courseApi.getAllSubject().then((res) => {
                this.subjectParent = res.data.subjects;
            });
        },

        // 分页
        gotoPage(page) {
            if (page > this.data.pages) {
                return;
            }
            courseApi.getCourseList(page, 8, this.condition).then((res) => {
                this.data = res.data;
            });
        },
        // 一级分类查询
        searchOne(id, index) {
            // 激活样式
            this.oneIndex = index;

            // 构建查询条件
            this.condition.subjectParentId = id;
            this.condition.subjectId = "";
            this.gotoPage(1);
            for (let i = 0; i < this.subjectParent.length; i++) {
                let oneSubject = this.subjectParent[i];
                if (id == oneSubject.id) {
                    this.subject = oneSubject.children;
                }
            }
        },
        // 二级分类查询
        searchTwo(id, index) {
            console.log(1);
            // 激活样式
            this.twoIndex = index;
            this.condition.courseApisubjectParentId = "";
            this.condition.subjectId = id;
            this.gotoPage(1);
        },
        // 关注度排序
        searchBuyCount() {
            if (this.buyCountSort == "1") {
                this.buyCountSort = "0"
            } else {
                this.buyCountSort = "1"
            }
            this.gmtCreateSort = ""
            this.priceSort = ""

            this.condition.buyCountSort = this.buyCountSort
            this.condition.gmtCreateSort = this.gmtCreateSort
            this.condition.priceSort = this.priceSort
            this.gotoPage(1)
        },
        // 时间排序
        searchGmtCreate() {
            this.buyCountSort = ""
            if (this.gmtCreateSort == "1") {
                this.gmtCreateSort = "0"
            } else {
                this.gmtCreateSort = "1"
            }
            this.priceSort = ""

            this.condition.buyCountSort = this.buyCountSort
            this.condition.gmtCreateSort = this.gmtCreateSort
            this.condition.priceSort = this.priceSort
            this.gotoPage(1)
        },
        // 价格排序
        searchPrice() {
            this.buyCountSort = ""
            this.gmtCreateSort = ""
            if (this.priceSort == "1") {
                this.priceSort = "0"
            } else {
                this.priceSort = "1"
            }


            this.condition.buyCountSort = this.buyCountSort
            this.condition.gmtCreateSort = this.gmtCreateSort
            this.condition.priceSort = this.priceSort
            this.gotoPage(1)
        },
        allCourse() {
            this.page = 1
            this.data = 1
            this.subject = []
            this.condition = {}
            this.oneIndex = -1
            this.twoIndex = -1
            this.buyCountSort = ""
            this.gmtCreateSort = ""
            this.priceSort = ""
            this.gotoPage(1)
        }
    },
};
</script>
<style scoped>
.active {
    background-color: #68cb9b;
}
.hide {
    display: none;
}
.show {
    display: block;
}
</style>