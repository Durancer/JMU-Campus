import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
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
          component: () => import('@/components/publish.vue')
        },
        {
          path: '/detail/:postId',
          name: 'detail',
          component: () => import('@/views/detail/index.vue')
        }
      ]
    },
    {
      path: '/admin',
      name: 'admin',
      component: () => import('@/views/admin/index.vue')
    }
  ]
})

export default router
