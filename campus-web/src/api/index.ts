import { LOGIN_TOKEN } from '@/global/constants'
import { localCache } from '@/utils/cache'
// import { BASE_URL, TIME_OUT } from './config'
import Request from './request'

const request = new Request({
  baseURL: '/Api',
  // baseURL: 'http://60.204.139.75/api',
  interceptors: {
    requestSuccessFn: (config) => {
      /**/
      // 每一个请求都自动携带token
      const token = localCache.getCache('login').token
      // const userStore = useUserStore()
      // const token = userStore.token
      // console.log('token', token.value)
      // config.headers['Content-Type'] = 'multipart/form-data'
      if (config.headers && token) {
        // 类型缩小
        // config.headers.Authorization = 'Bearer ' + token
        config.headers['token'] = token || '0'
        // if (config.method === 'post' || config.method === 'put') {
        //   config.data['token'] = token
        // }
        config.headers['Content-Type'] =
          'multipart/form-data; boundary=----WebKitFormBoundarykJbrPyNSEAef7e5h'
      }
      return config
    }
  }
})

export default request
