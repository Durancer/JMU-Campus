<template>
  <template v-if="post">
    <PostItem v-bind="post"></PostItem>
    <!-- 评论 -->
    <template v-if="post.commentList?.length >= 1">
      <!-- TODO：增加评论   -->
      评论
    </template>
    <template v-else>暂时还没有评论</template>
  </template>
  <template v-else>还在加载中...</template>
  <!-- 后面优化 -->
</template>

<script setup lang="ts">
import { getPostDetail } from '@/api/posts/index.ts'
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import PostItem from '@/components/PostItem.vue'

const post = ref({})
const route = useRoute()
const getPostDetailFn = async (postId: string) => {
  const res = await getPostDetail(postId)
  post.value = res.data
  console.log(post.value, 'post.value')
}
onMounted(() => getPostDetailFn(route.params.postId as string))
</script>

<style lang="less" scoped></style>
