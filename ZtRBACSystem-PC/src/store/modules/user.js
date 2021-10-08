import {getInfo, login, logout, refreshToken} from '@/api/login'
import {getToken, removeToken, setExpiresIn, setToken} from '@/utils/auth'

const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
    permissions: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_EXPIRES_IN: (state, time) => {
      state.expires_in = time
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    }
  },

  actions: {
    // 登录
    Login({
            commit
          }, userInfo) {
      console.log('登录信息commit:' + JSON.stringify(commit))
      const userName = userInfo.userName.trim()
      const userPwd = userInfo.userPwd
      const code = userInfo.code
      const uuid = userInfo.uuid
      return new Promise((resolve, reject) => {
        console.log('登录信息resolve:' + JSON.stringify(resolve))
        login(userName, userPwd, code, uuid).then(res => {
          console.log('登录成功:' + JSON.stringify(res))
          let zttoken = res.data
          setToken(zttoken)
          commit('SET_TOKEN', zttoken)
          // setExpiresIn(data.expires_in)
          // commit('SET_EXPIRES_IN', data.expires_in)
          //回到src/permission.js，然后调用GetInfo
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息 src/permission.js
    GetInfo({
              commit,
              state
            }) {
      return new Promise((resolve, reject) => {
        getInfo(state.token).then(res => {
          // console.log('获取用户信息GetInfo:' + JSON.stringify(res.data.user))
          // console.log('获取用户信息GetInfoRoles:' + JSON.stringify(res.data.roles))
          // console.log('获取用户信息GetInfoPermissions:' + JSON.stringify(res.data.permissions))
          const user = res.data.user
          const avatar = user.avatar == "" ? require("@/assets/images/profile.jpg") : user.avatar;
          if (res.data.roles && res.data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', res.data.roles)
            commit('SET_PERMISSIONS', res.data.permissions)
          } else {
            commit('SET_ROLES', ['ROLE_DEFAULT'])
          }
          commit('SET_NAME', user.userName)
          commit('SET_AVATAR', avatar)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 刷新token
    RefreshToken({
                   commit,
                   state
                 }) {
      return new Promise((resolve, reject) => {
        refreshToken(state.token).then(res => {
          setExpiresIn(res.data)
          commit('SET_EXPIRES_IN', res.data)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 退出系统
    LogOut({
             commit,
             state
           }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          resolve()
        }).catch(error => {
          reject(error)
        }).finally(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          commit('SET_PERMISSIONS', [])
          removeToken()
        })
      })
    },

    // 前端 登出
    FedLogOut({
                commit
              }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
