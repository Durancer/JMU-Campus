<template>
  <div class="login-card">
    <el-card shadow="always">
      <template #header>
        <h1 style="text-align: center">登录页面</h1>
      </template>
      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginRules"
        label-width="auto"
        label-position="right"
      >
        <el-form-item v-for="item in loginFromSetting" :key="item.label" v-bind="item">
          <el-input v-model="loginForm[item.prop]" v-bind="item.inputArr" />
        </el-form-item>
        <div style="border: none; background: none; display: flex; justify-content: center">
          <el-form-item style="border: none; background: none">
            <el-button type="primary" style="width: 100%" @click="login">登录</el-button>
          </el-form-item>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'

import { useUserStore } from '@/stores/userStore.ts'

const userStore = useUserStore()

interface APILoginForm {
  username: string
  password: string
  [key: string]: string
}

const loginFormRef = ref<FormInstance>()

const loginForm = reactive<APILoginForm>({
  username: '简单点',
  password: '123456'
})

const loginRules = reactive<FormRules<APILoginForm>>({
  username: [{ required: true, message: '请输入账号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入账号密码', trigger: 'blur' }]
})

const login = async () => {
  try {
    userStore.loginFn(loginForm)
  } catch (err) {}
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
  width: 40%;
  transform: translate(-50%, -50%);
}
</style>
