import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: ()=>import('@/views/Home'),
    meta: {
      title: '首页',
      requireAuth: false,
      showInbreadcrumb:false
    }
  },
  {
    path: '/login',
    name: 'Login',
    component: ()=>import('@/views/auth/Login'),
    meta: {
      title: '用户登录',
      requireAuth: false,
      showInbreadcrumb:false
    }
  },
  {
    path: '/employ/register',
    name: 'employ_register',
    component: ()=>import('@/views/auth/Register'),
    meta: {
      title: '用户注册',
      requireAuth: true,
      showInbreadcrumb:true
    }
  },
  {
    path: '/employ/manage',
    name: 'employ_manage',
    component: ()=>import('@/views/auth/Manage'),
    meta: {
      title: '用户管理',
      requireAuth: true,
      showInbreadcrumb:true
    }
  },
  {
    path: '/good/manage',
    name: 'good_manage',
    component: ()=>import('@/views/good/goodManage'),
    meta: {
      title: '货物管理',
      requireAuth: true,
      showInbreadcrumb:true
    }

  },
  {
    path: '/good/sale',
    name: 'good_sale',
    component: ()=>import('@/views/good/goodSale'),
    meta: {
      title: '销售界面',
      requireAuth: true,
      showInbreadcrumb:true
    }
  },
  {
    path: '/good/sale_sta',
    name: 'sale_sta',
    component: ()=>import('@/views/good/sale_sta'),
    meta: {
      title: '销售统计',
      requireAuth: true,
      showInbreadcrumb:true
    }
  },
  {
    path: '/statistic',
    name: 'statistic',
    component: ()=>import('@/views/statistic'),
    meta: {
      title: '统计',
      requireAuth: true,
      showInbreadcrumb:true
    }
  },
  {
    path: '/good/bill_sta',
    name: 'bill_sta',
    component: ()=>import('@/views/good/bill_sta'),
    meta: {
      title: '账单',
      requireAuth: true,
      showInbreadcrumb:true
    }
  },
  {
    path: "/404",
    name: '404',
    component: ()=>import('@/views/error/404'),
    meta: {
      title: '404',
      requireAuth: false,
      showInbreadcrumb:false
    }
  },
  {
    path: "*",
    redirect: "/404",
    hidden: true,
    meta: {
      title: 'else',
      requireAuth: false,
      showInbreadcrumb:false
    }
  },




]

const router = new VueRouter({
  routes
})

export default router
