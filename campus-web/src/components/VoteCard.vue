<template>
  <el-card style="margin-left: 20px" :class="{ expired: disabled }">
    <!-- TODO 根据isVote展示是否已经投完票了 -->
    <template #header>
      投票：{{ topic }}{{ type === 'multiple' ? '(多选)' : '(单选)' }}
      <span class="expire-text" v-show="!expired">已过期</span>
      <span class="has-vote-text" v-show="isVote?.length > 0">已经投过票了</span>
    </template>
    <template v-if="type === 'radio'">
      <el-radio-group v-model="optionId">
        <el-radio
          :label="option.optionId"
          size="large"
          border
          v-for="(option, idx) in optionList"
          :key="option.optionId"
          :disabled="disabled"
          >{{ option.content }} 当前{{ option.num }}票</el-radio
        >
      </el-radio-group>
    </template>
    <template v-else>
      <el-checkbox-group v-model="optionIds">
        <el-checkbox
          border
          v-for="(option, idx) in optionList"
          :label="option.optionId"
          :key="option.optionId"
          :disabled="disabled"
          >{{ option.content }} 当前{{ option.num }}票</el-checkbox
        >
      </el-checkbox-group>
    </template>
    <el-button
      type="primary"
      @click="voteFn"
      style="margin-top: 1em; display: block"
      :disabled="disabled"
      >投票</el-button
    >
  </el-card>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { vote } from '@/api/posts/index.ts'
import { useUserStore } from '@/stores/userStore'
import { failMessage, sucMessage } from '@/utils/common'

interface voteOption {
  optionId: number
  voteId: number
  content: string
  num: number
  ratio: string
}
interface Props {
  voteId: number
  postId: number
  topic: string
  type: string
  expired: boolean
  amount: number
  isVote: null | number[]
  optionList: voteOption[]
}
const props = defineProps<Props>()
const userStore = useUserStore()
const optionId = ref('') // 单选框的答案
const optionIds = ref([]) // 多选框的答案
// TODO: 代码重构
const voteFn = async () => {
  if (!userStore.userInfo.id) {
    failMessage('请先登录再投票')
    return
  }
  const data = {
    postId: props?.postId,
    voteId: props?.voteId
  }
  if (props?.type === 'radio') {
    data.optionIds = +optionId.value
    const res = await vote(data, userStore.userInfo.id)
    res.status && sucMessage('投票成功')
  } else {
    data.optionIds = optionIds.value
    let formData = new FormData()
    for (let key in data) {
      formData.append(key, data[key])
    }
    const res = await vote(formData, userStore.userInfo.id)
    res.status && sucMessage('投票成功')
  }
}
// 过期或者投过票，则不可以继续投
const disabled = computed(() => !props.expired || props.isVote?.length > 0)
</script>

<style lang="less" scoped>
.expired {
  font-weight: 100;
  .expire-text {
    margin: 0 5px;
  }
  &:deep(.el-card__header) {
    text-decoration: line-through;
  }
}
</style>
