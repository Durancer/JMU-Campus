// 登录用
export interface user {
  username: string
  password: string
}

// 注册用
export interface userinfo extends user {
  idencode: number
  email: string
  nickname: string
  introduce?: string
  sex?: number
  phone?: string
}

// 更新用户信息用
export interface updateInfo {
  nickname?: string
  introduce?: string
  sex?: number
  phone?: string
}

interface tempUser {
  id: number
  username: string
  nickname: string
  avatarUrl: string
  email: string
  sex: number
  phone: null | string
  createTime: string
}
/*
userInfo: {
  id: 10
  username: "简单点"
  nickname: "simple"
  avatarUrl: "http://60.204.139.75/image/default.jpg"
  introduce: "ta还没有个人介绍哦！"
  email: "it_wangyu_1999@163.com"
  sex: 0
  phone: null
  createTime: "2023-08-10T11:10:28.000+00:00"
},
token: 'abcde'
*/
// 登录返回的信息类型
export interface loginReturnInfo {
  userInfo: tempUser
  token: string
}
