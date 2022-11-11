import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
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
      path: '/user/home',
      component: () => import('@/components/User/index'),
      children:[
        {
          path: '/user/chat',
          component: () => import('@/components/User/Chat/index')
        },
        {
          path: '/user/contact',
 
        },
        {
          path: '/user/addfriend',
 
        },
        {
          path: '/user/addgroup',
 
        },
      ]
    },
    {
      path: '/admin/home',
      component: () => import('@/components/Admin/index'),
      children:[
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
        },
        {
          path: '/admin/list',
          component: () => import('@/components/Admin/AdminList/index'),
        },
        {
          path: '/assests/avatar',
        }
      ]
    },
  ]
})
