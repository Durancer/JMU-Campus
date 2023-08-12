<template>
  <div>
    <el-form
      ref="registerFormRef"
      :model="registerForm"
      :rules="registerRules"
      label-width="auto"
      label-position="right"
    >
      <el-form-item
        class="from-item"
        v-for="item in registerFromSetting"
        :key="item.label"
        v-bind="item"
      >
        <template v-if="item.prop === 'email'">
          <div style="display: flex; flex-direction: row">
            <el-input class="input-width" v-model="registerForm[item.prop]" autocomplete="off" />
            <el-button @click="requestCodeFn">
              <template v-if="registerEmailFlag"> 已发送</template>
              <template v-else> 请求验证码 </template>
            </el-button>
          </div>
        </template>
        <template v-else-if="item.prop === 'idencode'">
          <div style="display: flex; flex-direction: row">
            123<el-input
              class="input-width"
              v-model.number="registerForm[item.prop]"
              autocomplete="off"
            />
          </div>
        </template>
        <template v-else-if="item.prop === 'sex'">
          <el-radio-group v-model="registerForm.sex" class="ml-4">
            <el-radio label="1" size="large">男</el-radio>
            <el-radio label="2" size="large">女</el-radio>
          </el-radio-group>
        </template>
        <el-input
          v-else
          class="input-width"
          v-model="registerForm[item.prop]"
          v-bind="item.inputArr"
        />
      </el-form-item>
      <div style="border: none; background: none; display: flex; justify-content: center">
        <el-form-item class="from-item" style="border: none; background: none">
          <el-button
            class="input-width"
            type="primary"
            style="width: 100%"
            @click="handleRegister(registerFormRef)"
            >注册</el-button
          >
        </el-form-item>
      </div>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { register, requestCode } from '@/api/user/index.ts'
import type { userinfo } from '@/api/user/type.ts'

const emits = defineEmits(['close'])

const registerEmailFlag = ref(false)
const registerFormRef = ref<FormInstance>()

const registerForm = reactive<userinfo>({
  username: '活得简单点',
  password: '123456',
  idencode: 514861,
  email: '1547025615@qq.com',
  nickname: 'be simple'
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
  console.log(registerForm.email)
  const res = await requestCode(registerForm.email)
  console.log(res)
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
.from-item {
  width: 360px;

  .input-width {
    width: 170px;
  }
}
</style>
