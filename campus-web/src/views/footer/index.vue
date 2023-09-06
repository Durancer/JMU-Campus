<template>
  <div class="footer">
    <img :src="JCImg" alt="备案" />
    &nbsp;&nbsp;&nbsp;闽ICP备 2023012102号-1
  </div>
</template>

<script setup lang="ts">
import JCImg from '@/assets/img/jc.png'
</script>

<style lang="less" scoped>
.footer {
  height: 40px;
  line-height: 40px;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
