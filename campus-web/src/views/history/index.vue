<template>
  {{ result }}
</template>

<script setup lang="ts">
import { getUserPost } from '@/api/posts/index.ts'
import { ref, onMounted, computed } from 'vue'
import { useRoute } from 'vue-router'

const result = ref({})
const route = useRoute()
const userId = computed(() => route.params.id)
const getUserPostFn = async () => {
  const res = await getUserPost(userId.value)
  console.log(res)
  result.value = res
}
onMounted(() => {
  getUserPostFn()
})
</script>

<style lang="less" scoped></style>
