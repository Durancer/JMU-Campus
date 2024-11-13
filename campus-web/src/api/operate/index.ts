import http from '../index'
// 发布是否公开
export function register() {
  return http.request({
    method: 'post',
    url: '/post/operate/private',

  })
}


