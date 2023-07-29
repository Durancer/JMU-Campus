<template>
    <header class="list-header">
        <nav class="list-nav">
            <ul class="nav-list">
                <li class="nav-item">热门</li>
                <li class="nav-item">最新</li>
            </ul>
        </nav>
    </header>
</template>

<script setup lang="ts"></script>

<style scoped lang="less">
.list-header {
    padding: 12px 20px;
    border-bottom: 1px solid #e4e6eb;

    .list-nav {
        display: flex;
        justify-content: flex-start;

        .nav-list {
            display: flex;
            align-items: center;
            justify-content: space-between;
            line-height: 1;

            .nav-item {
                margin-left: -20px;
                padding: 0 20px;
                line-height: 12px;
                font-size: 12px;
                color: #515767;
                position: relative;
            }
        }
    }
}
</style>