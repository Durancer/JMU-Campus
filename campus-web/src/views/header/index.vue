<template>
    <div>
        <header class="header">
            <div class="header_nav">
                <div>
                    <img class="logo-img" src="@/assets/img/logo.jpg" alt="">
                </div>
                <div>
                    <input type="text" placeholder="搜索" class='searcher'>
                </div>
                <div class="login">
                    <button type="button" class="btn" @click="handleBtn('loginBtn')">登录</button>
                    <div class="line"></div>
                    <button type="button" class="btn" @click="handleBtn('register')">注册</button>
                </div>
            </div>
        </header>

        <el-dialog v-model="loginFlag" title="登录" width="320px" center>
            <login @close="close('login')" />
        </el-dialog>

        <el-dialog v-model="registerFlag" title="注册" width="400px" center>
            <register @close="close('register')" />
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import type { FormInstance, FormRules } from 'element-plus'

import login from './cpn/login.vue'
import register from './cpn/register.vue'

const loginFormRef = ref<FormInstance>()
const registerFormRef = ref<FormInstance>()

const loginFlag = ref(false)
const registerFlag = ref(false)
const handleBtn = (str: string) => {
    if (str === 'loginBtn') {
        loginFormRef.value?.resetFields()
        loginFlag.value = true
    } else if (str === 'register') {
        registerFormRef.value?.resetFields()
        registerFlag.value = true
    }
}

const close = (str: string) => {
    if (str === 'register') {
        registerFlag.value = false
    } else if (str === 'login') {
        console.log('44');

        loginFlag.value = false
    }
}

</script>

<style scoped lang="less">
.header {
    .header_nav {
        max-width: 1122px;
        height: 100%;
        margin: auto;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .logo-img {
            padding: 10px;
            width: 40px;
        }

        .login {
            position: relative;
            color: #007fff;
            padding: 0;
            cursor: default;
            font-size: 14px;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            background: rgba(30, 128, 255, .05);
            border: 1px solid rgba(30, 128, 255, .3);
            border-radius: 4px;
            line-height: 26px;
            font-weight: 400;
            overflow: hidden;

            .btn {
                outline: none;
                border: none;
                padding: 3px 12px;
                color: #007fff;
                line-height: 1.9rem;
                font-size: 14px;
                font-weight: 400;
                height: 36px;
                overflow: hidden;
                display: flex;
                align-items: center;
                text-decoration: none;
                cursor: pointer;
            }

            .line {
                background-color: #abcdff;
                height: 12px;
                width: 1px;
            }
        }
    }
}
</style>