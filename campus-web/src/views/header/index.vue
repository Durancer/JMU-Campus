<template>
  <div>
    <header class="header">
      <div class="header_nav">
        <div>
          <RouterLink :to="{ name: 'index' }">
            <SvgIcon name="logo" size="3em"></SvgIcon>
          </RouterLink>
        </div>
        <div @click="$router.push({ name: 'admin' })">admin</div>
        <div>
          <input type="text" placeholder="搜索" class="searcher" />
        </div>
        <div class="header-right">
          <template v-if="!userStore.userInfo?.id">
            <div class="login">
              <RouterLink :to="{ name: 'login' }">登录</RouterLink>
            </div>
            <div class="registr">
              <RouterLink :to="{ name: 'register' }">注册</RouterLink>
            </div>
          </template>
          <template v-else>
            <div style="display: inline-flex; align-items: center">
              <el-dropdown>
                <!-- 失去了响应性 -->
                <el-avatar :src="userStore.userInfo?.avatarUrl"></el-avatar>
                <!--todo 为啥放到这里不行 -->
                <el-icon><arrow-down /></el-icon>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item @click="$router.push({ name: 'account' })"
                      >修改信息</el-dropdown-item
                    >
                    <el-dropdown-item
                      @click="
                        $router.push({ name: 'history', params: { userId: userStore.userInfo.id } })
                      "
                      >我的历史记录</el-dropdown-item
                    >
                    <el-dropdown-item @click="userStore.logoutFn">退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
              <div class="intro">
                <span>昵称: {{ userStore.userInfo?.nickname }}</span>
                <span>自我介绍: {{ userStore.userInfo?.introduce }}</span>
              </div>
            </div>
          </template>
          <div class="publish" @click="handlePublish">发布</div>
        </div>
      </div>
    </header>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore.ts'
import { ArrowDown } from '@element-plus/icons-vue'
import SvgIcon from '@/components/SvgIcon.vue'

const userStore = useUserStore()

const router = useRouter()
const handlePublish = () => {
  router.push({ name: 'publish' })
}
</script>

<style scoped lang="less">
.header {
  background-color: #fff;
  box-shadow: 0 0 0 0 transparent, 0 0 0 0 transparent, 0 1px 4px 0 rgba(0, 0, 0, 0.02),
    0 2px 12px 0 rgba(0, 0, 0, 0.04), 0 2px 6px 0 rgba(0, 0, 0, 0.02);
  .header_nav {
    max-width: 1122px;
    height: 56px;
    margin: auto;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .logo-img {
      padding: 10px;
      width: 40px;
    }

    .intro {
      display: flex;
      flex-direction: column;
      margin-left: 10px;
    }
    .publish {
      background: linear-gradient(135deg, #00dcc2, #00dc93);
      border: none;
      border-radius: 18px;
      color: #fff;
      cursor: pointer;
      display: flex;
      align-items: center;
      font-size: 14px;
      font-weight: 400;
      height: 30px;
      justify-content: center;
      line-height: 14px;
      width: 60px;
    }
    .header-right {
      display: flex;
      align-items: center;
      div {
        margin-right: 30px;
      }
    }
  }
}
</style>
