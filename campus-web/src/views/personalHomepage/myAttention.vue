<template>
    <div class="myAttention">
        <div class="myAttention-title">关注的人</div>
        <div class="myAttention-list">
            <div class="myAttention-list-title">全部关注</div>
            <div class="myAttention-list-item">
                <div class="left-avatar"></div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { useUserStore } from '@/stores/userStore.ts'
import { getUserFollowList } from '@/api/comments'
import { useRoute } from 'vue-router';
import { onMounted } from 'vue';
const userStore = useUserStore()
const route = useRoute()

const getUserFollowLists = async () => {
    const res = await getUserFollowList({
        userId: Number(route.query.id),
        current: 1,
        size: 10
    })
}
onMounted(() => {
    getUserFollowLists()
})

</script>

<style lang="less" scoped>
.myAttention {
    min-width: 720px;
    margin: 0 10px;

    .myAttention-title {
        height: 44px;
        background-color: #fff;
        line-height: 44px;
        padding-left: 15px;
        color: #ff8200;
    }

    .myAttention-list {
        background-color: #fff;
        margin-top: 10px;

        .myAttention-list-title {
            height: 44px;
            padding: 0  10px;
            line-height: 44px;
        }
    }
}
</style>