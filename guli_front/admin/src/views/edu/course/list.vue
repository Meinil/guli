<template>
    <div class="app-container">
        课程列表
        <el-form :inline="true" class="demo-form-inline">
            <el-form-item>
                <el-input v-model="courseQuery.name" placeholder="课程名称" />
            </el-form-item>
            
            <el-form-item>
                <el-select
                    v-model="courseQuery.status"
                    clearable
                    placeholder="课程状态"
                >
                    <el-option value="Normal" label="已发布" />
                    <el-option value="Draft" label="未发布" />
                </el-select>
            </el-form-item>

            <el-form-item label="修改时间">
                <el-date-picker
                    v-model="courseQuery.begin"
                    type="datetime"
                    placeholder="选择开始时间"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    default-time="00:00:00"
                />
            </el-form-item>
            <el-form-item>
                <el-date-picker
                    v-model="courseQuery.end"
                    type="datetime"
                    placeholder="选择截止时间"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    default-time="00:00:00"
                />
            </el-form-item>

            <el-button type="primary" icon="el-icon-search" @click="getList()"
                >查询</el-button
            >
            <el-button type="default" @click="resetData()">清空</el-button>
        </el-form>

        <el-table
            :data="list"
            style="width: 100%"
            border
            fit
            highlight-current-row
        >
            <el-table-column label="序号" width="70" align="center">
                <template slot-scope="scope">
                    {{ (page - 1) * limit + scope.$index + 1 }}
                </template>
            </el-table-column>

            <el-table-column prop="title" label="课程名称" width="150" />

            <el-table-column label="课程状态" width="80">
                <template slot-scope="scope">
                    {{ scope.row.status === "Draft" ? "未发布" : "已发布" }}
                </template>
            </el-table-column>

            <el-table-column prop="lessonNum" label="课时数" width="80"/>

            <el-table-column prop="gmtCreate" label="添加时间" width="160" />

            <el-table-column prop="viewCount" label="浏览数量" width="60" />

            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <router-link :to="'/teacher/edit/' + scope.row.id">
                        <el-button
                            type="primary"
                            size="mini"
                            icon="el-icon-edit"
                            >编辑课程基本信息</el-button
                        >
                    </router-link>
                    <router-link :to="'/teacher/edit/' + scope.row.id">
                        <el-button
                            type="primary"
                            size="mini"
                            icon="el-icon-edit"
                            >编辑课程大纲</el-button
                        >
                    </router-link>
                    <el-button
                        type="danger"
                        size="mini"
                        icon="el-icon-delete"
                        @click="removeDataById(scope.row.id)"
                        >删除</el-button
                    >
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination
            background
            :total="total"
            :current-page="page"
            :page-size="limit"
            @current-change="getList"
            style="padding: 30px 0; text-align: center"
            layout="total, prev, pager, next, jumper"
        ></el-pagination>
    </div>
</template>

<script>
import course from "@/api/edu/course";
export default {
    data() {
        return {
            page: 1, // 当前页
            limit: 10, // 每页记录数
            courseQuery: {}, // 查询条件
            total: 0, // 总记录数
            list: null, // 查询的数据集合
        };
    },
    created() {
        this.getList();
    },
    methods: {
        // 查询讲师
        getList(page = 1) {
            this.page = page;
            course.getCourseList(this.page, this.limit, this.courseQuery)
            .then((res) => {
                this.list = res.data.courses;
                this.total = res.data.total;
            })
        },
        resetData() {
            this.teacherQuery = {};
            this.getList();
        },
        removeDataById(id) {
            // 弹窗提示
            this.$confirm("此操作将永久删除课程, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
            .then(() => {
                course
                .removeCourse(id)
                .then((res) => {
                    this.$message({
                        type: "success",
                        message: "删除成功!",
                    });
                    // 重新请求数据
                    this.getList();
                })
            })
        },
    },
};
</script>

<style>
</style>