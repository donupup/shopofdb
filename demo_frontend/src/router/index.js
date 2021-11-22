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
    component: ()=>import('@/views/auth/Register')
  },
  {
    path: '/employ/manage',
    name: 'employ_manage',
    component: ()=>import('@/views/auth/Manage')
  },
  {
    path: '/good/manage',
    name: 'good_manage',
    component: ()=>import('@/views/good/goodManage')
  },




]

const router = new VueRouter({
  routes
})

export default router
