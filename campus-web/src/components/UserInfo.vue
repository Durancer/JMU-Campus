<template>
  <div class="user">
    <div>
      <div class="user-image" @click="router.push({ path: '/personalHomepage', query: { id } })">
        <el-avatar :src="avatarUrl"></el-avatar>
      </div>
      <div class="user-info">
        <div>{{ nickname }}</div>
        <div>{{ handleTime(createTime) }}</div>
      </div>
    </div>
    <el-button v-if="id !== -1 && userInfo.id !== id" color="#ff8200" plain size="small" icon="Plus"
      @click="attention(id)">关注</el-button>
  </div>
</template>

<script setup lang="ts">
import { handleTime } from '@/utils/common.ts'
import { useRouter } from 'vue-router'
import { postUserFollow } from '@/api/comments/index.ts'
import { sucMessage } from '@/utils/common'
import { useUserStore } from '@/stores/userStore'
const userInfo = useUserStore().userInfo

const router = useRouter()
interface Props {
  avatarUrl: string
  nickname: string
  createTime: string
  id: number
}
defineProps<Props>()
const attention = async (id: number) => {
  const res = await postUserFollow(id)
  if (res.status) {
    sucMessage(res.message)
  }
}
</script>

<style lang="less" scoped>
.user {
  display: flex;
  align-items: center;
  justify-content: space-between;

  >div {
    display: flex;
    align-items: center;
  }

  .attention {
    width: 64px;
    height: 28px;
    border-radius: 14px;
    border: 1px solid rgb(0, 0, 0)
  }

  .user-image {
    margin-right: 0.5em;

    &:deep(.el-avatar) {
      vertical-align: bottom;
    }

    &:hover {
      cursor: pointer;
    }
  }

  .user-info {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
}
</style>
