<template>
  <template v-if="post?.id">
    <PostItem v-bind="post"></PostItem>
    <!-- TODO：增加评论  临时改变  其他不变   -->
    <div class="comment-root-wrapper">
      <el-input v-model="comment" :rows="2" type="textarea" ref="commentTextarea" placeholder="快来评论吧" />
      <el-button type="primary" @click="addCommentFn" style="margin-top: 10px">增加评论</el-button>
    </div>
    <div style="margin-left: 20px">
      <el-divider content-position="center">评论</el-divider>
      <!-- 评论 -->
      <template v-if="commentList?.length >= 1">
        <!-- 评论列表 -->
        <template v-for="comment in commentList" :key="comment.id">
          <!-- 根评论 -->
          <div class="comment-item">
            <UserInfo v-bind="comment.userInfo" :create-time="comment.createTime"></UserInfo>
            <div class="comment-content">{{ comment.content }}</div>
            <CommentFooter v-bind="comment"></CommentFooter>
          </div>
          <!-- 子评论 -->
          <div class="sub-comment" v-if="comment.answerCommentList?.length > 0">
            <el-card>
              <div v-for="(subComment, idx) in comment.answerCommentList" :key="subComment.id">
                <!-- 子评论卡片 -->
                <div class="sub-comment-card">
                  <div class="sub-comment-item">
                    <el-avatar :src="subComment.userInfo.avatarUrl" :size="20"></el-avatar>
                  </div>
                  <div class="sub-comment-content">
                    <div class="sub-comment-content-title">
                      {{ subComment.userInfo.nickname }}
                      <span class="reply_text">回复</span>
                      {{ subComment.answerUserInfo.nickname }}
                    </div>
                    <div class="sub-comment-content-content">{{ subComment.content }}</div>
                    <CommentFooter v-bind="subComment"></CommentFooter>
                  </div>
                </div>
                <el-divider v-if="idx !== comment.answerCommentList?.length - 1" />
              </div>
            </el-card>
          </div>
          <el-divider />
        </template>
      </template>
      <template v-else>暂时还没有评论</template>

      <!-- 删除帖子 -->
      <template v-if="isDelete">
        <div class="delete-btn">
          <el-popconfirm width="220" confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled"
            icon-color="#626AEF" title="是否要删除这篇帖子?" @confirm="deletePostFn(post.id)">
            <template #reference>
              <el-button type="danger">删除这篇帖子</el-button>
            </template>
          </el-popconfirm>
          <el-button type="danger" @click="deleteVoteFn(post.voteMessage?.voteId)"
            v-if="post.voteMessage?.voteId">删除投票</el-button>
        </div>
      </template>
    </div>
  </template>
  <template v-else>还在加载中...</template>
  <!-- 后面优化 -->
</template>

<script setup lang="ts">
import { getPostDetail, deletePost, deleteVote } from '@/api/posts/index.ts'
import { addComment, likeComment, deleteComment, getUserComments } from '@/api/comments/index.ts'
import { computed, onMounted, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import PostItem from '@/components/PostItem.vue'
import { failMessage, handleTime, sucMessage } from '@/utils/common'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore.ts'
import { InfoFilled } from '@element-plus/icons-vue'

interface UserInfo {
  id: number
  nickname: string
  avatarUrl: string
  sex:number
}

interface CommentList {
  userInfo: UserInfo
  createTime: string
  content: string
  id: number
  postId: number
  answerCommentList: CommentList[]
  answerUserInfo: UserInfo
}

interface Post {
  voteMessage: any
  id:string
}

const post = ref<Post>()
const commentList = ref<CommentList[]>([])
const route = useRoute()
const getPostDetailFn = async (postId: string) => {
  const userId = userStore.userInfo?.id
  const res = await getPostDetail(postId, userId)
  post.value = res.data
}
const getCommentList = async (postId: number) => {
  const res = await getUserComments(postId)
  commentList.value = res.data.records
}

const deleteVoteFn = async (voteId: any) => {
  const res = await deleteVote(voteId)
  if (res.status) {
    sucMessage('删除投票成功')
    getPostDetailFn(route.params.postId as string)
  }
}
const router = useRouter()
const userStore = useUserStore()
let isDelete = ref(false)
watch(post, (newPost) => {
  isDelete.value = userStore?.userInfo?.id === newPost?.userInfo?.id
})
const deletePostFn = async (postId: string) => {
  const res = await deletePost(postId)
  if (res.status) {
    sucMessage('删除成功')
    router.push({ name: 'index' })
  }
}
const comment = ref('')
const addCommentFn = async () => {
  const data = {
    postId: route.params.postId,
    content: comment.value,
    type: 'root'
  }
  addCommentCommon(data)
}
const addCommentCommon = async (data:any) => {
  // 判断是否登录
  if (!userStore.userInfo?.id) {
    failMessage('请先登录在进行评论')
    return
  }
  if (!comment.value) {
    failMessage('评论不能为空')
  } else {
    const res = await addComment(data)
    if (res.status) {
      sucMessage('添加评论成功')
      comment.value = ''
    } else {
      failMessage('添加评论失败')
    }
  }
}
onMounted(() => {
  getPostDetailFn(route.params.postId as string)
  getCommentList(Number(route.params.postId))
})
</script>

<style lang="less" scoped>
.comment-item {
  display: flex;
  flex-direction: column;
  padding: 20px;
}

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

.sub-comment {
  padding: 0 20px;

  .el-card {
    margin-left: 20px;
  }

  &:deep(.el-card) {
    background-color: #f8f8f8;
    border-radius: 12px;
    // margin-top: 16px;
    border: none;
    // padding: 4px 0;
  }

  &:deep(.el-card__body) {
    padding: 0;
  }

  &:deep(.el-divider) {
    margin: 5px 0;
  }
}

.sub-comment-card {
  // margin-left: ; background: ;
  display: flex;
  padding-bottom: 12px;
  padding-left: 16px;
  padding-top: 12px;

  .sub-comment-content {
    display: flex;
    flex-direction: column;
    margin-left: 8px;
  }

  .sub-comment-content-title {
    display: flex;
    align-items: center;
    height: 20px;
    line-height: 20px;
    font-size: 1.2em;

    .reply_text {
      color: #888;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      margin-left: 8px;
      margin-right: 8px;
    }
  }
}

.comment-root-wrapper {
  padding: 20px 0 20px 20px;

  .el-input {
    width: 100%;
  }
}

.sub-comment-content-content {
  margin: 5px 0;
}

.sub-comment-content-footer {
  display: flex;
  align-items: center;
}

.comment-content {
  margin: 5px 0;
}

.comment-delete-btn {
  cursor: pointer;
}

.comment-delete-btn:hover {
  color: red;
}

.reply,
.comment-delete-btn {
  cursor: pointer;
}

.reply:hover {
  color: aqua;
}

.el-divider {
  margin: 20px 0 0;
}

.delete-btn {
  margin-top: 20px;
}
</style>
