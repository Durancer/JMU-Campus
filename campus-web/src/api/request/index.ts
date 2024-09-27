import axios from 'axios'
import type { AxiosInstance, AxiosRequestConfig } from 'axios'
import { ElMessage, ElLoading } from 'element-plus'
import { localCache } from '@/utils/cache'

let loadingInstance:any = null
let requestNum = 0

const addLoading = () => {
  // 增加loading 如果pending请求数量等于1，弹出loading, 防止重复弹出
  requestNum++
  if (requestNum == 1) {
    loadingInstance = ElLoading.service({
      text: '正在努力加载中....',
      background: 'rgba(0, 0, 0, 0)'
    })
  }
}

const cancelLoading = () => {
  // 取消loading 如果pending请求数量等于0，关闭loading
  requestNum--
  if (requestNum === 0) loadingInstance?.close()
}

(function () {
  // 环境的切换  todo: 类型问题
  if (process.env.NODE_ENV == 'development') {
    axios.defaults.baseURL = '/Apis'
  } else if (process.env.NODE_ENV == 'debug') {
    axios.defaults.baseURL = '/debug'
  } else if (process.env.NODE_ENV == 'production') {
    axios.defaults.baseURL = 'http://jmucampus.top/api'
  }
})()

export const createAxiosByinterceptors = (config?: AxiosRequestConfig): AxiosInstance => {
  const instance = axios.create({
    timeout: 5000, //超时配置
    // withCredentials: true,  //跨域携带cookie
    ...config // 自定义配置覆盖基本配置
  })

  // 添加请求拦截器
  instance.interceptors.request.use(
    function (config: any) {
      // 在发送请求之前做些什么
      const { loading = true } = config
      console.log('config:', config)
      // config.headers.Authorization = vm.$Cookies.get("vue_admin_token");
      if (loading) addLoading()
      const token = localCache.getCache('login')?.token
      if (config.headers && token) {
        // 类型缩小
        // config.headers.Authorization = 'Bearer ' + token
        config.headers['token'] = token || '0'
        config.headers['Content-Type'] =
          'multipart/form-data; boundary=----WebKitFormBoundarykJbrPyNSEAef7e5h'
      }
      return config
    },
    function (error) {
      // 对请求错误做些什么
      return Promise.reject(error)
    }
  )

  // 添加响应拦截器
  instance.interceptors.response.use(
    function (response) {
      // 对响应数据做点什么
      console.log('response:', response)
      const { loading = true, method } = response.config
      if (loading) cancelLoading()
      const { code, message } = response.data
      // config设置responseType为blob 处理文件下载
      if (response.data instanceof Blob) {
        // return downloadFile(response)
      } else {
        if (code <= 299 && code >= 200) {
          // if (method === 'post' && loading) {
          //   ElMessage({ message, type: 'success', duration: 1000 }) // post弹出消息提示
          // }
          return response.data
        } else {
          ElMessage.error(message)
          return Promise.reject(response.data)
        }
      }
    },
    function (error) {
      // 对响应错误做点什么
      console.log('error-response:', error.response)
      console.log('error-config:', error.config)
      console.log('error-request:', error.request)
      const { loading = true } = error.config
      if (loading) cancelLoading()
      ElMessage.error(error?.response?.data?.message || '服务端异常')
      return Promise.reject(error)
    }
  )
  return instance
}
