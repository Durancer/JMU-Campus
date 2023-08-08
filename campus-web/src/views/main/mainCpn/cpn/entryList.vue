<template>
  <div>
    <div class="enty-list-wrap">
      <ul class="entry-list" v-infinite-scroll="toBottom">
        <li
          class="item"
          v-for="item in essay"
          :key="item.id"
          @click="$router.push({ name: 'detail', params: { postId: item.id } })"
        >
          <div class="entry">
            <div class="meta-container">
              <div class="user-message">
                <div class="user-popover">
                  {{ item.userInfo.nick }}
                </div>
              </div>
            </div>
            <div class="content-wrapper">
              <div class="content-main">
                <div class="title-row">
                  <div href="#" class="title">{{ item.title }}</div>
                </div>
                <div class="abstract">
                  <div class="text-link">
                    <div class="text">
                      {{ item.content }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
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

      .entry {
        cursor: pointer;
        position: relative;
        background: #fff;
        padding: 12px 20px 0;
        display: flex;
        flex-direction: column;
        align-items: flex-start;

        .meta-container {
          color: #8a919f;
          display: flex;
          align-items: center;

          .user-message {
            display: flex;
            align-items: center;
            margin-right: 12px;
            max-width: 162px;
            font-size: 13px;
            line-height: 22px;
            color: #515767;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            word-break: break-all;

            .user-popover {
              display: inline;
              overflow: hidden;
              text-overflow: ellipsis;
            }
          }
        }

        .content-wrapper {
          display: flex;
          padding-bottom: 12px;
          border-bottom: 1px solid #e4e6eb;
          margin-top: 10px;
          width: 100%;

          .content-main {
            flex: 1 1 auto;

            .title-row {
              display: flex;
              margin-bottom: 8px;

              .title {
                font-weight: 600;
                font-size: 16px;
                line-height: 24px;
                color: #252933;
                width: 100%;
                display: -webkit-box;
                overflow: hidden;
                text-overflow: ellipsis;
                -webkit-box-orient: vertical;
                -webkit-line-clamp: 1;
              }
            }

            .abstract {
              margin-bottom: 8px;

              .text-link {
                color: #8a919f;
                font-size: 13px;
                line-height: 22px;
                display: -webkit-box;
                overflow: hidden;
                text-overflow: ellipsis;
                -webkit-box-orient: vertical;
                -webkit-line-clamp: 1;
              }
            }
          }
        }
      }
    }
  }
}
</style>
