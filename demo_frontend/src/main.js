import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// Buefy
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'
// ElementUI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/assets/app.css'
import './assets/plugins/font-awesome-4.7.0/css/font-awesome.min.css'
import format from 'date-fns/format'
//import '@/permission'
import relativeTime from 'dayjs/plugin/relativeTime';

// 国际化
import 'dayjs/locale/zh-cn'
const dayjs = require('dayjs');

import '@/permission'

import * as echarts from 'echarts';
Vue.prototype.$echarts = echarts;

// 相对时间插件
dayjs.extend(relativeTime)

dayjs.locale('zh-cn') // use locale globally
dayjs().locale('zh-cn').format() // use locale in a specific instance

Vue.prototype.dayjs = dayjs;//可以全局使用dayjs

Vue.filter('date', (date) => {
  return format(new Date(date), 'yyyy-MM-dd')
})

Vue.use(Buefy)
Vue.use(ElementUI);

Vue.config.productionTip = false

//printjs插件
import printjs from 'print-js'

import 'print-js/dist/print.css';

Vue.prototype.$print = printjs;

//高德插件

import VueAMap from 'vue-amap';
Vue.use(VueAMap);
VueAMap.initAMapApiLoader({
  // 高德key
  key: '7ce21834d080ec5fee6e4beb0e2b6fe9',
  // 插件集合 （插件按需引入）
  plugin: ['AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.Autocomplete',
    'AMap.MapType', 'AMap.PlaceSearch', 'AMap.Geolocation', 'AMap.Geocoder'],
  uiVersion:'1.1',//一定不要忘记了
  v:"1.4.15"
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
