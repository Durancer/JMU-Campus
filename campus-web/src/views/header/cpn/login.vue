<template>
  <div>
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
          <el-button type="primary" style="width: 100%" @click="handleBtn('loginForm')"
            >登录</el-button
          >
        </el-form-item>
      </div>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import type { emitChangeFn, FormInstance, FormRules } from 'element-plus'

import { useLoginStore } from '@/stores/login'

const emits = defineEmits(['close'])

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

const loginFlag = ref(false)

const handleBtn = async (str: string) => {
  if (str === 'loginForm') {
    console.log(loginForm)
    try {
      await useLoginStore().loginFn(loginForm)
      console.log('5555555555555')
      // ElMessage({
      //     message: '登录成功',
      //     type: 'success'
      // })
      emits('close')
    } catch (err) {}
  }
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

<style scoped lang="less"></style>
