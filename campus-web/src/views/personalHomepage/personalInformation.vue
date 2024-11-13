<template>
    <!-- 主体内容 -->
    <div class="homepageMain">
        <!-- 背景图片 -->
        <div class="bacgroundImage"></div>
        <div class="personalInformation">
            <!-- 头像组件不能用 ？？？？ -->
            <!-- <el-avatar  :size="120"  :url="userStore.userInfo?.avatarUrl"/> -->
            <img class="avatar" :src="userStore.userInfo?.avatarUrl">
            <span class="nickname">{{ userStore.userInfo?.nickname }}</span>
        </div>
    </div>
</template>

<script setup lang="ts">
import { useUserStore } from '@/stores/userStore.ts'
const userStore = useUserStore()




</script>

<style lang="less" scoped>
.homepageMain {
    min-width: 720px;
    background-color: #fff;
    margin: 0 10px;

    .bacgroundImage {
        background-image: url('@/assets/img/testBacImg.jpg');
        height: 361px;
        background-repeat: no-repeat;
        background-size: 100% 100%;
    }

    .personalInformation {
        margin-top: 20px;

        .avatar {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            position: relative;
            top: -50px;
            margin: 0 20px;
        }

        .nickname {
            font-weight: bolder;
            font-size: 20px;
        }
    }
}
</style>