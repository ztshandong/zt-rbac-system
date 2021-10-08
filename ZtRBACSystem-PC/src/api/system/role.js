import request from '@/utils/request'

// 查询角色列表
export function listRole(query) {
  // console.log(JSON.stringify(query))
  return request({
    url: '/usercenter/ztRoleInfo/select',
    method: 'post',
    data: query
  })
}

// 查看当前角色配置了哪些菜单
export function getMenusByRoleCode(roleCode) {
  const data = {
    roleCode
  }
  return request({
    url: '/usercenter/ztRoleMenuInfo/select/',
    method: 'post',
    data: data
  })
}

// 查询角色详细
export function getRole(id) {
  return request({
    url: '/usercenter/ztRoleInfo/selectid/' + id,
    method: 'get'
  })
}

// 新增角色
export function addRole(data) {
  return request({
    url: '/usercenter/ztRoleInfo',
    method: 'post',
    data: data
  })
}

// 修改角色
export function updateRole(data) {
  return request({
    url: '/usercenter/ztRoleInfo',
    method: 'put',
    data: data
  })
}

// 角色数据权限
export function dataScope(data) {
  return request({
    url: '/usercenter/ztRoleInfo/dataScope',
    method: 'put',
    data: data
  })
}

// 角色状态修改
export function changeRoleStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/usercenter/ztRoleInfo/changeStatus',
    method: 'put',
    data: data
  })
}

// 删除角色
export function delRole(id) {
  return request({
    url: '/usercenter/ztRoleInfo/' + id,
    method: 'delete'
  })
}
