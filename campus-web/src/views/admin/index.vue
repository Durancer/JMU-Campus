<template>
  <el-table :data="data">
    <el-table-column prop="id" label="编号" width="80"></el-table-column>
    <el-table-column prop="userInfo.nickname" label="用户名称" width="120"></el-table-column>
    <el-table-column prop="title" label="帖子标题"></el-table-column>
    <el-table-column prop="content" label="帖子内容"></el-table-column>
    <el-table-column prop="viewNum" label="阅读量" width="80"></el-table-column>
    <el-table-column prop="createTime" label="创建时间"></el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button type="success" @click="checkPostFn(scope.row.id, 1)">通过审核</el-button>
        <el-button type="success" @click="checkPostFn(scope.row.id, 2)">不通过审核</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup lang="ts">
import { checkPost, getNotCheckedPosts } from '@/api/posts/index.ts'
import { sucMessage, failMessage } from '@/utils/common'
import { ref, onMounted } from 'vue'
const data = ref([])
const current = ref<number>(1) // 当前是第几页
const size = ref<number>(20) // 多少页
const getNotCheckedPostsFn = async ({ current, size }) => {
  const res = await getNotCheckedPosts({ current, size })
  if (res.data) {
    data.value = res.data.records
    console.log(res.data.records, data.value)
  }
}
const checkPostFn = async (postId, type) => {
  const res = await checkPost(postId, type)
  if (res.status) {
    sucMessage('操作成功')
    getNotCheckedPostsFn({ current: current.value, size: size.value })
  } else {
    failMessage('操作失败')
  }
}
onMounted(() => {
  getNotCheckedPostsFn({ current: current.value, size: size.value })
})
</script>

<style lang="less" scoped>
.el-table {
  width: 80%;
  margin: 0 auto;
}
</style>
