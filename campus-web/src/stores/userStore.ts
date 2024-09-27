import { ref, reactive, computed } from 'vue'
import { defineStore, storeToRefs } from 'pinia'
import router from '@/router'
import 'element-plus/es/components/message/style/css'
import { failMessage, sucMessage } from '@/utils/common.ts'
import { login, updateUserinfo, getUserDetail } from '@/api/user/index.ts'


export const useUserStore = defineStore(
  'login',
  () => {
    const userInfo = ref({})
    const token = ref('')
    async function loginFn(data: any) {
      const res = await login(data)
      console.log('data', res)
      if (res.status) {
        // localCache.setCache(LOGIN_TOKEN, res.data.userInfo.token)
        token.value = res.data.token
        userInfo.value = res.data.userInfo
        sucMessage('登录成功')
        router.push({ name: 'index' })
      }
    }
    async function getUserDetailFn(userId: number) {
      const res = await getUserDetail(userId)
      if (res.status) {
        userInfo.value = res.data
      }
    }
    async function updateUserinfoFn(data: any) {
      const res = await updateUserinfo(data)
      if (res.status) {
        getUserDetailFn(userInfo.value.id)
        sucMessage('更新成功')
      }
    }
    async function logoutFn() {
      userInfo.value = {}
      token.value = ''
    }
    return { userInfo, token, loginFn, updateUserinfoFn, getUserDetailFn, logoutFn }
  },
  {
    persist: true
  }
)
