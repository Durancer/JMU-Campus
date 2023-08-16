<template>
  <div class="post-item">
    <UserInfo v-bind="userInfo" :create-time="createTime"></UserInfo>
    <div class="title" @click="jumpPostDetail">
      {{ title }}
    </div>
    <div class="content" @click="jumpPostDetail">
      <p>
        {{ content }}
      </p>
    </div>
    <div class="footer">
      <!-- 点赞 -->
      <!-- <Position class="myicon" :class="{ active: isActive }" @click="likeFn(id)" />
      <span :class="{ active: isActive }">{{ likeNumCopy }}</span> -->
      <Like :likeNum="likeNum" :isLike="isLike" @like-click="likeFn(id)"></Like>
      <!-- 浏览量 -->
      <View class="myicon" />
      <span>{{ viewNum }}</span>
    </div>
  </div>
  <!-- TODO  根据首页和详情页信息进行变动(主要是内容是否完全展示出来) -->
  <template v-if="isDetailPage && voteMessage?.voteId">
    <el-card>
      <!-- TODO 根据isVote展示是否已经投完票了 -->
      <template #header>
        投票：{{ voteMessage.topic }}{{ voteMessage.type === 'multiple' ? '(多选)' : '(单选)' }}
      </template>
      <template v-if="voteMessage.type === 'radio'">
        <el-radio-group v-model="optionId">
          <el-radio
            :label="option.optionId"
            size="large"
            border
            v-for="(option, idx) in voteMessage.optionList"
            :key="option.optionId"
            >{{ option.content }} 当前{{ option.num }}票</el-radio
          >
        </el-radio-group>
      </template>
      <template v-else>
        <el-checkbox-group v-model="optionIds">
          <el-checkbox
            border
            v-for="(option, idx) in voteMessage.optionList"
            :label="option.optionId"
            :key="option.optionId"
            >{{ option.content }} 当前{{ option.num }}票</el-checkbox
          >
        </el-checkbox-group>
      </template>
      <el-button type="primary" @click="voteFn" style="margin-top: 1em; display: block"
        >投票</el-button
      >
    </el-card>
  </template>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { vote, like } from '@/api/posts/index.ts'
import { useUserStore } from '@/stores/userStore'
import { failMessage, sucMessage } from '@/utils/common'
import UserInfo from '@/components/UserInfo.vue'
import Like from '@/components/Like.vue'

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
  isVote: null | boolean
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
  isLike?: boolean
}
const props = defineProps<Props>()
// const likeNum = computed(() => props.userLikeBOList?.length ?? 0)
const optionId = ref('') // 单选框的答案
const optionIds = ref([]) // 多选框的答案
// const optionIds = computed(() => {
//   if (!props.voteMessage?.voteId) {
//     return null
//   }
//   if (props.voteMessage?.type === 'radio') {
//     return ''
//   } else if (props.voteMessage?.type === 'multiple') {
//     return []
//   }
// })
const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const jumpPostDetail = () => {
  router.push({ name: 'detail', params: { postId: props.id } })
}
const isDetailPage = computed(() => {
  return route.name === 'detail'
})
// TODO: 代码重构
const voteFn = async () => {
  if (!userStore.userInfo.id) {
    failMessage('请先登录再投票')
    return
  }
  const data = {
    postId: props.voteMessage?.postId,
    voteId: props.voteMessage?.voteId
  }
  if (props.voteMessage?.type === 'radio') {
    data.optionIds = Number(optionId.value)
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

const isActive = ref(props.isLike)
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
    white-space: nowrap;
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
