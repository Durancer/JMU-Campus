<template>
  <!-- TODO 表单验证 ，删除功能，增加完成一个的清空功能-->
  <el-card>
    <el-form v-model="voteForm" ref="formRef" label-width="200px">
      <el-form-item label="请输入投票标题" prop="topic">
        <el-input v-model="voteForm.topic"></el-input>
      </el-form-item>
      <el-form-item label="请输入投票周期" prop="cycle">
        <el-select v-model="voteForm.cycle" placeholder="请输入投票周期">
          <el-option label="一天" value="day"></el-option>
          <el-option label="一周" value="week"></el-option>
          <el-option label="一月" value="month"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="请输入投票类型(单选、多选)" prop="type">
        <el-select v-model="voteForm.type" placeholder="请输入投票类型(单选、多选)">
          <el-option label="单选" value="radio"></el-option>
          <el-option label="多选" value="multiple"></el-option>
        </el-select>
      </el-form-item>
      <template v-if="voteForm.options.length > 0">
        <template v-if="voteForm.type === 'radio'">
          <el-form-item>
            <el-radio-group>
              <el-radio v-for="(item, idx) in voteForm.options" :key="idx + '_'" :label="item">{{
                item
              }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </template>
        <template v-else>
          <el-form-item>
            <el-checkbox-group>
              <el-checkbox v-for="(item, idx) in voteForm.options" :key="idx + '_'" :label="item" />
            </el-checkbox-group>
          </el-form-item>
        </template>
      </template>
      <el-form-item label="增加投票选项">
        <el-input v-model="option"></el-input>
        <el-button type="info" @click="addOption">增加</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import type { FormInstance } from 'element-plus'
import { failMessage } from '@/utils/common'

const emit = defineEmits(['submit'])
const formRef = ref<FormInstance>()
const option = ref('')
const voteForm = reactive({
  topic: '',
  type: '',
  cycle: '',
  options: []
})
const addOption = () => {
  if (!option.value) {
    failMessage('请输入投票选项')
  } else {
    voteForm.options.push(option.value)
  }
}

defineExpose({
  voteForm
})
</script>

<style lang="less" scoped>
.el-select,
.el-input {
  width: 70%;
}
</style>
