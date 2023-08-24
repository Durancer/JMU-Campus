<template>
  <div>
    <div class="enty-list-wrap">
      <ul class="entry-list" v-infinite-scroll="toBottom">
        <li class="item" v-for="item in records" :key="item.id">
          <PostItem v-bind="item" />
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
import PostItem from '@/components/PostItem.vue'
import _ from 'lodash'
const props = defineProps(['records'])
const emit = defineEmits(['to-bottom'])
// TODO：bug一开始就会翻到第二页
const toBottom = _.debounce(() => {
  console.log('emit-to-bottom')
  emit('to-bottom')
}, 1000)
</script>

<style scoped lang="less">
.enty-list-wrap {
  .entry-list {
    width: 100%;
    background-color: #fff;
    position: relative;

    .item {
      transition: all 0.3s ease-in;
    }
  }
}
</style>
