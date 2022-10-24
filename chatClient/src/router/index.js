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
      path: '/chat',
      component: () => import('@/components/chat')
    },
    {
      path: '/admin/home',
      component: () => import('@/components/admin/index'),
      children:[
        {
          path: '/admin/userlist',
          component: () => import('@/components/admin/UserList/index'),
        },
        {
          path: '/admin/userstatistics',
          component: () => import('@/components/admin/UserStatistics/index'),
        },
        {
          path: '/admin/grouplist',
        },
        {
          path: '/admin/manage',
        },
        {
          path: '/assests/avatar',
        }
      ]
    },
  ]
})
