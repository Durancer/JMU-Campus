<template>
  <div class="login-card">
    <el-card shadow="always">
      <template #header>
        <h1 style="text-align: center">i集大校园登录</h1>
      </template>
      <el-form ref="loginFormRef" :model="loginForm" :rules="loginRules" label-width="auto" label-position="right">
        <el-form-item v-for="item in loginFromSetting" :key="item.label" v-bind="item">
          <el-input v-model="loginForm[item.prop]" v-bind="item.inputArr" />
        </el-form-item>
        <div style="display: flex; justify-content: center">
          <el-button type="primary" @click="login">登录</el-button>
          <el-button plain type="primary" @click="goRegister">注册</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'

import { useUserStore } from '@/stores/userStore.ts'
import router from '@/router'

const userStore = useUserStore()

interface APILoginForm {
  username: string
  password: string
  [key: string]: string
}

const loginFormRef = ref<FormInstance>()

const loginForm = reactive<APILoginForm>({
  username: '',
  password: ''
})

const loginRules = reactive<FormRules<APILoginForm>>({
  username: [{ required: true, message: '请输入账号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入账号密码', trigger: 'blur' }]
})

const goRegister = () => {
  router.push('/register')
}

const login = async () => {
  try {
    userStore.loginFn(loginForm)
  } catch (err) { }
}

const loginFromSetting = reactive([
  {
    label: '账户',
    prop: 'username',
    inputArr: {
      type: 'text',
      placeholder: '请输入账户',
      'show-password': false,
      autocomplete: 'off'
    }
  },
  {
    label: '密码',
    prop: 'password',
    inputArr: {
      type: 'text',
      placeholder: '请输入密码',
      'show-password': true
    }
  }
])
</script>

<style scoped lang="less">
.login-card {
  position: fixed;
  left: 50%;
  top: 50%;
  width: 30%;
  transform: translate(-50%, -50%);

}
</style>
