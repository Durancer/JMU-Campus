<template>
  <div>
    <div class="main-container">
      <div class="main-content">
        <header-cpn />
        <PostList :records="records" :isDisabled="isDisabled" @to-bottom="handleToBottom" />
        <div :class="{ hidden: isHidden }">还在加载中....</div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, reactive, watch, watchEffect } from 'vue'
import headerCpn from './cpn/headerCpn.vue'
import PostList from './cpn/PostList.vue'
// import asideCpn from './cpn/asideCpn.vue'
import { getAllPosts } from '@/api/posts/index.ts'
const records = reactive([])
const current = ref<number>(1) // 当前是第几页
const size = ref<number>(10) // 多少页
const pages = ref<number>(1) // 总共有多少页
const isHidden = ref(false)
const isDisabled = ref(true)
const getAllPostsFn = async ({ current, size }: { current: number, size: number }) => {
  isHidden.value = false
  const res = await getAllPosts({ current, size })
  isDisabled.value = false
  if (res.data) {
    console.log('现在是第几页', current)
    records.push(...res.data.records)
    pages.value = res.data.pages
    isHidden.value = true
  }
}
const handleToBottom = () => {
  if (current.value < pages.value) {
    current.value += 1
  }
}
watch(current, () => getAllPostsFn({ current: current.value, size: size.value }))
onMounted(() => {
  getAllPostsFn({ current: current.value, size: size.value })
})
</script>

<style scoped lang="less">
.main-container {
  // background-color: #fff;
  border-radius: 4px;
  display: flex;
  justify-content: space-between;

  .main-content {
    // margin-right: 240px;
    border-radius: 2px;
    width: 720px;
    position: relative;
    background-color: #fff;
    // justify-content: space-between;
  }
}
.hidden {
  display: none;
}
</style>
