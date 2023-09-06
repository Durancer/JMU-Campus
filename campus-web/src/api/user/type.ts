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

// 登录返回的信息类型
export interface loginReturnInfo {
  userInfo: tempUser
  token: string
}
