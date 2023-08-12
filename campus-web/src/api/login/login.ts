import hyRequest from '../index'

export function login(userinfo: any) {
  return hyRequest.get({
    url: `/user/login?password=${userinfo.password}&username=${userinfo.username}`
  })
}
