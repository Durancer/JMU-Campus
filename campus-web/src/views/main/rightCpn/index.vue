<template>
  <div>
    <aside class="aside-wrap">
      <div class="aside-header">
        <h2 class="title">热搜话题</h2>
      </div>
      <div class="aside-content">
        <ul class="main-item" v-for="(topic, idx) in topicList" :key="topic.key">
          <li class="item-box">
            <RouterLink :to="{ name: 'index', query: { topic: topic.name } }">
              <span class="order">
                {{ idx + 1 }}
              </span>
              <span class="title">
                {{ topic.name }}
              </span>
            </RouterLink>
          </li>
        </ul>
      </div>
    </aside>
  </div>
</template>

<script setup lang="ts">
import { useFetchTopic } from '@/hooks/useFetchTopic'
const topicList = useFetchTopic()
</script>

<style scoped lang="less">
.aside-wrap {
  //   width: 200px;
  display: block;
  background-color: #fff;
  box-shadow: 0 1px 2px #00051008, 0 2px 6px #00051008;
  border-radius: 12px;
  // text-align: center;
  padding: 20px 0;

  .title {
    text-align: center;
  }

  .aside-content {
    .main-item {
      width: 100%;
      //   padding: 0 14px;
      &:nth-child(1) .item-box .order {
        color: var(--az-hot-color-1);
      }

      &:nth-child(2) .item-box .order {
        color: var(--az-hot-color-2);
      }

      &:nth-child(3) .item-box .order {
        color: var(--az-hot-color-3);
      }

      .item-box {
        position: relative;
        height: 40px;
        padding: 0 26px 0 21px;
        overflow: hidden;
        white-space: nowrap;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: var(--el-font-size-large);
        transition: 0.3s;

        &:hover {
          background-color: #e1e1e1;
          cursor: pointer;
        }

        .order {
          margin-right: 0.5em;
          color: var(--az-font-4);
        }

        .title {
          margin-right: 10px;
          color: #333;
          line-height: 18px;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
      }
    }
  }
}
</style>
