<template>
  <div>
    <div class="enty-list-wrap">
      <ul class="entry-list" v-infinite-scroll="toBottom" :infinite-scroll-disabled="isDisabled">
        <li class="item" v-for="item in records" :key="item.id">
          <PostItem v-bind="item" />
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
import PostItem from '@/components/PostItem.vue'
import debounce from 'lodash.debounce'
const props = defineProps(['records','isDisabled'])
const emit = defineEmits(['to-bottom'])


// TODO：bug一开始就会翻到第二页---已修复
const toBottom = debounce(() => {
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
