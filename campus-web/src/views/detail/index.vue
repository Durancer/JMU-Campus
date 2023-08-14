<template>
  <template v-if="post?.id">
    <PostItem v-bind="post"></PostItem>
    <!-- 评论 -->
    <template v-if="post.commentList?.length >= 1">
      <!-- TODO：增加评论   -->
      评论
    </template>
    <template v-else>暂时还没有评论</template>
    <!-- 删除帖子 -->
    <template v-if="isDelete">
      <div>
        <el-popconfirm
          width="220"
          confirm-button-text="确定"
          cancel-button-text="取消"
          :icon="InfoFilled"
          icon-color="#626AEF"
          title="是否要删除这篇帖子?"
          @confirm="deletePostFn(post.id)"
        >
          <template #reference>
            <el-button type="danger">删除这篇帖子</el-button>
          </template>
        </el-popconfirm>
        <el-button
          type="danger"
          @click="deleteVoteFn(post.voteMessage?.voteId)"
          v-if="post.voteMessage?.voteId"
          >删除投票</el-button
        >
      </div>
    </template>
  </template>
  <template v-else>还在加载中...</template>
  <!-- 后面优化 -->
</template>

<script setup lang="ts">
import { getPostDetail, deletePost, deleteVote } from '@/api/posts/index.ts'
import { computed, onMounted, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import PostItem from '@/components/PostItem.vue'
import { failMessage, sucMessage } from '@/utils/common'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore.ts'
import { InfoFilled } from '@element-plus/icons-vue'

const post = ref({})
const route = useRoute()
const getPostDetailFn = async (postId: string) => {
  const res = await getPostDetail(postId)
  post.value = res.data
}
const deleteVoteFn = async (voteId) => {
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
onMounted(() => getPostDetailFn(route.params.postId as string))
</script>

<style lang="less" scoped></style>
