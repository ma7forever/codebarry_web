import Vue from 'vue'
import Router from 'vue-router'
import HomeIndex from '@/views/home'
import CreateCenterHome from '@/views/CreateCenterHome'

import store from '../store'
Vue.use(Router)

export const constantRouterMap = [
  {
    path: '/',
    component: HomeIndex,
    children: [
      { path: '/', component: () => import('@/views/index') },
      { path: '/about', component: () => import('@/views/about') },
      { path: '/life', component: () => import('@/views/life') },
      { path: '/list', component: () => import('@/views/list') },
      { path: '/sort', component: () => import('@/views/sort') },
      { path: '/share', component: () => import('@/views/share') },
      { path: '/subject', component: () => import('@/views/subject') },
      { path: '/classify', component: () => import('@/views/classify') },
      { path: '/tag', component: () => import('@/views/tag') },
      { path: '/time', component: () => import('@/views/time') },
      { path: '/info', component: () => import('@/views/info') },
      { path: '/messageBoard', component: () => import('@/views/messageBoard') },
      { path: '/videoShow', component: () => import('@/views/videoShowAL') },
      { path: '/anime', component: () => import('@/views/anime') },
  
      { path: '/newestVideo', component: () => import('@/views/newestVideo') },
      { path: '/createCenter',

  component:  CreateCenterHome,
  children: [
    {  path: '/createCenter',component: () => import('@/views/createCenterIndex')  } ,
    {  path: '/upload',component: () => import('@/views/uploadAuth')  } ,
      { path: '/myVideo',component: () => import('@/views/myVideo') } ,
      { path: '/auditFail',component: () => import('@/views/auditFail') } ,
      { path: '/publishVideo',component: () => import('@/views/publishVideo') } ,
      { path: '/auditing',component: () => import('@/views/auditing') } ,
  ]

  },
    ]
  },
  
  { path: '/404', component: () => import('@/views/404') },
  { path: '/500', component: () => import('@/views/500') },
  { path: '/502', component: () => import('@/views/502') },
  { path: '/*', component: () => import('@/views/404') }

]

const router = new Router({
  routes: constantRouterMap
})

// router.beforeEach((to, from, next) => {
//   console.log("获取路径", to, from)
//   console.log("------------")
//   console.log(store.state.app.webNavbarList)
//   next()
// })

export default router
