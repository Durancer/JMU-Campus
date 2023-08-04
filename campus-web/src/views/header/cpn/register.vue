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
            <el-button @click="requestCode(registerForm[item.prop])">请求验证码</el-button>
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
            @click="handleBtn('registerForm')"
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

import { useRegisterStore } from '@/stores/register'

const emits = defineEmits(['close'])

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
  [key: string]: string | number
}

const registerFlag = ref(false)
const registerFormRef = ref<FormInstance>()

const registerForm = reactive<APIResigerForm>({
  nickname: '',
  username: '',
  password: '',
  email: '',
  idencode: '',
  introduce: '',
  sex: 0,
  phone: ''
})
const registerRules = reactive<FormRules<APIResigerForm>>({
  nickName: [],
  userName: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 8, message: '请输入长度3-5的用户名', trigger: 'blur' }
  ],
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    { min: 3, max: 8, message: '请输入长度3-5的账号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 3, max: 8, message: '请输入长度3-5的密码', trigger: 'blur' }
  ],
  email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
  idencode: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { min: 3, max: 8, message: '请输入长度3-5的验证码', trigger: 'blur' }
  ]
})

const requestCode = (val: string) => {
  console.log(val)
  try {
    useRegisterStore().requestCodeFn({ email: val })
    emits('close')
  } catch (err) {}
}

const handleBtn = (str: string) => {
  if (str === 'register') {
    registerFormRef.value?.resetFields()
    registerFlag.value = true
  } else if (str === 'registerForm') {
    console.log(registerForm)
    useRegisterStore().registerFn(registerForm)
  }
}

const registerFromSetting = reactive([
  {
    label: '名称',
    prop: 'nickName',
    inputArr: {
      type: 'text',
      placeholder: '请输入用户名称',
      'show-password': false,
      autocomplete: 'off'
    }
  },
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
  },
  {
    label: '个人介绍',
    prop: 'introduce',
    inputArr: {
      type: 'text',
      placeholder: '请输入账户',
      'show-password': false,
      autocomplete: 'off'
    }
  },
  {
    label: '性别',
    prop: 'sex',
    inputArr: {
      type: 'text',
      placeholder: '请输入性别',
      'show-password': false,
      autocomplete: 'off'
    }
  },
  {
    label: '电话',
    prop: 'phone',
    inputArr: {
      type: 'text',
      placeholder: '请输入电话',
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
