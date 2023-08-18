<template>
  <div class="footer" :class="{ active: isLike }">
    <!-- 点赞 -->
    <Like :likeNum="hot" :isLike="isLike" @like-click="commentLikeFn"></Like>
    <span class="reply" @click="reply">{{
      isReplying && isReplyingId === id ? '取消回复' : '回复TA'
    }}</span>
    <el-popconfirm
      confirm-button-text="确定"
      width="240"
      cancel-button-text="取消"
      title="真的要删除这条评论吗?"
      @confirm="deleteCommentFn()"
      v-if="userInfo.id === userStore.userInfo?.id"
    >
      <template #reference>
        <span class="comment-delete-btn">删除</span>
      </template>
    </el-popconfirm>
  </div>
  <div v-show="isReplying && isReplyingId === id">
    <el-input v-model="subCommentContent" :placeholder="subPlaceholder"></el-input>
    <el-button type="primary" @click="submitSubComment">提交子评论</el-button>
  </div>
</template>

<script setup lang="ts">
import { addComment, likeComment, deleteComment } from '@/api/comments/index.ts'
import { ref } from 'vue'
import { failMessage, sucMessage } from '@/utils/common'
import { useUserStore } from '@/stores/userStore.ts'
interface Props {
  id: number
  postId: number
  userInfo: {
    id: number
    nickname: string
    avatarUrl: string
    sex: number
  }
  answerUserInfo?: {
    id: number
    nickname: string
    avatarUrl: string
    sex: number
  }
  rootId: number
  content?: string
  hot?: number
  type: string
  isLike: boolean
  createTime: string
  answerCommentList?: []
}
const props = defineProps<Props>()
const userStore = useUserStore()
// 回复帖子
const subPlaceholder = ref('')
const subCommentData = ref({})
const subCommentContent = ref('')
const isReplying = ref(false)
const isReplyingId = ref(0)
const reply = () => {
  let data = {
    postId: props.postId,
    type: 'answer',
    rootId: props.rootId,
    toUserId: props.userInfo.id
  }
  let nickname = props.userInfo.nickname
  subPlaceholder.value = '回复:' + nickname
  subCommentData.value = data

  // 评论进行回复
  isReplying.value = !isReplying.value
  isReplyingId.value = props.id
}
//
const addCommentCommon = async (data) => {
  // 判断是否登录
  if (!userStore.userInfo?.id) {
    failMessage('请先登录在进行评论')
    return
  }
  if (!subCommentContent.value) {
    failMessage('评论不能为空')
  } else {
    const res = await addComment(data)
    if (res.status) {
      sucMessage('添加评论成功')
      subCommentContent.value = ''
    } else {
      failMessage('添加评论失败')
    }
  }
}
const submitSubComment = () => {
  subCommentData.value.content = subCommentContent.value
  addCommentCommon(subCommentData.value)
}
// 评论点赞点踩
const commentLikeFn = async () => {
  const userId = userStore.userInfo?.id
  const res = await likeComment(props.id, userId)
  if (res.status) {
    sucMessage(res.message)
  }
}
// 删除评论
const deleteCommentFn = async () => {
  const res = await deleteComment(props.id)
  if (res.status) {
    sucMessage(res.message)
  }
}
</script>

<style lang="less" scoped>
.footer {
  display: flex;
  align-items: center;
  .reply {
    margin: 0 1em 0 0.5em;
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
</style>
