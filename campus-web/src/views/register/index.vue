<template>
  <el-card class="register-card">
    <template #header>
      <h1 style="text-align: center">注册</h1>
    </template>
    <el-form
      ref="registerFormRef"
      :model="registerForm"
      :rules="registerRules"
      label-width="auto"
      label-position="right"
    >
      <el-form-item
        class="form-item"
        v-for="item in registerFromSetting"
        :key="item.label"
        :label="item.label"
        :prop="item.prop"
      >
        <template v-if="item.prop === 'email'">
          <div style="display: flex">
            <el-input
              class="input-width"
              v-model="registerForm[item.prop]"
              v-bind="item.inputArr"
            />
            <i style="font-size: 3rem">&nbsp;</i>
            <template v-if="registerEmailFlag">
              <el-button @click="requestCodeFn" disabled> 已发送({{ timeCount }}s) </el-button>
            </template>
            <template v-else><el-button @click="requestCodeFn"> 请求验证码 </el-button> </template>
          </div>
        </template>
        <template v-else-if="item.prop === 'idencode'">
          <el-input v-model.number="registerForm[item.prop]" v-bind="item.inputArr" />
        </template>
        <template v-else>
          <el-input v-model="registerForm[item.prop]" v-bind="item.inputArr" />
        </template>
      </el-form-item>
      <div style="border: none; background: none; display: flex; justify-content: center">
        <el-form-item class="form-item" style="border: none; background: none">
          <el-button type="primary" style="width: 100%" @click="handleRegister(registerFormRef)"
            >注册</el-button
          >
        </el-form-item>
      </div>
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { register, requestCode } from '@/api/user/index.ts'
import type { userinfo } from '@/api/user/type.ts'
import { useCountDown } from '@/hooks/useCountDown .ts'

const { timeCount, start } = useCountDown(() => (registerEmailFlag.value = false))
const registerEmailFlag = ref(false)
const registerFormRef = ref<FormInstance>()

const registerForm = reactive<userinfo>({
  username: '',
  password: '',
  idencode: '',
  email: '',
  nickname: ''
})
const registerRules = reactive<FormRules>({
  username: [
    { required: true, message: '请输入用户账号', trigger: 'blur' },
    { min: 1, max: 10, message: '请输入长度1-10的用户账号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 16, message: '请输入长度6-16的密码', trigger: 'blur' }
  ],
  idencode: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
  email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
  nickname: [
    { required: true, message: '请输入用户名称', trigger: 'blur' },
    { min: 1, max: 10, message: '请输入长度1-10的用户名称', trigger: 'blur' }
  ]
})

async function requestCodeFn() {
  registerEmailFlag.value = true
  start(60) // 倒计时60秒钟
  const res = await requestCode(registerForm.email)
  if (res && res.status) {
    registerEmailFlag.value = true
  }
}
async function registerFn(data: any) {
  const res = await register(data)
  if (res.status) {
    ElMessage({
      message: '注册成功',
      type: 'success'
    })
  }
}

const handleRegister = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      registerFn(registerForm)
      emits('close')
    } else {
      ElMessage({
        message: '注册失败',
        type: 'success'
      })
    }
  })
}
const registerFromSetting = reactive([
  {
    label: '用户名称',
    prop: 'nickname',
    inputArr: {
      type: 'text',
      placeholder: '请输入用户名称',
      'show-password': false,
      autocomplete: 'off'
    }
  },
  {
    label: '用户账号',
    prop: 'username',
    inputArr: {
      type: 'text',
      placeholder: '请输入用户账号',
      'show-password': false,
      autocomplete: 'off'
    }
  },
  {
    label: '密码',
    prop: 'password',
    inputArr: {
      type: 'password',
      placeholder: '请输入密码',
      'show-password': true,
      autocomplete: 'off'
    }
  },
  {
    label: '邮箱',
    prop: 'email',
    inputArr: {
      type: 'text',
      placeholder: '请输入邮箱',
      'show-password': false,
      autocomplete: 'off'
    }
  },
  {
    label: '验证码',
    prop: 'idencode',
    inputArr: {
      type: 'text',
      placeholder: '请输入验证码',
      'show-password': false,
      autocomplete: 'off'
    }
  }
])
</script>

<style scoped lang="less">
.form-item {
  width: 480px;
  margin: 20px auto;
  .el-input {
    width: 300px;
  }
}
.register-card {
  position: fixed;
  left: 50%;
  top: 50%;
  width: 40%;
  transform: translate(-50%, -50%);
}
</style>
