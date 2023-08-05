import { localCache } from './cache'
import { ElMessage } from 'element-plus'
// import { getCurrentInstance } from 'vue'
// 判断是否登录
export function isLogin() {
  return localCache.getCache('token')
}

// 成功的消息提示
export function sucMessage(message: string) {
  ElMessage({
    message,
    type: 'success'
  })
}

// 失败的消息提示
export function failMessage(message: string) {
  return ElMessage({
    message,
    type: 'error'
  })
}
