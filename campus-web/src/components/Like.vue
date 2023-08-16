<template>
  <div class="like" @click="clickFn" :class="{ isLike: isLike || tmpIsLike }">
    <SvgIcon name="like"></SvgIcon>
    <span class="likeNum">{{ tmpLikeNum }}</span>
  </div>
</template>

<script setup lang="ts">
import SvgIcon from '@/components/SvgIcon.vue'
import _ from 'lodash'
import { ref } from 'vue'
const props = defineProps({
  likeNum: {
    required: false,
    type: Number,
    default: 0
  },
  isLike: {
    required: false,
    type: Boolean,
    default: false
  }
})
const tmpLikeNum = ref(props.likeNum)
const tmpIsLike = ref(props.isLike)
const emit = defineEmits(['like-click'])
const emitClickFn = _.debounce(() => {
  if (tmpIsLike.value !== props.isLike) {
    emit('like-click')
  }
}, 1000)

// 点赞或者取消点赞都需要及时更新颜色和数字
// 但是只有最后一次才发送http请求
const clickFn = () => {
  // 之前点过赞
  if (tmpIsLike.value) {
    tmpLikeNum.value -= 1
  } else {
    tmpLikeNum.value += 1
  }
  tmpIsLike.value = !tmpIsLike.value
  emitClickFn()
}
</script>

<style lang="less" scoped>
.like {
  display: inline-flex;
  align-items: center;
  margin-right: 0.3em;
  cursor: pointer;
  :deep(svg:hover) {
    color: green;
  }
  &:hover {
    color: green;
  }
  .likeNum {
    margin-left: 0.3em;
  }
}
.isLike {
  color: green;
}
</style>
