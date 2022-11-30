import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

let router = new Router({
  mode: 'history',
  routes: [{
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      component: () => import('@/components/Login')
    },
    {
      path: '/register',
      component: () => import('@/components/Register')
    },
    {
      path: '/admin/login',
      component: () => import('@/components/Admin/Login')
    },
    {
      path: '/user/home',
      component: () => import('@/components/User/index'),
      children: [{
          path: '/user',
          redirect: '/user/home'
        },
        {
          path: '/user/chat',
          component: () => import('@/components/User/Chat/index')
        },
        {
          path: '/user/info',
          component: () => import('@/components/User/Info/index')
        },
        {
          path: '/user/contact',
          component: () => import('@/components/User/Contact/index')
        },
        {
          path: '/user/group',
          component: () => import('@/components/User/Group/index')
        },
        {
          path: '/user/search',
          component: () => import('@/components/User/Search/SearchUser')
        },
        {
          path: '/group/search',
          component: () => import('@/components/User/Search/SearchGroup')
        },
        {
          path: '/user/checkpassword',
          component: () => import('@/components/User/Password/CheckPassword')
        },
        {
          path: '/user/updatepassword',
          component: () => import('@/components/User/Password/UpdatePassword')
        },
      ]
    },
    {
      path: '/admin/home',
      component: () => import('@/components/Admin/index'),
      children: [{
          path: '/admin',
          redirect: '/admin/home'
        },
        {
          path: '/admin/userlist',
          component: () => import('@/components/Admin/UserList/index'),
        },
        {
          path: '/admin/userstatistics',
          component: () => import('@/components/Admin/UserStatistics/index'),
        },
        {
          path: '/admin/grouplist',
          component: () => import('@/components/Admin/GroupList/index'),
        },
        {
          path: '/admin/list',
          component: () => import('@/components/Admin/AdminList/index'),
        },
        {
          path: '/assests/avatar',
          component: () => import('@/components/Admin/WaterFall/index'),
        }
      ]
    },
  ]
})

export default router

