import Vue from 'vue';
import App from './App.vue';
import router from './router';
import axios from 'axios';

Vue.config.productionTip = false;

axios.interceptors.request.use((config) => {
  config.baseURL = process.env.VUE_APP_URL_PREFIX;
  config.headers.token = 'hello_world';
  return config;
});

new Vue({
  router,
  render: (h) => h(App),
}).$mount('#app');
