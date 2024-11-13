<template>
    <div class="myFans">
        <div class="myFans-title">我的粉丝</div>
    </div>
</template>

<script setup lang="ts">
import { useUserStore } from '@/stores/userStore.ts'
import { getUserFollowGansList } from '@/api/comments'
import { useRoute } from 'vue-router';
import { onMounted } from 'vue';
const userStore = useUserStore()
const route = useRoute()

const getUserFollowGansLists = async () => {
    const res = await getUserFollowGansList({
        userId: Number(route.query.id),
        current: 1,
        size: 10
    })
}
onMounted(() => {
    getUserFollowGansLists()
})

</script>

<style lang="less" scoped>
.myFans {
    min-width: 720px;
    margin: 0 10px;

    .myFans-title {
        height: 44px;
        background-color: #fff;
        line-height: 44px;
        padding-left: 15px;
        color: #ff8200;
    }
}
</style>