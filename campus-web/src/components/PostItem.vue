<template>
  <div class="post-item">
    <div class="user">
      <div class="user-image">
        <el-avatar :src="userInfo.avatarUrl"></el-avatar>
      </div>
      <div class="user-info">
        <div>{{ userInfo.nickname }}</div>
        <div>{{ createTime }}</div>
      </div>
    </div>
    <div class="title" @click="jumpPostDetail">
      {{ title }}
    </div>
    <div class="content" @click="jumpPostDetail">
      <p>
        {{ content }}
      </p>
    </div>
    <div class="footer">
      <Position style="width: 1em; height: 1em; margin-right: 8px" />
      <span>{{ likeNum }}</span>
      <View style="width: 1em; height: 1em; margin-right: 8px" />
      <span>{{ viewNum }}</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
interface postItemUserInfo {
  id: number
  nickname: string
  avatarUrl: string
  sex: number
}
interface Props {
  id: number
  userInfo: postItemUserInfo
  title: string
  content: string
  viewNum: number
  imgList?: null | string[]
  voteMessage?: null | string
  userLikeBOList?: null | postItemUserInfo[]
  createTime: string
}
const props = defineProps<Props>()
const likeNum = computed(() => props.userLikeBOList?.length ?? 0)

const router = useRouter()
const jumpPostDetail = () => {
  router.push({ name: 'detail', params: { postId: props.id } })
}
</script>

<style lang="less" scoped>
.post-item {
  display: flex;
  flex-direction: column;
  padding: 20px;
  border-bottom: 1px solid #f1f1f1;
  .user {
    display: flex;
    align-items: center;
    .user-image {
      margin-right: 0.5em;
      &:deep(.el-avatar) {
        vertical-align: bottom;
      }
    }
    .user-info {
      display: flex;
      flex-direction: column;
      justify-content: space-between;
    }
  }
  .title {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    cursor: pointer;
  }
  .content p {
    display: -webkit-box;
    -webkit-box-orient: vertical;
    overflow: hidden;
    -webkit-line-clamp: 3;
    white-space: nowrap;
    cursor: pointer;
  }
  .footer {
    display: flex;
    align-items: center;
    span {
      margin-right: 5px;
    }
  }
}
</style>
