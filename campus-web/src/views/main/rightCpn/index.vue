<template>
    <div>
        <aside class="aside-wrap">
            <div class="aside-main">
                <div class="aside-header">
                    <div class="title">
                        热搜榜单
                    </div>
                </div>
                <div class="aside-content">
                    <div class="main-item" v-for="i of 6" :key="i">
                        <a href="#">
                            <div class="item-box">
                                <div class="title">
                                    {{ `热搜事件名字${i}` }}
                                </div>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
        </aside>
    </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";

</script>

<style scoped lang="less">
.aside-wrap {
    width: 200px;
    display: block;
    background-color: #fff;

    .aside-main {
        .aside-header {
            height: 25px;
            padding: 12px 14px 8px;
            display: flex;
            align-items: center;

            .title {
                align-self: center;
                color: #333;
                font-weight: 500;
                font-size: 16px;
                line-height: 22px;
                box-sizing: border-box;
                flex: 1;
            }
        }

        .aside-content {
            .main-item {
                width: 100%;
                padding: 0 14px;

                .item-box {
                    position: relative;
                    height: 40px;
                    padding: 0 26px 0 21px;
                    overflow: hidden;
                    white-space: nowrap;
                    display: flex;
                    align-items: center;

                    .title {
                        margin-right: 10px;
                        color: #333;
                        font-size: 14px;
                        line-height: 18px;
                        overflow: hidden;
                        text-overflow: ellipsis;
                        white-space: nowrap;
                    }
                }
            }
        }
    }


}
</style>