/**
 * 操作权限处理
 * 自定义按钮权限
 * v-hasPermi
 * Copyright (c) 2019 zhuorui
 */

import store from '@/store'

export default {
  inserted(el, binding, vnode) {
    // console.log('hasPermi.js:el:' + JSON.stringify(el))
    // console.log('hasPermi.js:binding:' + JSON.stringify(binding))
    // console.log('hasPermi.js:vnode:' + JSON.stringify(vnode))
    const {
      value
    } = binding
    const all_permission = "*:*:*";
    const permissions = store.getters && store.getters.permissions
    // console.log(store.getters.permissions)
    // console.log(store.getters)

    if (value && value instanceof Array && value.length > 0) {
      const permissionFlag = value

      const hasPermissions = permissions.some(permission => {
        return all_permission === permission || permissionFlag.includes(permission)
      })

      if (!hasPermissions) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error(`请设置操作权限标签值`)
    }
  }
}
