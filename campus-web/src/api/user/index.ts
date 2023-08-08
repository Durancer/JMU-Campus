import http from '../index'

interface user {
  username: string
  password: string
}
// 登录
export function login(userinfo: user) {
  return http.request({
    url: '/user/login',
    params: {
      ...userinfo
    }
  })
}

interface userinfo extends user {
  idencode: string
  email: string
  nickname: string
  introduce?: string
  sex?: number
  phone?: string
}

// 注册
export function register(data: userinfo) {
  return http.request({
    method: 'post',
    url: '/user/register',
    data: { data }
  })
}

// 发送请求码
export function requestCode(email: string) {
  return http.post({
    url: '/user/send/code',
    method: 'post',
    data: {
      email
    }
  })
}
