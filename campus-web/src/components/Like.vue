<template>
  <div class="like" @click="clickFn" :class="{ isLike: isLike || tmpIsLike }">
    <SvgIcon name="like"></SvgIcon>
    <span class="likeNum">{{ tmpLikeNum }}</span>
  </div>
</template>

<script setup lang="ts">
import SvgIcon from '@/components/SvgIcon.vue'
import debounce from 'lodash.debounce'
import { computed, ref } from 'vue'
import { useUserStore } from '@/stores/userStore'
import { failMessage } from '@/utils/common'
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
const emitClickFn = debounce(() => {
  if (tmpIsLike.value !== props.isLike) {
    emit('like-click')
  }
}, 1000)

const userStore = useUserStore()
const isLogin = computed(() => userStore.token)

// 点赞或者取消点赞都需要及时更新颜色和数字
// 但是只有最后一次才发送http请求
const clickFn = () => {
  if (!isLogin.value) {
    failMessage('请先登录')
    return
  }
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
