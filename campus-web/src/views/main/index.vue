<template>
    <main class="main-container">
        <div class="main">
            <div class="left-container" :class="{ 'left-top': isHidden }">
                <leftCpn :leftTabsList="leftTabsList" />
            </div>
            <right-cpn />
        </div>
    </main>
</template>

<script setup lang="ts">
import { reactive, ref, watch } from 'vue'


import leftCpn from './leftCpn/index.vue'
import rightCpn from './rightCpn/index.vue'

const { isHidden } = defineProps({
    isHidden: Boolean
})

const leftTabsList = reactive([
    { lable: '推荐', value: 1 },
    { lable: '热榜', value: 2 },
    { lable: 'tab', value: 3 },
])

</script>

<style scoped lang="less">
.main-container {
    position: relative;
    max-width: 1200px;
    margin: auto;
    width: 100%;

    .main {
        margin-top: 8px;
        display: flex;
        flex-direction: row;

        margin-top: 18px;

        .left-top.left-container {
            top: 20px;
            max-height: calc(100vh - 40px);
        }

        &>.left-container {
            position: sticky;
            top: 80px;
        }

        .left-container {
            width: 180px;
            margin-right: 20px;
            height: fit-content;
            border-radius: 4px;
            background-color: #fff;
            max-height: calc(100vh - 101px);
            overflow-x: hidden;


        }
    }

}
</style>