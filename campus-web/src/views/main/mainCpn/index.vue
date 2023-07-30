<template>
    <div>
        <div class="main-container">
            <div class="main-content">
                <header-cpn />
                <entry-list :essay="essay" />
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import headerCpn from './cpn/headerCpn.vue'
import entryList from './cpn/entryList.vue'
// import asideCpn from './cpn/asideCpn.vue'

const essay = reactive([
    {
        title: '集大校园1',
        text: '正文部分正文部分正文部分正文部分正文部分正文部分正文部分正文部分正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分',
        id: 1
    },
    {
        title: '集大校园222',
        text: '正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分',
        id: 2
    }, {
        title: '集大校园3333',
        text: '正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分 正文部分',
        id: 3
    },
    {
        title: '测试',
        text: '测试',
        id: 4
    },
    {
        title: '测试',
        text: '测试',
        id: 5
    },
    {
        title: '测试',
        text: '测试',
        id: 6
    },
    {
        title: '测试',
        text: '测试',
        id: 7
    },
    {
        title: '测试',
        text: '测试',
        id: 8
    },
    {
        title: '测试',
        text: '测试',
        id: 8
    },
    {
        title: '测试',
        text: '测试',
        id: 9
    },
    {
        title: '测试',
        text: '测试',
        id: 10
    },
    {
        title: '测试',
        text: '测试',
        id: 11
    },
    {
        title: '测试',
        text: '测试',
        id: 12
    },
    {
        title: '测试',
        text: '测试',
        id: 13
    },
])
</script>

<style scoped lang="less">
.main-container {
    // background-color: #fff;
    border-radius: 4px;
    display: flex;
    justify-content: space-between;

    .main-content {
        // margin-right: 240px;
        border-radius: 2px;
        width: 720px;
        position: relative;
        background-color: #fff;
        // justify-content: space-between;
    }
}
</style>