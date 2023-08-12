<template>
  <div>
    <div class="enty-list-wrap">
      <ul class="entry-list" v-infinite-scroll="toBottom">
        <li class="item" v-for="item in essay" :key="item.id">
          <PostItem v-bind="item" />
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
import PostItem from '@/components/PostItem.vue'
const props = defineProps(['essay'])
const emit = defineEmits(['to-bottom'])
const toBottom = () => {
  emit('to-bottom')
}
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
