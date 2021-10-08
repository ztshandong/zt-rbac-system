import request from '@/utils/request'
import api from './index.js'

// 登录方法
export function login(userName, userPwd, code, uuid) {
  var data = {
    userName,
    userPwd
  }
  return api.post('/ZtIndex/login', data)
}

// export function login(userName, userPwd, code, uuid) {
//   return request({
//     url: '/ZtIndex/login',
//     method: 'post',
//     data: {userName, userPwd, code, uuid}
//   })
// }


// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/ZtIndex/getUserInfoAfterLogin',
    method: 'post'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/ZtIndex/logout',
    method: 'post'
  })
}
