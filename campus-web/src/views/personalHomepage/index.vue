<template>
    <div class="homepageWrap">
        <div class="personalHomepage">
            <!-- 侧边菜单 -->
            <div class="asideMenu">
                <div class="asideMenuTitle">个人主页</div>
                <div class="asideMenuItem" @click="router.push('/personalHomepage/personalInformation')">个人信息</div>
                <div class="asideMenuItem">置顶帖子</div>
                <div class="asideMenuItem">我的帖子</div>
                <div class="asideMenuItem">我的评论</div>
                <div class="asideMenuItem"
                    @click="router.push({ path: '/personalHomepage/myAttention', query: { id: route.query.id } })">我的关注
                </div>
                <div class="asideMenuItem"
                    @click="router.push({ path: '/personalHomepage/myFans', query: { id: route.query.id } })">我的粉丝
                </div>
            </div>
            <RouterView />
        </div>
    </div>
</template>

<script setup lang="ts">
import { RouterView, useRoute } from 'vue-router';
import { useRouter } from 'vue-router';
const router = useRouter();
const route = useRoute()




</script>

<style lang="less" scoped>
.homepageWrap {
    display: flex;
    justify-content: center;

    .personalHomepage {
        min-width: 1200px;
        display: flex;

        .asideMenu {
            width: 180px;
            background-color: #fff;
            padding: 5px 5px;

            .asideMenuTitle {
                font-size: 20px;
            }

            .asideMenuItem {
                padding: 8px 10px;

                &:hover {
                    background-color: #eee;
                    cursor: pointer;
                }
            }
        }
    }
}
</style>