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

        <el-dialog v-model="loginFlag" width="20%" align-center>
            <h3>99</h3>
            <el-form :model="loginForm" :rules="loginRules">
                <el-form-item label="账户" prop="account">
                    <el-input v-model="loginForm.account" autocomplete="off" />
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="loginForm.account" autocomplete="off" />
                </el-form-item>
                <el-form-item style="border: none; background: none">
                    <el-button type="primary" style="width: 100%" @click="handleBtn('loginForm')">注册</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog v-model="registerFlag" width="20%" align-center>
            <el-form :model="registerForm" :rules="registerRules" label-width="auto" label-position="right">
                <el-form-item label="账户" prop="account">
                    <el-input v-model="registerForm.account" autocomplete="off" />
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="registerForm.account" autocomplete="off" />
                </el-form-item>
                <el-form-item label="用户名" prop="userName">
                    <el-input v-model="registerForm.userName" autocomplete="off" />
                </el-form-item>
                <el-form-item label="邮箱" prop="userEmail">
                    <el-input v-model="registerForm.userEmail" autocomplete="off" />
                </el-form-item>
                <el-form-item label="验证码" prop="code">
                    <el-input v-model="registerForm.code" autocomplete="off" />
                </el-form-item>
                <el-form-item style="border: none; background: none">
                    <el-button type="primary" style="width: 100%" @click="handleBtn('registerForm')">注册</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';

import type { FormInstance, FormRules } from 'element-plus'

interface APILoginForm {
    account: string
    password: string
}

interface APIResigerForm {
    account: string
    password: string
    userName: string
    userEmail: string
    code: string
}



const loginFormRef = ref<FormInstance>()

const loginForm = reactive<APILoginForm>({
    account: '',
    password: ''
})



const loginRules = reactive<FormRules<APILoginForm>>({
    account: [
        { required: true, message: 'Please input Activity name', trigger: 'blur' },
        { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
    ],
    password: [
        { required: true, message: 'Please input Activity name', trigger: 'blur' },
        { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
    ],
})

const registerForm = reactive<APIResigerForm>({
    account: '',
    password: '',
    userName: '',
    userEmail: '',
    code: ''

})
const registerRules = reactive<FormRules<APIResigerForm>>({
    account: [
        { required: true, message: 'Please input Activity name', trigger: 'blur' },
        { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
    ],
    password: [
        { required: true, message: 'Please input Activity name', trigger: 'blur' },
        { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
    ],
    userName: [
        { required: true, message: 'Please input Activity name', trigger: 'blur' },
        { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
    ],
    userEmail: [
        { required: true, message: 'Please input Activity name', trigger: 'blur' },
        { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
    ],
    code: [
        { required: true, message: 'Please input Activity name', trigger: 'blur' },
        { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
    ],
})

const loginFlag = ref(false)
const registerFlag = ref(false)
const handleBtn = (str: string) => {
    if (str === 'loginBtn') {
        loginFlag.value = true
    } else if (str === 'register') {
        registerFlag.value = true
    } else if (str === 'registerForm') {
        console.log(registerForm);
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
            // background-color: #abcdff;

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


.el-form-item {

    width: 270px;

}
</style>