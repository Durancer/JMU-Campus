import http from '../index'
import type { user, userinfo, updateInfo } from './type.ts'

// 登录
export function login(params: user) {
  return http.request({
    url: '/user/login',
    params
  })
}

// 注册
export function register(data: userinfo) {
  return http.request({
    method: 'post',
    url: '/user/register',
    data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 发送请求码
export function requestCode(email: string) {
  return http.request({
    url: '/user/send/code',
    method: 'post',
    data: {
      email: email
    },
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 获取用户信息
export function getUserDetail(userId: number) {
  return http.request({
    url: '/user/detail',
    params: {
      userId
    }
  })
}

// 修改用户头像（限jpg、jpeg、png、webp）
export function updateAvatar(data: File) {
  return http.request({
    method: 'post',
    url: '/user/person/update/avatar',
    data
  })
}

// 更新用户信息
export function updateUserinfo(data: updateInfo) {
  return http.request({
    method: 'post',
    url: '/user/person/update/userInfo',
    data
  })
}
