<template>
  <div>
    <header class="header">
      <div class="header_nav">
        <div>
          <RouterLink :to="{ name: 'index' }">
            <img class="logo-img" src="@/assets/img/logo.jpg" alt="" />
          </RouterLink>
        </div>
        <div @click="$router.push({ name: 'admin' })">admin</div>
        <div>
          <input type="text" placeholder="搜索" class="searcher" />
        </div>
        <template v-if="!userStore.userInfo?.id">
          <div class="login">
            <template v-if="userStore.userInfo">
              <button type="button" class="btn" @click="handleBtn('loginBtn')">登录</button>
            </template>
            <div class="line"></div>
            <button type="button" class="btn" @click="handleBtn('register')">注册</button>
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
    </header>

    <el-dialog v-model="loginFlag" title="登录" width="320px" center>
      <login @close="close('login')" />
    </el-dialog>

    <el-dialog v-model="registerFlag" title="注册" width="400px" center>
      <register @close="close('register')" />
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import login from './cpn/login.vue'
import register from './cpn/register.vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore.ts'
import { ArrowDown } from '@element-plus/icons-vue'

const userStore = useUserStore()
const loginFormRef = ref<FormInstance>()
const registerFormRef = ref<FormInstance>()

const loginFlag = ref(false)
const registerFlag = ref(false)
const handleBtn = (str: string) => {
  if (str === 'loginBtn') {
    loginFormRef.value?.resetFields()
    loginFlag.value = true
  } else if (str === 'register') {
    registerFormRef.value?.resetFields()
    registerFlag.value = true
  }
}

// const loginStore = useLoginStore()
const router = useRouter()
const handlePublish = () => {
  router.push({ name: 'publish' })
}

const close = (str: string) => {
  if (str === 'register') {
    registerFlag.value = false
  } else if (str === 'login') {
    console.log('44')

    loginFlag.value = false
  }
}
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
      position: relative;
      color: #007fff;
      padding: 0;
      cursor: default;
      font-size: 14px;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      background: rgba(30, 128, 255, 0.05);
      border: 1px solid rgba(30, 128, 255, 0.3);
      border-radius: 4px;
      line-height: 26px;
      font-weight: 400;
      overflow: hidden;

      .btn {
        outline: none;
        border: none;
        padding: 3px 12px;
        color: #007fff;
        line-height: 1.9rem;
        font-size: 14px;
        font-weight: 400;
        height: 36px;
        overflow: hidden;
        display: flex;
        align-items: center;
        text-decoration: none;
        cursor: pointer;
      }

      .line {
        background-color: #abcdff;
        height: 12px;
        width: 1px;
      }
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
  }
}
</style>
