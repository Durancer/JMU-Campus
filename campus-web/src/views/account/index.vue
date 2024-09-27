<template>
  <el-card>
    <div class="avatar">
      <div class="pre-avatar">
        头像：<el-avatar :src="userStore.userInfo?.avatarUrl"></el-avatar>
      </div>
      <el-upload ref="upload" :action="action" :limit="1" :on-exceed="handleExceed" :on-success="handleAvatarSuccess"
        :headers="{
          token: userStore.token
        }">
        <el-button type="primary">点击上传图片</el-button>
        <template #tip>
          <div class="el-upload__tip">头像文件限jpg、jpeg、png、webp格式</div>
        </template>
      </el-upload>
    </div>
  </el-card>
  <el-card>
    <el-form v-model="updateForm" label-width="120px">
      <el-form-item label="昵称名称">
        <el-input v-model="updateForm.nickname"></el-input>
      </el-form-item>
      <el-form-item label="个性介绍">
        <el-input v-model="updateForm.introduce"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select :options="options" v-model="updateForm.sex">
          <el-option label="隐藏" :value="0"></el-option>
          <el-option label="男" :value="1"></el-option>
          <el-option label="女" :value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="电话号码">
        <el-input v-model="updateForm.phone"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleUpdate">确认修改</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted, computed, toRefs } from 'vue'
import { useUserStore } from '@/stores/userStore.ts'
import { genFileId } from 'element-plus'
import type { UploadInstance, UploadProps, UploadRawFile } from 'element-plus'

const userStore = useUserStore()
const options = reactive([0, 1, 2])
let updateForm = reactive({
  nickname: '',
  introduce: '',
  sex: 0,
  phone: ''
})
const handleUpdate = () => {
  userStore.updateUserinfoFn(updateForm)
}
// 上传图片
const action = '/api/user/person/update/avatar'
const upload = ref<UploadInstance>()

const handleExceed: UploadProps['onExceed'] = (files) => {
  console.log(files)
  upload.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  upload.value!.handleStart(file)
  upload.value!.submit()
}
// 上传成功
const handleAvatarSuccess: UploadProps['onSuccess'] = (response, uploadFile) => {
  // 获取新的用户信息
  userStore.getUserDetailFn(userStore.userInfo.id)
}

// todo ：验证表单
onMounted(() => {
  const { nickname, sex, introduce, phone } = userStore.userInfo
  updateForm = reactive({ nickname, sex, introduce, phone })
})
</script>

<style lang="less" scoped>
.el-card {
  width: 100%;
  display: block;
  //   flex-shrink: 0;
  margin: 20px 0;
}

.el-input {
  width: 40%;
}

.avatar {
  display: flex;
  flex-direction: column;

  .pre-avatar {
    display: flex;
    align-items: center;
    margin-bottom: 0.5em;
  }
}
</style>
