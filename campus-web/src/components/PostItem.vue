<template>
  <div class="post-item">
    <UserInfo v-bind="userInfo" :create-time="createTime"></UserInfo>
    <h2 class="title" @click="jumpPostDetail">
      {{ title }}
    </h2>
    <div class="content" :class="{ detail: isDetailPage }" @click="jumpPostDetail">
      <p v-html="content"></p>
    </div>
    <div class="footer">
      <!-- 点赞 -->
      <Like :likeNum="likeNum" :isLike="isLike" @like-click="likeFn(id)"></Like>
      <!-- 浏览量 -->
      <View class="myicon" />
      <span>{{ viewNum }}</span>
    </div>
  </div>
  <!-- TODO  根据首页和详情页信息进行变动(主要是内容是否完全展示出来) -->
  <template v-if="isDetailPage && voteMessage?.voteId">
    <VoteCard v-bind="voteMessage"></VoteCard>
  </template>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { like } from '@/api/posts/index.ts'
import { failMessage, sucMessage } from '@/utils/common'
import UserInfo from '@/components/UserInfo.vue'
import Like from '@/components/Like.vue'
import VoteCard from '@/components/VoteCard.vue'

interface postItemUserInfo {
  id: number
  nickname: string
  avatarUrl: string
  sex: number
}
interface voteOption {
  optionId: number
  voteId: number
  content: string
  num: number
  ratio: string
}
interface voteMessageInter {
  voteId: number
  postId: number
  topic: string
  type: string
  expired: boolean
  amount: number
  isVote: null | number[]
  optionList: voteOption[]
}
interface Props {
  id: number
  userInfo: postItemUserInfo
  title: string // 后期补一下title属性
  content: string
  viewNum: number
  imgList?: null | string[]
  voteMessage?: null | voteMessageInter
  userLikeBOList?: null | postItemUserInfo[]
  createTime: string
  commentNum?: number // detail
  likeNum?: number
  commentList?: []
  topics?: string[]
  isLike?: boolean
}
const props = defineProps<Props>()
// const likeNum = computed(() => props.userLikeBOList?.length ?? 0)

const router = useRouter()
const route = useRoute()
const jumpPostDetail = () => {
  router.push({ name: 'detail', params: { postId: props.id } })
}
const isDetailPage = computed(() => {
  return route.name === 'detail'
})

const likeFn = async (postId) => {
  const res = await like(postId)
  if (res.status) {
    sucMessage(res.message)
  }
}
</script>

<style lang="less" scoped>
.post-item {
  display: flex;
  flex-direction: column;
  padding: 20px;
  border-bottom: 1px solid #f1f1f1;

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
    // white-space: nowrap;
    cursor: pointer;
  }
  .content.detail p {
    overflow: visible;
    display: block;
    cursor: pointer;
  }
  .footer {
    display: flex;
    align-items: center;
    span {
      margin-right: 5px;
    }
    .myicon {
      width: 1em;
      height: 1em;
      margin-right: 8px;
      cursor: pointer;
    }
    .active {
      color: red;
    }
  }
}
</style>
