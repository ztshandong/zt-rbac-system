import router from './router'
import store from './store'
import {Message} from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import {getToken} from '@/utils/auth'

NProgress.configure({
  showSpinner: false
})

const whiteList = ['/login', '/auth-redirect', '/bind', '/register']

router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    /* has token*/
    if (to.path === '/login') {
      next({
        path: '/'
      })
      NProgress.done()
    } else {
      if (store.getters.roles.length === 0) {
        // 判断当前用户是否已拉取完user_info信息
        //src/store/modules/user.js
        store.dispatch('GetInfo').then(res => {
          // 拉取user_info
          const roles = res.data.roles
          // console.log('获取userRoles:'+JSON.stringify(roles))
          //src/store/modules/permission.js
          store.dispatch('GenerateRoutes', {
            roles
          }).then(accessRoutes => {
            // console.log('获取accessRoutes:'+JSON.stringify(accessRoutes))
            // console.log('根据roles权限生成可访问的路由表:'+JSON.stringify(accessRoutes))
            // 根据roles权限生成可访问的路由表
            // var routesChildren = [{
            //   path: 'index',
            //   component: (resolve) => require(['@/views/index'], resolve),
            //   name: '首页',
            //   meta: {
            //     title: '首页',
            //     icon: 'dashboard',
            //     cacheFlag: false,
            //     affix: true
            //   }
            // }]
            // routesChildren.push(accessRoutes)

            // var routeIndex = {
            //   path: '',
            //   component: Layout,
            //   redirect: 'index',
            //   children: routesChildren
            // }

            // var dyRoutes = [routeIndex]

            // console.log('获取dyRoutes:' + JSON.stringify(dyRoutes))

            // router.addRoutes(dyRoutes)

            // console.log('现有路由')
            // console.log(router.options.routes[4].children)
            // accessRoutes.forEach((el,index)=>{
            //   el.hidden = true
            //   router.options.routes[4].children.push(el)
            // })
            // router.addRoutes(router.options.routes[4].children)

            router.addRoutes(accessRoutes) // 动态添加可访问路由表
            next({
              ...to,
              replace: true
            }) // hack方法 确保addRoutes已完成
          })
        }).catch(err => {
          console.log('GetInfoErr:' + JSON.stringify(err))
          store.dispatch('LogOut').then(() => {
            Message.error(err)
            next({
              path: '/'
            })
          })
        })
      } else {
        next()
      }
    }
  } else {
    // 没有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      next(`/login?redirect=${to.fullPath}`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
