<template>
  <!-- <template v-for="post in postList" :key="post.id">
    <PostItem v-bind="post"></PostItem>
  </template> -->
  <!-- TODO: 增加删除帖子功能 -->
  <template v-if="records.length > 0">
    <PostList :records="records" />
  </template>
</template>

<script setup lang="ts">
import { getSelfPost } from '@/api/posts/index.ts'
import { ref, onMounted, computed } from 'vue'
import PostItem from '@/components/PostItem.vue'
import PostList from '@/views/main/mainCpn/cpn/PostList.vue'
// import { useRoute } from 'vue-router'

const records = ref([])
// const route = useRoute()
// const userId = computed(() => route.params.id)
const getUserPostFn = async () => {
  const res = await getSelfPost()
  records.value = res.data.records
  console.log(res.records)
}
onMounted(() => {
  getUserPostFn()
})
</script>

<style lang="less" scoped></style>
