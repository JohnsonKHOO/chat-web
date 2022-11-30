// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from '@/App'
import router from '@/router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/css/global.css'
import * as echarts from 'echarts';

Vue.prototype.$echarts = echarts
const axios = require('axios')
Vue.prototype.$axios = axios
axios.defaults.baseURL = 'http://localhost:9000'

Vue.use(ElementUI)
Vue.config.productionTip = false


new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  render: h => h(App)
})
