import request from '../index.ts'

interface getAllPostsParams {
  current: number
  size: number
}
// 分页获取所有帖子,获取的为审核通过的帖子
export function getAllPosts(params: getAllPostsParams) {
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
export function addPost(params: addPostParams) {
  return request.post({
    url: '/post/add',
    params
  })
}
