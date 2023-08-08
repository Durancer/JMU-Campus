import { ref, reactive } from 'vue'
import { defineStore, storeToRefs } from 'pinia'
import router from '@/router'
import 'element-plus/es/components/message/style/css'
import { localCache } from '@/utils/cache.ts'
import { LOGIN_TOKEN } from '@/global/constants'
import { failMessage, sucMessage } from '@/utils/common.ts'
import { login } from '@/api/user/index.ts'

export const useUserStore = defineStore('login', () => {
  const userInfo = ref({})
  async function loginFn(data: any) {
    const res = await login(data)
    if (res.status) {
      localCache.setCache(LOGIN_TOKEN, res.data.userInfo.token)
      userInfo.value = res.data.userInfo
      sucMessage('登录成功')
    } else {
      failMessage('登录失败')
    }
  }
  return { userInfo, loginFn }
})
