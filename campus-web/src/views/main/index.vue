<template>
  <div class="left-container" :class="{ 'left-top': isHidden }">
    <left-cpn :leftTabsList="leftTabsList" />
  </div>
  <main-cpn />
  <div class="right-container" :class="{ 'right-top': isHidden }">
    <right-cpn />
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, watch } from 'vue'

import leftCpn from './leftCpn/index.vue'
import mainCpn from './mainCpn/index.vue'
import rightCpn from './rightCpn/index.vue'

const { isHidden } = defineProps({
  isHidden: Boolean
})

const leftTabsList = reactive([
  { lable: '推荐', value: 1 },
  { lable: '热榜', value: 2 },
  { lable: 'tab', value: 3 }
])
</script>

<style scoped lang="less">
.left-top.left-container,
.right-top.right-container {
  top: 20px;
  max-height: calc(100vh - 40px);
}

& > .left-container,
& > .right-container {
  position: sticky;
  top: 80px;
}

.left-container,
.right-container {
  width: 180px;
  // margin-right: 20px;
  height: fit-content;
  border-radius: 4px;
  background-color: #fff;
  max-height: calc(100vh - 101px);
  overflow-x: hidden;
}

.right-container {
  width: 260px;
}
</style>
