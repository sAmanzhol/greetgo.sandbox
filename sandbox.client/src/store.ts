import Vue from 'vue';
import Vuex from 'vuex';
import axios from 'axios';

Vue.use(Vuex);

axios.interceptors.request.use(config => {
  config.baseURL = process.env.VUE_APP_URL_PREFIX;
  config.headers["token"] = "hello_world";
  return config;
});

export default new Vuex.Store({
  state: {

  },
  mutations: {

  },
  actions: {

  },
});
