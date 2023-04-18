// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'//自动扫描里面的路由配置
import VueRouter from "vue-router";
import store from "./store";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css';
import axios from "axios";
Vue.prototype.$axios=axios;
Vue.prototype.$httpUrl='http://localhost:8081';
Vue.config.productionTip = false

Vue.use(ElementUI,{size:'small'});
Vue.use(VueRouter)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
