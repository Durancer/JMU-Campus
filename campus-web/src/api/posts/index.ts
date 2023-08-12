import http from '../index.ts'

/**
 *
 {id: 1, userInfo: {id: 1, nickname: "lucy",…}, title: "帖子标题", content: "测试", viewNum: 1,…}
    id: 1
    userInfo: {id: 1, nickname: "lucy",…}
    title: "帖子标题"
    content: "测试"
    viewNum: 1
    imgList: [{id: 3, parentId: 1, imgUrl: "http://60.204.139.75/image/e08e9fa091b94dcab6137e58280e3ed2.png"}]
    voteMessage: null
    userLikeBOList: [{id: 1, nickname: "lucy",…},…]
    createTime: "2023-02-04T13:10:35.000+00:00"
 */

interface getAllPostsParams {
  current: number
  size: number
}
// 分页获取所有帖子,获取的为审核通过的帖子
export function getAllPosts(params?: getAllPostsParams) {
  return http.request({
    url: '/post/list/all',
    params
  })
}

// 分页获取我的帖子
export function getSelfPost() {
  return http.request({
    url: '/post/list/user/self'
  })
}

// 分页获取用户帖子
export function getUserPost(params: string) {
  return http.request({
    url: '/post/list/user',
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
  return http.request({
    method: 'post',
    url: '/post/add',
    data: {
      ...data
      // topic: '测试话题1',
      // type: 'radio',
      // cycle: 'day',
      // options: ['1', '2', '3']
    },
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 获取帖子详细信息
export function getPostDetail(postId: string) {
  return http.request({
    url: '/post/detail',
    params: {
      postId
    }
  })
}

// 审核帖子
export function checkPost(postId: number, decision: number) {
  return http.request({
    method: 'post',
    url: '/post/check',
    data: {
      postId,
      decision
    },
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
