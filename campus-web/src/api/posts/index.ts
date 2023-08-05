import request from '../index.ts'

interface getAllPostsParams {
  current: number
  size: number
}
// 分页获取所有帖子,获取的为审核通过的帖子
export function getAllPosts(params?: getAllPostsParams) {
  return request.get({
    url: '/post/list/all',
    params
  })
}

interface addPostParams {
  title: string
  content: string
  files?: string
  topic?: string
  type?: string
  cycle?: string
  options?: number
}
// 添加帖子
export function addPost(data: addPostParams) {
  return request.request({
    method: 'post',
    url: '/post/add',
    data: {
      ...data,
      topic: '测试话题1',
      type: '123',
      cycle: '7000'
    }
  })
}
