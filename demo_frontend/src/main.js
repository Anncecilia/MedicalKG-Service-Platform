import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import ElementUI from "element-ui";
import axios from "axios";
import "element-ui/lib/theme-chalk/index.css";

console.log(ElementUI);

Vue.config.productionTip = false;
Vue.use(ElementUI)
axios.defaults.baseURL = "http://localhost:8077"; // 配置axios请求的地址
axios.defaults.headers.post["Content-Type"] = "application/json; charset=utf-8";
axios.defaults.crossDomain = true;
// axios.defaults.withCredentials = true;

Vue.prototype.$http = axios;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
