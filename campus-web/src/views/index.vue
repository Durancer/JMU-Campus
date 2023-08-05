<template>
  <div>
    <div class="container">
      <div class="header-box">
        <div class="header-wrapper" :class="{ hidden: isHidden }">
          <header-cpm />
        </div>
      </div>
      <div>
        <main class="main-container">
          <div class="main">
            <RouterView></RouterView>
          </div>
        </main>
        <!-- <main-cpm :is-hidden="isHidden" /> -->
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router'
import { reactive, ref, watch } from 'vue'
import { useScroll, useDebounceFn } from '@vueuse/core'

import headerCpm from './header/index.vue'
import mainCpm from './main/index.vue'

const isHidden = ref(false)

const { directions, isScrolling, arrivedState } = useScroll(document)

const checkHeaderStatus = useDebounceFn((top, bottom, topArrived) => {
  if (topArrived) {
    isHidden.value = false
    return
  }
  if (top) {
    isHidden.value = false
  } else if (bottom) {
    isHidden.value = true
  }
}, 100)
watch(directions, () => {
  if (isScrolling.value) {
    checkHeaderStatus(directions.top, directions.bottom, arrivedState.top)
  }
})
</script>

<style scoped lang="less">
.main-container {
  position: relative;
  max-width: 1200px;
  margin: auto;
  width: 100%;
  height: 100%;
  .main {
    margin-top: 8px;
    display: flex;
    flex-direction: row;
    margin-top: 18px;
    justify-content: space-between;
    height: 100%;
  }
}
.container {
  // max-width: 1200px;
  position: relative;
  margin: 0 auto;
  width: 100%;

  .header-box {
    position: relative;
    height: 60px;

    .hidden.header-wrapper {
      transform: translateY(-60px);
    }

    .header-wrapper {
      position: fixed;
      z-index: 1000;
      top: 0;
      left: 0;
      right: 0;
      height: 60px;
      // border-top: 2px solid #ff8200;
      width: 100%;
      border-color: #f9f9f9;
      background-color: #fff;
      transition: transform 0.15s ease-in-out;
    }
  }
}
</style>
