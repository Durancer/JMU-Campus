import http from '../index.ts'

interface getPostsParams {
  current: number
  size: number
}
// 分页获取所有帖子,获取的为审核通过的帖子
export function getAllPosts(params?: getPostsParams) {
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
    data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 获取帖子详细信息
export function getPostDetail(postId: string, userId?: number) {
  return http.request({
    url: '/post/detail',
    params: {
      postId
    },
    headers: {
      userId
    }
  })
}

// 获取需要审核的帖子
export function getNotCheckedPosts(params?: getPostsParams) {
  return http.request({
    url: '/post/status/list',
    params
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

// 删除帖子
export function deletePost(postId: string) {
  return http.request({
    method: 'post',
    url: '/post/delete',
    data: {
      postId
    }
  })
}
interface voteParams {
  postId: number
  voteId: number
  optionIds: number | number[]
}
// 用户投票
export function vote(data: voteParams, userId: number) {
  return http.request({
    url: '/post/vote/record/add',
    method: 'post',
    data,
    headers: {
      userId
    }
  })
}
// 删除投票
export function deleteVote(voteId: number) {
  return http.request({
    url: '/post/vote/delete',
    method: 'post',
    data: {
      voteId
    }
  })
}
// 点赞 | 取消点赞用户帖子
export function like(postId: number) {
  return http.request({
    url: '/post/operate/like',
    method: 'post',
    data: {
      postId
    }
  })
}

// 查看所有话题
export function getAllTopic() {
  return http.request({
    url: 'post/topic/list'
  })
}

// 根据话题区别帖子
export function getTopicPost(topicName: string) {
  return http.request({
    url: '/post/topic/list',
    params: {
      name: topicName
    }
  })
}
