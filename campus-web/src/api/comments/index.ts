import http from '../index.ts'

interface commentParams {
  postId: number
  content: string
  type: string
  rootId?: number
  toUserId?: number
}
interface UserFollowList {
  userId: number
  current?: number
  size?: number
}
// 添加评论
export function addComment(data: commentParams) {
  return http.request({
    method: 'post',
    url: '/comment/add',
    data
  })
}
// 点赞 | 取消点赞用户评论
export function likeComment(commentId: number, userId: number) {
  return http.request({
    url: '/comment/like',
    method: 'post',
    data: {
      commentId
    },
    headers: {
      userId
    }
  })
}

// 删除评论
export function deleteComment(commentId: number) {
  return http.request({
    method: 'post',
    url: '/comment/delete',
    data: {
      commentId
    }
  })
}
// 查询帖子评论
export function getComments(commentId: number) {
  return http.request({
    method: 'post',
    url: '/comment/post/list',
    data: {
      commentId
    }
  })
}
// 获取用户的评论
export function getUserComments(postId: number) {
  return http.request({
    method: 'get',
    url: '/comment/post/list',
    params: {
      postId
    }
  })
}
// 关注 | 取消关注用户
export function postUserFollow(followId: number) {
  return http.request({
    method: 'post',
    url: '/user/follow',
    data: {
      followId
    }
  })
}
// 获取用户关注列表
export function getUserFollowList(params: UserFollowList) {
  return http.request({
    method: 'get',
    url: '/user/follow/list',
    params
  })
}
// 获取用户关注列表
export function getUserFollowGansList(params: UserFollowList) {
  return http.request({
    method: 'get',
    url: '/user/follow/fans/list',
    params
  })
}
