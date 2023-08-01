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
            <el-form ref="loginFormRef" :model="loginForm" :rules="loginRules" label-width="auto" label-position="right">
                <el-form-item label="账户" prop="username">
                    <el-input v-model="loginForm.username" autocomplete="off" />
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="loginForm.password" autocomplete="off" />
                </el-form-item>
                <div style="border: none; background: none; display: flex; justify-content: center; ">
                    <el-form-item style="border: none; background: none">
                        <el-button type="primary" style="width: 100%" @click="handleBtn('loginForm')">登录</el-button>
                    </el-form-item>
                </div>

            </el-form>
        </el-dialog>

        <el-dialog v-model="registerFlag" title="注册" width="400px" center>

            <el-form ref="registerFormRef" :model="registerForm" :rules="registerRules" label-width="auto"
                label-position="right">
                <el-form-item class="from-item" label="名称" prop="nickName">
                    <el-input class="input-width" v-model="registerForm.nickName" autocomplete="off" />
                </el-form-item>
                <el-form-item class="from-item" label="账户" prop="username">
                    <el-input class="input-width" v-model="registerForm.username" autocomplete="off" />
                </el-form-item>
                <el-form-item class="from-item" label="密码" prop="password">
                    <el-input class="input-width" v-model="registerForm.password" autocomplete="off" />
                </el-form-item>
                <el-form-item class="from-item" label="邮箱" prop="email">
                    <div style="display: flex; flex-direction: row;">
                        <el-input class="input-width" v-model="registerForm.email" autocomplete="off" />
                        <el-button @click="requestCode(registerForm.email)">请求验证码</el-button>
                    </div>
                </el-form-item>
                <el-form-item class="from-item" label="验证码" prop="idencode">
                    <el-input class="input-width" v-model="registerForm.idencode" autocomplete="off" />
                </el-form-item>
                <el-form-item class="from-item" label="个人介绍" prop="introduce">
                    <el-input class="input-width" type="textarea" maxlength="100" show-word-limit
                        v-model="registerForm.introduce" autocomplete="off" />
                </el-form-item>
                <el-form-item class="from-item" label="性别" prop="sex">
                    <el-radio-group v-model="registerForm.sex" class="ml-4">
                        <el-radio label=1 size="large">男</el-radio>
                        <el-radio label=2 size="large">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item class="from-item" label="电话" prop="phone">
                    <el-input class="input-width" v-model="registerForm.phone" autocomplete="off" />
                </el-form-item>
                <div style="border: none; background: none; display: flex; justify-content: center; ">
                    <el-form-item class="from-item" style="border: none; background: none; ">
                        <el-button class="input-width" type="primary" style="width: 100%"
                            @click="handleBtn('registerForm')">注册</el-button>
                    </el-form-item>
                </div>

            </el-form>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import type { FormInstance, FormRules } from 'element-plus'

import { useRegisterStore } from '@/stores/register'
import { useLoginStore } from '@/stores/login'

interface APILoginForm {
    username: string
    password: string
}

interface APIResigerForm {
    nickName: string
    username: string
    password: string
    userName: string
    email: string
    idencode: string
    introduce: string
    sex: number
    phone: string
}



const loginFormRef = ref<FormInstance>()
const registerFormRef = ref<FormInstance>()

const loginForm = reactive<APILoginForm>({
    username: '',
    password: ''
})



const loginRules = reactive<FormRules<APILoginForm>>({
    username: [
        { required: true, message: '请输入账号', trigger: 'blur' },
    ],
    password: [
        { required: true, message: '请输入账号密码', trigger: 'blur' },
    ],
})

const registerForm = reactive<APIResigerForm>({
    nickName: '',
    username: '',
    password: '',
    userName: '',
    email: '',
    idencode: '',
    introduce: '',
    sex: 0,
    phone: ''

})
const registerRules = reactive<FormRules<APIResigerForm>>({
    nickName: [

    ],
    userName: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 3, max: 8, message: '请输入长度3-5的用户名', trigger: 'blur' },
    ],
    username: [
        { required: true, message: '请输入账号', trigger: 'blur' },
        { min: 3, max: 8, message: '请输入长度3-5的账号', trigger: 'blur' },
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 3, max: 8, message: '请输入长度3-5的密码', trigger: 'blur' },
    ],
    email: [
        { required: true, message: '请输入邮箱', trigger: 'blur' },
    ],
    idencode: [
        { required: true, message: '请输入验证码', trigger: 'blur' },
        { min: 3, max: 8, message: '请输入长度3-5的验证码', trigger: 'blur' },
    ],
})

const requestCode = (val: string) => {
    console.log(val);
    useRegisterStore().requestCodeFn({ email: val })

}
const loginFlag = ref(false)
const registerFlag = ref(false)
const handleBtn = (str: string) => {
    if (str === 'loginBtn') {
        loginFormRef.value?.resetFields()
        loginFlag.value = true
    } else if (str === 'register') {
        registerFormRef.value?.resetFields()
        registerFlag.value = true
    } else if (str === 'registerForm') {
        console.log(registerForm);
        useRegisterStore().registerFn(registerForm)
    } else if (str === 'loginForm') {
        console.log(loginForm);
        useLoginStore().loginFn(loginForm)
        // useRegisterStore().registerFn(registerForm)
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


.from-item {
    width: 360px;

    .input-width {
        width: 170px;
    }
}
</style>