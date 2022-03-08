<template>
    <div class="main">
        <div class="title">
            <a class="active" href="/login">登录</a>
            <span>·</span>
            <a href="/register">注册</a>
        </div>

        <div class="sign-up-container">
            <el-form ref="userForm" :model="user">
                <el-form-item
                    class="input-prepend restyle"
                    prop="mobile"
                    :rules="[
                        {
                            required: true,
                            message: '请输入邮箱',
                            trigger: 'blur',
                        },
                        { validator: checkPhone, trigger: 'blur' },
                    ]"
                >
                    <div>
                        <el-input
                            type="text"
                            placeholder="邮箱"
                            v-model="user.mobile"
                        />
                        <i class="iconfont icon-phone" />
                    </div>
                </el-form-item>

                <el-form-item
                    class="input-prepend"
                    prop="password"
                    :rules="[
                        {
                            required: true,
                            message: '请输入密码',
                            trigger: 'blur',
                        },
                    ]"
                >
                    <div>
                        <el-input
                            type="password"
                            placeholder="密码"
                            v-model="user.password"
                        />
                        <i class="iconfont icon-password-icon" />
                    </div>
                </el-form-item>

                <div class="btn">
                    <input
                        type="button"
                        class="sign-in-button"
                        value="登录"
                        @click="submitLogin()"
                    />
                </div>
            </el-form>
            <!-- 更多登录方式 -->
            <div class="more-sign">
                <h6>社交帐号登录</h6>
                <ul>
                    <li>
                        <a
                            id="weixin"
                            class="weixin"
                            target="_blank"
                            href="http://qy.free.idcfengye.com/api/ucenter/weixinLogin/login"
                            ><i class="iconfont icon-weichat"
                        /></a>
                    </li>
                    <li>
                        <a id="qq" class="qq" target="_blank" href="#"
                            ><i class="iconfont icon-qq"
                        /></a>
                    </li>
                    <li>
                        <a id="github" class="github" target="_blank" :href="BASE_URL+'/educenter/member/github/login'"
                            ><i class="iconfont icon-github"
                        /></a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
import "~/assets/css/sign.css";
import "~/assets/css/iconfont.css";

import cookie from "js-cookie"

import loginApi from "@/api/login"
import {BASE_URL} from "@/utils/constant"

export default {
    layout: "sign",

    data() {
        return {
            // 登录所需信息
            user: {
                mobile: "",
                password: "",
            },
            BASE_URL: BASE_URL,
            // 登录过后的用户信息
            loginInfo: {},
        };
    },
    created() {
    },

    methods: {
        submitLogin() {
            loginApi.loginUser(this.user)
            .then(res => {
                // 请求token
                let token = res.data.token
                console.log(token)
                cookie.set("guli_token", token, {domain: "localhost"})
                return loginApi.getUserInfo(token)
            })
            .then(res => {
                // 根据token请求用户信息
                this.loginInfo = res.data.userInfo
                cookie.set("guli_ucenter", JSON.stringify(this.loginInfo), {domain: "localhost"})
                
                window.location.href = "/"
            })
        },
        // 检验邮箱
        checkPhone(rule, value, callback) {
            //debugger
            if (!/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(value)) {
                return callback(new Error("邮箱格式不正确"));
            }
            return callback();
        },
    },
};
</script>
<style>
.el-form-item__error {
    z-index: 9999999;
}
</style>