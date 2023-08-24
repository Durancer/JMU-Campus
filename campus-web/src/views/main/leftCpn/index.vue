<template>
  <div>
    <nav class="nav-slide-wrap">
      <div v-for="(item, index) in leftTabsList" :key="item.value" class="nav-item-wrap">
        <div class="nav-item-content">
          <a class="nav-item">
            <span class="nav-item-texx">
              {{ item.lable }}
            </span>
          </a>
        </div>
      </div>
    </nav>
  </div>
</template>

<script setup lang="ts">
defineProps(['leftTabsList'])
</script>

<style scoped lang="less">
.nav-slide-wrap {
  min-width: 180px;
  box-sizing: border-box;
  padding: 8px;
  font-size: 16px;
  color: #515767;
  overflow: visible;
  .nav-item-wrap {
    display: flex;
    flex-direction: column;

    .nav-item-content {
      line-height: 24px;
      border-radius: 4px;
      cursor: pointer;
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: space-between;

      .nav-item {
        display: inline-block;
        width: 100%;
        box-sizing: border-box;
        position: relative;
        padding: 10px 17px;
        color: #515767;

        .nav-item-text {
          vertical-align: middle;
          position: relative;
        }
      }
    }
  }
}
</style>
