import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: ()=>import('@/views/Home')
  },
  {
    path: '/login',
    name: 'Login',
    component: ()=>import('@/views/auth/Login')
  },
  {
    path: '/employ/register',
    name: 'employ_register',
    component: ()=>import('@/views/auth/Register'),
    meta: {
      title: '用户注册',
      requireAuth: true
    }
  },
  {
    path: '/employ/manage',
    name: 'employ_manage',
    component: ()=>import('@/views/auth/Manage'),
    meta: {
      title: '用户管理',
      requireAuth: true
    }
  },
  {
    path: '/good/manage',
    name: 'good_manage',
    component: ()=>import('@/views/good/goodManage'),
    meta: {
      title: '货物管理',
      requireAuth: true
    }

  },
  {
    path: "/404",
    name: '404',
    component: ()=>import('@/views/error/404')
  },
  {
    path: "*",
    redirect: "/404",
    hidden: true,
  },




]

const router = new VueRouter({
  routes
})

export default router
