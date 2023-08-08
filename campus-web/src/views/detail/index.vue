<template>detail</template>

<script setup lang="ts">
import { getPostDetail } from '@/api/posts/index.ts'
import { onMounted } from 'vue'
import { useRoute } from 'vue-router'
const route = useRoute()
const getPostDetailFn = async (postId: string) => {
  const res = await getPostDetail(postId)
  console.log(res)
}
onMounted(() => getPostDetailFn(route.params.postId))
</script>

<style lang="less" scoped></style>
