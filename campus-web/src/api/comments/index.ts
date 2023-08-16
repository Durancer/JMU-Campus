import http from '../index.ts'

interface commentParams {
  postId: number
  content: string
  type: string
  rootId?: number
  toUserId?: number
}
// 添加评论
export function addComment(data: commentParams) {
  return http.request({
    method: 'post',
    url: '/comment/add',
    data
  })
}
