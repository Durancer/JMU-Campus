<template>
    <div>
        <header class="header">
            <div class="header_nav">
                <div>
                    <img class="logo-img" src="@/assets/img/logo.jpg" alt="">
                </div>
                <div>
                    <input type="text" placeholder="搜索" class='searcher'>
                </div>
                <div class="login">
                    <div>登录</div>
                    <div>注册</div>
                </div>
            </div>
        </header>
    </div>
</template>

<script setup lang="ts">
</script>

<style scoped lang="less">
.header {
    .header_nav {
        max-width: 1122px;
        height: 100%;
        margin: auto;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .logo-img {
            padding: 10px;
            width: 40px;
        }

        .login {
            // margin-left: auto;
            display: flex;
        }
    }
}
</style>