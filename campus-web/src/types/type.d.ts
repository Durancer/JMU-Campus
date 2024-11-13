declare namespace API {
  type loginForm = {
    username: string
    password: string
    uuid: string
    verifyCode: string
  }

  type menuForm = {
    menuId: undefined | number
    type: number
    menuName: string
    router: string
    viewPath: string
    icon: string
    keepalive: number
    isShow: number
    permission: string
    title?: string
    parentId: undefined | number
    children?: menuForm[]
  }
}

interface userinfo {
  avatarUrl:string
  createTime:string
  email:string
  id:number
  introduce:string  
  phone:string
  sex:number
  updateTime:string
  username: string
  nickname: string
}
