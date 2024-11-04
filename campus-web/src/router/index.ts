import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/posts',
      name: 'PostManagement',
      component: PostManagement
    },
    {
      path: '/posts/:id/edit',
      name: 'EditPost',
      component: EditPost
    },
    {
      path: '/',
      redirect: '/index',
      component: () => import('../views/index.vue'),
      children: [
        {
          path: '/index',
          name: 'index',
          component: () => import('@/views/main/index.vue')
        },
        {
          path: '/publish',
          name: 'publish',
          component: () => import('@/views/publish/index.vue')
        },
        {
          path: '/detail/:postId',
          name: 'detail',
          component: () => import('@/views/detail/index.vue')
        },
        {
          path: '/account',
          name: 'account',
          component: () => import('@/views/account/index.vue')
        },
        {
          path: '/history/:userId',
          name: 'history',
          component: () => import('@/views/history/index.vue')
        }
      ]
    },
    {
      path: '/admin',
      name: 'admin',
      component: () => import('@/views/admin/index.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/login/index.vue')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/register/index.vue')
    }
  ]
})
// todo : 路由鉴权
export default router
