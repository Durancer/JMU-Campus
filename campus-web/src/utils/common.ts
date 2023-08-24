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

// 处理时间  2023-02-04T13:10:35.000+00:00
export function handleTime(dateString: string) {
  const date = new Date(dateString)
  const formattedDate = `${date.getFullYear()}-${(date.getMonth() + 1)
    .toString()
    .padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`
  const formattedTime = `${date.getHours().toString().padStart(2, '0')}:${date
    .getMinutes()
    .toString()
    .padStart(2, '0')}:${date.getSeconds().toString().padStart(2, '0')}`
  const formattedDateTime = `${formattedDate} ${formattedTime}`
  return formattedDateTime
}
